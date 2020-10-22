package com.mystarwars.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mystarwars.base.Sprite;
import com.mystarwars.math.Rect;

public class Background extends Sprite {

    public Background(TextureRegion region) {
        super(region);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(worldBounds.getHeight());
        pos.set(worldBounds.pos);
    }
}
