package com.mystarwars.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mystarwars.base.Sprite;
import com.mystarwars.math.Rect;

public class Logo extends Sprite {

    public Logo(TextureRegion region) {
        super(region);
    }

    @Override
    public void resize(Rect worldBounds) {
        setSize(0.1f, 0.1f);
        pos.set(worldBounds.pos);
    }

    public void startMoving(Vector2 v){
        pos.add(v);
    }
}
