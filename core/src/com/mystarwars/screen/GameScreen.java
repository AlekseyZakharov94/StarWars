package com.mystarwars.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.mystarwars.base.BaseScreen;
import com.mystarwars.math.Rect;
import com.mystarwars.pool.BulletPool;
import com.mystarwars.pool.EnemyShipPool;
import com.mystarwars.sprite.Background;
import com.mystarwars.sprite.MainShip;
import com.mystarwars.sprite.Star;
import com.mystarwars.utils.EnemyEmitter;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 64;

    private TextureAtlas atlas;
    private Texture bg;
    private Music music;
    private Sound enemyBulletSound;

    private Background background;
    private Star[] stars;
    private BulletPool bulletPool;
    private EnemyShipPool enemyShipPool;
    private MainShip mainShip;
    private EnemyEmitter enemyEmitter;


    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas("textures\\mainAtlas.tpack");
        bg = new Texture("textures/background.jpg");
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds\\music.mp3"));
        enemyBulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds\\bullet.wav"));

        background = new Background(bg);
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        enemyShipPool = new EnemyShipPool(bulletPool, worldBounds);
        mainShip = new MainShip(atlas, bulletPool);
        enemyEmitter = new EnemyEmitter(worldBounds, enemyShipPool, enemyBulletSound, atlas);

        music.setLooping(true);
        music.play();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollision();
        freeAllDestroyed();
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        enemyShipPool.dispose();
        music.dispose();
        enemyBulletSound.dispose();
        mainShip.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        mainShip.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        mainShip.touchUp(touch, pointer, button);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
            mainShip.update(delta);
            bulletPool.updateActiveSprites(delta);
            enemyShipPool.updateActiveSprites(delta);
            enemyEmitter.generate(delta);
        }
    }

    private void checkCollision() {

    }

    private void freeAllDestroyed(){
        bulletPool.freeAllDestroydActiveSprites();
        enemyShipPool.freeAllDestroydActiveSprites();
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        mainShip.draw(batch);
        bulletPool.drawActiveSprites(batch);
        enemyShipPool.drawActiveSprites(batch);
        batch.end();
    }
}
