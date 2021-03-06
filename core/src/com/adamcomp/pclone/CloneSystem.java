package com.adamcomp.pclone;

import com.adamcomp.pclone.Sprites.Clone;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.uwsoft.editor.renderer.components.DimensionsComponent;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.physics.PhysicsBodyLoader;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;

/**
 * Created by Adam on 2016. 10. 22..
 */
public class CloneSystem extends IteratingSystem {

    private ComponentMapper<CloneComponent> mapper = ComponentMapper.getFor(CloneComponent.class);


    private float gravity = -500;
    private float jumpSpeed = 200;

    Vector2 speed;

    public CloneSystem() {
        super(Family.all(CloneComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transformComponent = ComponentRetriever.get(entity, TransformComponent.class);
        DimensionsComponent dimensionsComponent = ComponentRetriever.get(entity, DimensionsComponent.class);
        CloneComponent cloneComponent = mapper.get(entity);

        speed = cloneComponent.speed;

        if (cloneComponent.movements.size() > cloneComponent.frame && cloneComponent.frame >= 0) {
            if (cloneComponent.movements.get(cloneComponent.frame) == "J")
                cloneComponent.speed.y = jumpSpeed;
            if (cloneComponent.movements.get(cloneComponent.frame) == "R")
                cloneComponent.coord.x += cloneComponent.speed.x * deltaTime;
            // transformComponent.scaleX = 1.0f;
            if (cloneComponent.movements.get(cloneComponent.frame) == "L")
                cloneComponent.coord.x -= cloneComponent.speed.x * deltaTime;
            // transformComponent.scaleX = -1.0f;
        }
        cloneComponent.frame++;
        cloneComponent.speed.y += gravity * deltaTime;
        cloneComponent.coord.y += cloneComponent.speed.y * deltaTime;

        cloneComponent.speed.x = 100;
        rayCast(dimensionsComponent, transformComponent, cloneComponent.world, cloneComponent);
        if (speed.x == 0) cloneComponent.speed.x = 0;


        transformComponent.x = cloneComponent.coord.x;
        transformComponent.y = cloneComponent.coord.y;
    }

    private void rayCast(DimensionsComponent dimensionsComponent, TransformComponent transformComponent, World world, final CloneComponent clone) {
        float rayGap = dimensionsComponent.height / 2;
        float raySize = -(speed.y) * Gdx.graphics.getDeltaTime();
        float raySizeX = (speed.x) * Gdx.graphics.getDeltaTime();


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
                    clone.coord.y = point.y / PhysicsBodyLoader.getScale() + 0.01f;
                    clone.grounded = true;
                    //transformComponent.y = point.y / PhysicsBodyLoader.getScale() + 0.01f;
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

        //raycast right
        if (clone.frame - 1 >= 0 && clone.frame < clone.movements.size()) {
            if (clone.movements.get(clone.frame - 1) == "R") {
                Vector2 rayFrom = new Vector2((transformComponent.x + dimensionsComponent.width / 2) * PhysicsBodyLoader.getScale(),
                        (transformComponent.y + dimensionsComponent.height / 2) * PhysicsBodyLoader.getScale());
                Vector2 rayTo = new Vector2((transformComponent.x + dimensionsComponent.width + raySizeX) * PhysicsBodyLoader.getScale(),
                        (transformComponent.y + dimensionsComponent.height / 2) * PhysicsBodyLoader.getScale());

                //Cast the ray
                world.rayCast(new RayCastCallback() {
                    @Override
                    public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
                        speed.x = 0;
                        clone.coord.x = clone.coord.x - 0.01f;
                        return 0;

                    }
                }, rayFrom, rayTo);
            }
        }

        //Raycast Left
        if (clone.frame - 1 >= 0 && clone.frame < clone.movements.size()) {
            if (clone.movements.get(clone.frame - 1) == "R") {
                Vector2 rayFrom = new Vector2((transformComponent.x + dimensionsComponent.width / 2) * PhysicsBodyLoader.getScale(),
                        (transformComponent.y + dimensionsComponent.height / 2) * PhysicsBodyLoader.getScale());
                Vector2 rayTo = new Vector2((transformComponent.x - raySizeX) * PhysicsBodyLoader.getScale(),
                        (transformComponent.y + dimensionsComponent.height / 2) * PhysicsBodyLoader.getScale());

                //Cast the ray
                world.rayCast(new RayCastCallback() {
                    @Override
                    public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
                        speed.x = 0;
                        clone.coord.x = clone.coord.x + 0.01f;
                        return 0;

                    }
                }, rayFrom, rayTo);
            }
        }
    }
}
