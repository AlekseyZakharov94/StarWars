package com.mystarwars.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.mystarwars.base.BaseScreen;
import com.mystarwars.math.Rect;
import com.mystarwars.pool.BulletPool;
import com.mystarwars.pool.EnemyShipPool;
import com.mystarwars.pool.ExplosionPool;
import com.mystarwars.sprite.Background;
import com.mystarwars.sprite.Bullet;
import com.mystarwars.sprite.ButtonNewGame;
import com.mystarwars.sprite.EnemyShip;
import com.mystarwars.sprite.GameOver;
import com.mystarwars.sprite.MainShip;
import com.mystarwars.sprite.Star;
import com.mystarwars.sprite.TrackingStar;
import com.mystarwars.utils.EnemyEmitter;
import com.mystarwars.utils.Font;

import java.util.List;

// Завершенный проект

public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 64;
    private static final float FONT_SIZE = 0.02f;

    private static final String FRAGS = "FRAGS: ";
    private static final String HP = "HP: ";
    private static final String LEVEL = "LEVEL: ";

    private enum State {PLAYING, GAME_OVER}

    private TextureAtlas atlas;
    private Texture bg;
    private Music music;
    private Sound enemyBulletSound;
    private Sound explosionSound;

    private Background background;
    private TrackingStar[] stars;
    private BulletPool bulletPool;
    private EnemyShipPool enemyShipPool;
    private ExplosionPool explosionPool;
    private MainShip mainShip;
    private EnemyEmitter enemyEmitter;
    private GameOver gameOver;
    private ButtonNewGame buttonNewGame;

    private State state;

    private int frags;
    private Font font;
    private StringBuilder sbFrags;
    private StringBuilder sbHp;
    private StringBuilder sbLevel;


    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas("textures\\mainAtlas.tpack");
        bg = new Texture("textures/background.jpg");
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds\\music.mp3"));
        enemyBulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds\\bullet.wav"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds\\explosion.wav"));
        background = new Background(bg);
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas, explosionSound);
        enemyShipPool = new EnemyShipPool(bulletPool, explosionPool, worldBounds);
        mainShip = new MainShip(atlas, explosionPool, bulletPool);
        enemyEmitter = new EnemyEmitter(worldBounds, enemyShipPool, enemyBulletSound, atlas);

        stars = new TrackingStar[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i] = new TrackingStar(atlas, mainShip.getV());
        }

        gameOver = new GameOver(atlas);
        buttonNewGame = new ButtonNewGame(atlas, this);

        font = new Font("font/font.fnt", "font/font.png");
        font.setSize(FONT_SIZE);
        sbFrags = new StringBuilder();
        sbHp = new StringBuilder();
        sbLevel = new StringBuilder();

        music.setLooping(true);
        music.play();

        state = State.PLAYING;
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
        explosionPool.dispose();
        enemyShipPool.dispose();
        music.dispose();
        enemyBulletSound.dispose();
        explosionSound.dispose();
        mainShip.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            mainShip.touchDown(touch, pointer, button);
        } else if (state == State.GAME_OVER) {
            buttonNewGame.touchDown(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            mainShip.touchUp(touch, pointer, button);
        } else if (state == State.GAME_OVER) {
            buttonNewGame.touchUp(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyUp(keycode);
        }
        return false;
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        explosionPool.updateActiveSprites(delta);
        if (state == State.PLAYING) {
            bulletPool.updateActiveSprites(delta);
            enemyShipPool.updateActiveSprites(delta);
            mainShip.update(delta);
            enemyEmitter.generate(delta, frags);
        }
    }

    private void checkCollision() {
        if (state == State.GAME_OVER) {
            return;
        }
        List<EnemyShip> enemyShipList = enemyShipPool.getActiveObjects();
        for (EnemyShip enemyShip : enemyShipList) {
            if (enemyShip.isDestroyed()) {
                continue;
            }
            float minDist = mainShip.getHalfWidth() + enemyShip.getHalfWidth();
            if (enemyShip.pos.dst(mainShip.pos) < minDist) {
                enemyShip.destroy();
                mainShip.damage(enemyShip.getDamage());
                if (mainShip.isDestroyed()) {
                    state = State.GAME_OVER;
                }
                return;
            }
        }
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Bullet bullet : bulletList) {
            if (bullet.isDestroyed()) {
                continue;
            }
            if (bullet.getOwner() != mainShip) {
                if (mainShip.isBulletCollision(bullet)) {
                    bullet.destroy();
                    mainShip.damage(bullet.getDamage());
                    if (mainShip.isDestroyed()) {
                        state = State.GAME_OVER;
                    }
                    return;
                }
                continue;
            }
            for (EnemyShip enemyShip : enemyShipList) {
                if (enemyShip.isBulletCollision(bullet)) {
                    bullet.destroy();
                    enemyShip.damage(bullet.getDamage());
                    if (enemyShip.isDestroyed()) {
                        frags++;
                    }
                    return;
                }
            }
        }
    }

    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
        enemyShipPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        explosionPool.drawActiveSprites(batch);
        if (state == State.PLAYING) {
            mainShip.draw(batch);
            enemyShipPool.drawActiveSprites(batch);
            bulletPool.drawActiveSprites(batch);
        } else if (state == State.GAME_OVER) {
            gameOver.draw(batch);
            buttonNewGame.draw(batch);
        }
        printInfo();
        batch.end();
    }

    private void printInfo() {
        sbFrags.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft(), worldBounds.getTop());

        sbHp.setLength(0);
        font.draw(batch, sbHp.append(HP).append(mainShip.getHp()), worldBounds.pos.x, worldBounds.getTop(), Align.center);

        sbLevel.setLength(0);
        font.draw(batch, sbLevel.append(LEVEL).append(enemyEmitter.getLevel()), worldBounds.getRight(), worldBounds.getTop(), Align.right);
    }

    public void startNewGame() {
        state = State.PLAYING;
        frags = 0;
        mainShip.startNewGame(worldBounds);
        bulletPool.freeAllActiveObjects();
        enemyShipPool.freeAllActiveObjects();
        explosionPool.freeAllActiveObjects();
    }
}
