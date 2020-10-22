package com.mystarwars.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mystarwars.base.BaseScreen;
import com.mystarwars.math.Rect;
import com.mystarwars.sprite.Background;
import com.mystarwars.sprite.Logo;

public class MenuScreen extends BaseScreen {

    private Background background;
    private Texture bg;
    private Logo logo;
    private Texture img;
    private Vector2 imgV;
    private Vector2 tmpTouch;


    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/background.jpg");
        background = new Background(new TextureRegion(bg));
        img = new Texture("textures\\badlogic.jpg");
        logo = new Logo(new TextureRegion(img));
        imgV = new Vector2();
        tmpTouch = new Vector2();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        background.draw(batch);
        logo.draw(batch);
        logo.startMoving(imgV);
        if (tmpTouch.cpy().sub(logo.pos).len() < imgV.len()){
            imgV.setZero();
        }
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        logo.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        super.dispose();
    }



    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
       imgV.set(touch.cpy().sub(logo.pos).scl(0.01f));
       tmpTouch.set(touch);
       return false;
    }
}
