package com.mystarwars.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mystarwars.base.BaseScreen;
import com.mystarwars.math.Rect;
import com.mystarwars.sprite.Background;
import com.mystarwars.sprite.Ship;
import com.mystarwars.sprite.Star;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 64;

    private TextureAtlas atlas;
    private Texture bg;
    private Texture shipTexture;

    private Background background;
    private Star[] stars;
    private Ship ship;

    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas("textures\\mainAtlas.tpack");
        bg = new Texture("textures/background.jpg");

        background = new Background(bg);
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i] = new Star(atlas);
        }
//        shipTexture = atlas.findRegion("main_ship").getTexture();
        ship = new Ship(atlas);
//        ship = new Ship(new TextureRegion(shipTexture, 0,0, shipTexture.getWidth()/2, shipTexture.getHeight()));

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollision();
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star: stars) {
            star.resize(worldBounds);
        }
        ship.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        return super.touchDown(touch, pointer, button);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        return super.touchUp(touch, pointer, button);
    }

    @Override
    public boolean keyDown(int keycode) {
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        return super.keyUp(keycode);
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
    }

    private void checkCollision(){

    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        ship.draw(batch);
        batch.end();
    }
}
