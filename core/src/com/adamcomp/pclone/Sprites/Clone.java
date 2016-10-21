package com.adamcomp.pclone.Sprites;

import com.adamcomp.pclone.Main;
import com.adamcomp.pclone.Screens.PlayScreen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Stack;

/**
 * Created by Adam on 2016. 09. 01..
 */
public class Clone extends Sprite{
    public World world;
    public Body body;

    public Stack<String> movement;
    private float posX, posY;

    public Clone(World world, float x, float y){
        this.world = world;
        movement = new Stack<String>();
        posX = x; posY = y;
        setUpClone();
    }

    public void setUpClone(){

    }


    public void setMovement(String a){
        movement.push(a);
    }

    public void draw(Batch batch){
        super.draw(batch);
    }
}
