package com.mystarwars.pool;

import com.mystarwars.base.SpritesPool;
import com.mystarwars.sprite.Bullet;

public class BulletPool extends SpritesPool<Bullet> {

    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
