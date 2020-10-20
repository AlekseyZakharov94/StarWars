package com.mystarwars.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mystarwars.base.BaseScreen;

public class MenuScreen extends BaseScreen {
    private Texture img;
    private Vector2 imgV;
    private Vector2 pointV;
    private Vector2 v;


    @Override
    public void show() {
        super.show();
        img = new Texture("badlogic.jpg");
        imgV = new Vector2();
        pointV = new Vector2();
        v = new Vector2();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw(img, imgV.x, imgV.y);
        batch.end();
        imgV.add(v);
        if(pointV.cpy().sub(imgV).len() < v.len()){
            v.setZero();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        pointV.set(screenX, Gdx.graphics.getHeight() - screenY);
        v.set(pointV.cpy().sub(imgV).nor());
                return false;
    }
}
