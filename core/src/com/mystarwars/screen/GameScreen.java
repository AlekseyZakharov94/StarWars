package com.mystarwars.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.mystarwars.base.BaseScreen;
import com.mystarwars.math.Rect;
import com.mystarwars.pool.BulletPool;
import com.mystarwars.sprite.Background;
import com.mystarwars.sprite.Ship;
import com.mystarwars.sprite.Star;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 64;

    private TextureAtlas atlas;
    private Texture bg;

    private Background background;
    private Star[] stars;
    private BulletPool bulletPool;
    private Ship ship;
    private Music music;

    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas("textures\\mainAtlas.tpack");
        bg = new Texture("textures/background.jpg");

        background = new Background(bg);
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        ship = new Ship(atlas, bulletPool);
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds\\music.mp3"));
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
        ship.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        ship.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        ship.touchUp(touch, pointer, button);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        ship.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        ship.keyUp(keycode);
        return false;
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
            ship.update(delta);
            bulletPool.updateActiveSprites(delta);
        }
    }

    private void checkCollision() {

    }

    private void freeAllDestroyed(){
        bulletPool.freeAllDestroydActiveSprites();
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        ship.draw(batch);
        bulletPool.drawActiveSprites(batch);
        batch.end();
    }
}
