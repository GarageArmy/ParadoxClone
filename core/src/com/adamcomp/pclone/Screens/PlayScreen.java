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

/**
 * Created by Adam on 2016. 09. 01..
 */
public class PlayScreen implements Screen{

    private OrthographicCamera gamecam;
    private Game game;

    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;

    private Clone player;

    public PlayScreen(Game game){
        this.game = game;

        world = new World(new Vector2(0, -9.81f), true);
        b2dr = new Box2DDebugRenderer();

        // create platform
        BodyDef bdef = new BodyDef();
        bdef.position.set(320 / Main.PPM, 240 / Main.PPM);
        bdef.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(bdef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(100 / Main.PPM, 10 / Main.PPM);
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        body.createFixture(fdef);

        // create falling box
        bdef.position.set(320 / Main.PPM, 400 / Main.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        shape.setAsBox(10 / Main.PPM, 10 / Main.PPM);
        fdef.shape = shape;
        body.createFixture(fdef);


        player = new Clone(world);
        // set up box2d cam
        gamecam = new OrthographicCamera();
        gamecam.setToOrtho(false, Main.V_WIDTH / Main.PPM, Main.V_HEIGHT / Main.PPM);
    }


    public void handleInput(float dt){
        //control our player using immediate impulses
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
                player.body.applyLinearImpulse(new Vector2(0, 4f), player.body.getWorldCenter(), true);
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.body.getLinearVelocity().x <= 2)
                player.body.applyLinearImpulse(new Vector2(0.1f, 0), player.body.getWorldCenter(), true);
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.body.getLinearVelocity().x >= -2)
                player.body.applyLinearImpulse(new Vector2(-0.1f, 0), player.body.getWorldCenter(), true);
    }



    public void update(float dt){
        world.step(1 / 60f, 6, 2);
        handleInput(dt);


        gamecam.update();

    }



    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        // clear screen
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // draw box2d world
        b2dr.render(world, gamecam.combined);
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
