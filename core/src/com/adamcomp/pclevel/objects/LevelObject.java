package com.adamcomp.pclevel.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.XmlReader.Element;

/**
 * Created by Gabriel025 on 2016.09.16.
 */

public abstract class LevelObject{
    private World world;
    private Element element;

    public LevelObject(World world, Element element)
    {
        this.world = world;
    }

    Element getXML()
    {
        return element;
    }

    abstract void render();
}
