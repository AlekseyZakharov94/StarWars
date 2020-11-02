package com.mystarwars.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mystarwars.math.Rect;
import com.mystarwars.pool.BulletPool;
import com.mystarwars.sprite.Bullet;

public class Ship extends Sprite {

    protected Rect worldBounds;

    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;
    protected Sound bulletSound;
    protected float timeWithoutFire;
    protected  float reloadInterval;
    protected  float bulletHeight;
    protected int damage;
    protected int hp;

    protected final Vector2 v;
    protected final Vector2 v0;
    protected final Vector2 bulletV;
    protected final Vector2 bulletPos;

    public Ship() {
        v = new Vector2();
        v0 = new Vector2();
        bulletV = new Vector2();
        bulletPos = new Vector2();
    }

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
        v = new Vector2();
        v0 = new Vector2();
        bulletV = new Vector2();
        bulletPos = new Vector2();
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        timeWithoutFire += delta;
        if (timeWithoutFire >= reloadInterval) {
            shot();
            timeWithoutFire = 0;
        }
    }

    private void shot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, bulletPos, bulletV, worldBounds, damage, bulletHeight);
        bulletSound.play(1.0f);
    }
}
