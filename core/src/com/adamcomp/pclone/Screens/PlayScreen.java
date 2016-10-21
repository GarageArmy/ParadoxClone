package com.adamcomp.pclone.Screens;

import com.adamcomp.pclone.Main;
import com.adamcomp.pclone.Sprites.Clone;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.uwsoft.editor.renderer.SceneLoader;

import java.util.Stack;

/**
 * Created by Adam on 2016. 09. 01..
 */
public class PlayScreen implements Screen{


    private Game game;

    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;

    private Clone player;
    private Stack<Clone> cloneStack;

    private int cloneFrame = 0;

    public PlayScreen(Game game){
        Viewport viewport = new FitViewport(300, 200);
        SceneLoader sceneLoader = new SceneLoader();
        sceneLoader.loadScene("MainScene", viewport);


        this.game = game;
        cloneStack = new Stack<Clone>();

        world = new World(new Vector2(0, -9.81f), true);
        b2dr = new Box2DDebugRenderer();

        // create platform

    }


    public void handleInput(float dt){

    }



    public void update(float dt){
        world.step(1 / 60f, 6, 2);
        handleInput(dt);
        if (!cloneStack.empty()) {
            for (int i = 0; i < cloneStack.size(); i++) {
                if (cloneStack.get(i).movement.size() > cloneFrame) {
                    if (cloneStack.get(i).movement.get(cloneFrame) == "J")
                        cloneStack.get(i).body.applyLinearImpulse(new Vector2(0, 4f), player.body.getWorldCenter(), true);

                    if (cloneStack.get(i).movement.get(cloneFrame) == "R")
                        cloneStack.get(i).body.applyLinearImpulse(new Vector2(0.1f, 0), player.body.getWorldCenter(), true);

                    if (cloneStack.get(i).movement.get(cloneFrame) == "L")
                        cloneStack.get(i).body.applyLinearImpulse(new Vector2(-0.1f, 0), player.body.getWorldCenter(), true);
                }
            }
            cloneFrame++;
        }




    }



    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        // clear screen
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // draw box2d world
        update(delta);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }


}
