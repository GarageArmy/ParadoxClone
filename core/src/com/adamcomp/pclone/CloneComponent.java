package com.adamcomp.pclone;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Stack;

/**
 * Created by Adam on 2016. 10. 22..
 */
public class CloneComponent implements Component {

    public Stack<String> movements;
    public World world;
    public Vector2 speed = new Vector2(100, 0);
    public Vector2 coord;
    public boolean grounded = false;
    public int frame = 0;


    public CloneComponent(Stack<String> input, World world){
        movements = input;
        this.world = world;
    }
}
