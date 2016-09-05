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

    public Clone(World world){
        this.world = world;
        movement = new Stack<String>();
        setUpClone();
    }

    public void setUpClone(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(320 / Main.PPM, 450 / Main.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10 / Main.PPM);
        //fdef.filter.categoryBits = 
       // fdef.filter.maskBits =

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / Main.PPM, 6 / Main.PPM), new Vector2(2 / Main.PPM, 6 / Main.PPM));
       // fdef.filter.categoryBits =
        fdef.shape = head;
        fdef.isSensor = true;

        body.createFixture(fdef).setUserData(this);
    }

    public void setMovement(String a){
        movement.push(a);
    }

    public void draw(Batch batch){
        super.draw(batch);
    }
}
