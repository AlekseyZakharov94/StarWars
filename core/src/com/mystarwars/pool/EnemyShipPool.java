package com.mystarwars.pool;

import com.mystarwars.base.SpritesPool;
import com.mystarwars.math.Rect;
import com.mystarwars.sprite.EnemyShip;

public class EnemyShipPool extends SpritesPool<EnemyShip> {

    private BulletPool bulletPool;
    private Rect worldBounds;

    public EnemyShipPool(BulletPool bulletPool, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(bulletPool, worldBounds);
    }
}
