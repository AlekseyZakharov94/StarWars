package com.mystarwars.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mystarwars.base.Ship;
import com.mystarwars.math.Rect;
import com.mystarwars.pool.BulletPool;

public class EnemyShip extends Ship {

    private static final float START_Y_BOOSTER = -0.05f;

    public EnemyShip(BulletPool bulletPool, Rect worldBounds) {
        this.bulletPool = bulletPool;
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
}
