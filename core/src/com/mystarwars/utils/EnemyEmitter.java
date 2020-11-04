package com.mystarwars.utils;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mystarwars.math.Rect;
import com.mystarwars.math.Rnd;
import com.mystarwars.pool.EnemyShipPool;
import com.mystarwars.sprite.EnemyShip;

public class EnemyEmitter {

    private static final float GENERATE_INTERVAL = 120f;

    private static final float ENEMY_SMALL_HEIGHT = 0.1f;
    private static final float ENEMY_SMALL_BULLET_HEIGHT = 0.01f;
    private static final int ENEMY_SMALL_DAMAGE = 1;
    private static final float ENEMY_SMALL_RELOAD_INTERVAL = 30f;
    private static final int ENEMY_SMALL_HP = 1;

    private static final float ENEMY_MEDIUM_HEIGHT = 0.15f;
    private static final float ENEMY_MEDIUM_BULLET_HEIGHT = 0.02f;
    private static final int ENEMY_MEDIUM_DAMAGE = 5;
    private static final float ENEMY_MEDIUM_RELOAD_INTERVAL = 40f;
    private static final int ENEMY_MEDIUM_HP = 5;

    private static final float ENEMY_BIG_HEIGHT = 0.2f;
    private static final float ENEMY_BIG_BULLET_HEIGHT = 0.04f;
    private static final int ENEMY_BIG_DAMAGE = 10;
    private static final float ENEMY_BIG_RELOAD_INTERVAL = 40f;
    private static final int ENEMY_BIG_HP = 10;

    private final TextureRegion[] enemySmallRegions;
    private final TextureRegion[] enemyMediumRegions;
    private final TextureRegion[] enemyBigRegions;

    private final Vector2 enemySmallV = new Vector2(0f, -0.002f);
    private final Vector2 enemySmallBulletV = new Vector2(0f, -0.005f);

    private final Vector2 enemyMediumV = new Vector2(0f, -0.0003f);
    private final Vector2 enemyMediumBulletV = new Vector2(0f, -0.0025f);

    private final Vector2 enemyBigV = new Vector2(0f, -0.0005f);
    private final Vector2 enemyBigBulletV = new Vector2(0f, -0.0025f);

    private Rect worldBounds;
    private EnemyShipPool enemyShipPool;
    private Sound bulletSound;
    private TextureRegion bulletRegion;
    private float generateTimer;

    public EnemyEmitter(Rect worldBounds, EnemyShipPool enemyShipPool, Sound bulletSound, TextureAtlas atlas) {
        this.worldBounds = worldBounds;
        this.enemyShipPool = enemyShipPool;
        this.bulletSound = bulletSound;
        this.bulletRegion = atlas.findRegion("bulletEnemy");
        TextureRegion enemy0 = atlas.findRegion("enemy0");
        enemySmallRegions = Regions.split(enemy0, 1, 2, 2);
        TextureRegion enemy1 = atlas.findRegion("enemy1");
        enemyMediumRegions = Regions.split(enemy1, 1, 2, 2);
        TextureRegion enemy2 = atlas.findRegion("enemy2");
        enemyBigRegions = Regions.split(enemy2, 1, 2, 2);
    }

    public void generate(float delta) {
        generateTimer += delta;
        if (generateTimer >= GENERATE_INTERVAL) {
            generateTimer = 0;
            EnemyShip enemyShip = enemyShipPool.obtain();
            float type = (float) Math.random();
            if (type < 0.6f) {
                enemyShip.set(enemySmallRegions, enemySmallV, bulletRegion, ENEMY_SMALL_BULLET_HEIGHT,
                        enemySmallBulletV, bulletSound, ENEMY_SMALL_DAMAGE, ENEMY_SMALL_RELOAD_INTERVAL,
                        ENEMY_SMALL_HEIGHT, ENEMY_SMALL_HP);
            } else if (type < 0.9f) {
                enemyShip.set(enemyMediumRegions, enemyMediumV, bulletRegion, ENEMY_MEDIUM_BULLET_HEIGHT,
                        enemyMediumBulletV, bulletSound, ENEMY_MEDIUM_DAMAGE, ENEMY_MEDIUM_RELOAD_INTERVAL,
                        ENEMY_MEDIUM_HEIGHT, ENEMY_MEDIUM_HP);
            } else {
                enemyShip.set(enemyBigRegions, enemyBigV, bulletRegion, ENEMY_BIG_BULLET_HEIGHT,
                        enemyBigBulletV, bulletSound, ENEMY_BIG_DAMAGE, ENEMY_BIG_RELOAD_INTERVAL,
                        ENEMY_BIG_HEIGHT, ENEMY_BIG_HP);
            }
            enemyShip.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemyShip.getHalfWidth(),
                    worldBounds.getRight() - enemyShip.getHalfWidth());
            enemyShip.setBottom(worldBounds.getTop());
        }
    }
}
