package com.adamcomp.pclevel;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Main extends Game {
    private Stage stage;
    private Skin skin;
    private Window wnd;

    @Override
    public void create () {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("visui/uiskin.json"),
                new TextureAtlas("visui/uiskin.atlas"));

        wnd = new Window("Objects", skin);
        stage.addActor(wnd);
        wnd.debug();
        wnd.setPosition(10, 10);
        wnd.setSize(150, 500);
        TextButton btn = new TextButton("TEST", skin);
        wnd.add().setActor(btn);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render() {
        stage.act(Gdx.graphics.getDeltaTime());

        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
    }
}