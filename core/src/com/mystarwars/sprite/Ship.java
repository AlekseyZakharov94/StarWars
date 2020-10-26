package com.mystarwars.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mystarwars.base.Sprite;
import com.mystarwars.math.Rect;

public class Ship extends Sprite {

    public Ship(TextureAtlas atlas) {
        super(new TextureRegion(atlas.findRegion("main_ship"),0,0,atlas.findRegion("main_ship").getRegionWidth()/2,
                atlas.findRegion("main_ship").getRegionHeight()));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.2f);
        setRight(worldBounds.pos.x + getWidth()/2);
        setBottom(worldBounds.getBottom());
    }
}
