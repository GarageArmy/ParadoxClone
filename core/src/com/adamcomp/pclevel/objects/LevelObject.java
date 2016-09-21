package com.adamcomp.pclevel.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.XmlReader.Element;

/**
 * Created by Gabriel025 on 2016.09.16.
 */

public class LevelObject{
    private Element element;

    public LevelObject(Element element)
    {
        this.element = element;
    }

    Element getXML()
    {
        return element;
    }

    void render() {

    }
}
