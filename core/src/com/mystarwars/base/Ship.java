package com.mystarwars.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mystarwars.math.Rect;
import com.mystarwars.pool.BulletPool;
import com.mystarwars.pool.ExplosionPool;
import com.mystarwars.sprite.Bullet;
import com.mystarwars.sprite.Explosion;

public class Ship extends Sprite {

    private static final float DAMAGE_ANIMATE_INTERVAL = 0.1f;

    protected Rect worldBounds;
    protected BulletPool bulletPool;
    protected ExplosionPool explosionPool;
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

    private  float damageAnimateTimer = DAMAGE_ANIMATE_INTERVAL;

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
        damageAnimateTimer += delta;
        if (damageAnimateTimer >= DAMAGE_ANIMATE_INTERVAL){
            frame =0;
        }
    }

    private void shot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, bulletPos, bulletV, worldBounds, damage, bulletHeight);
        bulletSound.play(1.0f);
    }

    public void damage(int damage){
        frame = 1;
        damageAnimateTimer = 0f;
        hp -= damage;
        if (hp <= 0){
            hp = 0;
           destroy();
        }
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public void destroy() {
        super.destroy();
        boom();
    }

    public void boom(){
        Explosion explosion = explosionPool.obtain();
        explosion.set(getHeight(), pos);
    }

    public Vector2 getV() {
        return v;
    }

    public int getHp() {
        return hp;
    }
}
