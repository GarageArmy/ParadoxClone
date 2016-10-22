package com.adamcomp.pclone;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.uwsoft.editor.renderer.components.DimensionsComponent;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.components.physics.PhysicsBodyComponent;
import com.uwsoft.editor.renderer.physics.PhysicsBodyLoader;
import com.uwsoft.editor.renderer.scripts.IScript;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;

import java.util.Stack;
import java.util.Vector;

/**
 * Created by Adam on 2016. 10. 21..
 */
public class Player implements IScript {
    private Entity player;
    private TransformComponent transformComponent;
    private DimensionsComponent dimensionsComponent;

    private World world;

    private Vector2 speed;
    private float gravity = -500;
    private float jumpSpeed = 200;
    private boolean grounded = false;

    private Stack<String> movementSequence;





    public Player (World world){
        this.world = world;
    }

    @Override
    public void init(Entity entity) {
        player = entity;
        transformComponent = ComponentRetriever.get(entity, TransformComponent.class);
        dimensionsComponent = ComponentRetriever.get(entity, DimensionsComponent.class);

        speed = new Vector2(100, 0);
        movementSequence = new Stack<String>();
    }

    @Override
    public void act(float delta) {

        inputHandler(delta);

        speed.y += gravity * delta;
        transformComponent.y += speed.y * delta;

        rayCast();
    }

    @Override
    public void dispose() {

    }

    private void inputHandler(float delta){
        boolean moved = false;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            moved = true;
            transformComponent.x -= speed.x * delta;
            transformComponent.scaleX = -1.0f;
            movementSequence.push("L");
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            moved = true;
            transformComponent.x += speed.x * delta;
            transformComponent.scaleX = 1.0f;
            movementSequence.push("R");
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && grounded){
            moved = true;
            grounded = false;
            speed.y = jumpSpeed;
            movementSequence.push("J");
        }
        if (!moved)
            movementSequence.push("0");
    }

    private void rayCast() {
        float rayGap = dimensionsComponent.height / 2;
        float raySize = -(speed.y) * Gdx.graphics.getDeltaTime();


        //Raycast down
        if (speed.y <= 0) {
            Vector2 rayFrom = new Vector2((transformComponent.x + dimensionsComponent.width / 2) * PhysicsBodyLoader.getScale(),
                    (transformComponent.y + rayGap) * PhysicsBodyLoader.getScale());
            Vector2 rayTo = new Vector2((transformComponent.x + dimensionsComponent.width / 2) * PhysicsBodyLoader.getScale(),
                    (transformComponent.y - raySize) * PhysicsBodyLoader.getScale());

            //Cast the ray
            world.rayCast(new RayCastCallback() {
                @Override
                public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
                    speed.y = 0;
                    transformComponent.y = point.y / PhysicsBodyLoader.getScale() + 0.01f;
                    grounded = true;
                    return 0;

                }
            }, rayFrom, rayTo);
        }

        //raycast up
        if (speed.y > 0) {
            Vector2 rayFrom = new Vector2((transformComponent.x + dimensionsComponent.width / 2) * PhysicsBodyLoader.getScale(),
                    (transformComponent.y + rayGap) * PhysicsBodyLoader.getScale());
            Vector2 rayTo = new Vector2((transformComponent.x + dimensionsComponent.width / 2) * PhysicsBodyLoader.getScale(),
                    (transformComponent.y + raySize + rayGap + rayGap) * PhysicsBodyLoader.getScale());

            //Cast the ray
            world.rayCast(new RayCastCallback() {
                @Override
                public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
                    speed.y = 0;
                    return 0;

                }
            }, rayFrom, rayTo);
        }
    }

    public float getX (){
        return transformComponent.x;
    }
    public float getWidth (){
        return dimensionsComponent.width;
    }
    public float getY (){
        return transformComponent.y;
    }
    public float getHeight (){
        return dimensionsComponent.height;
    }
    public Stack<String> getStack(){
        return movementSequence;
    }
}
