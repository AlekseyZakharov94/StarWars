package com.mystarwars.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mystarwars.base.Ship;
import com.mystarwars.math.Rect;
import com.mystarwars.pool.BulletPool;
import com.mystarwars.pool.ExplosionPool;

public class EnemyShip extends Ship {

    private static final float START_Y_BOOSTER = -0.5f;

    public EnemyShip(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(float delta) {
        bulletPos.set(pos.x, getBottom());
        super.update(delta);
        if(getTop() <= worldBounds.getTop()){
            v.set(v0);
        }
        if(getBottom() < worldBounds.getBottom()){
            destroy();
        }
    }

    public void set(TextureRegion[]regions, Vector2 v0, TextureRegion bulletRegion, float bulletHeight,
                    Vector2 bulletV, Sound bulletSound, int damage, float reloadInterval, float height,
                    int hp){
        this.regions = regions;
        this.v0.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(bulletV);
        this.bulletSound = bulletSound;
        this.damage = damage;
        this.reloadInterval = reloadInterval;
        setHeightProportion(height);
        this.hp = hp;
        this.v.set(0, START_Y_BOOSTER);
    }

    public boolean isBulletCollision(Rect bullet){
        return !(bullet.getRight() < getLeft() || bullet.getLeft() > getRight()
        || bullet.getBottom() > getTop() || bullet.getTop() < pos.y);
    }
}
