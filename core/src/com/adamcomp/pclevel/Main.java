package com.adamcomp.pclevel;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends Game {
    public static final float PPM = 100;
    public static final int V_WIDTH = 640;
    public static final int V_HEIGHT = 480;
    public SpriteBatch batch;

    @Override
    public void create () {
        batch = new SpriteBatch();
    }

    @Override
    public void render () {
        super.render();
    }
}