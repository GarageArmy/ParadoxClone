package com.adamcomp.pclone;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.scripts.IScript;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;

/**
 * Created by Adam on 2016. 10. 21..
 */
public class Player implements IScript {
    private Entity player;
    private TransformComponent transformComponent;


    @Override
    public void init(Entity entity) {
        player = entity;
        transformComponent = ComponentRetriever.get(entity, TransformComponent.class);
    }

    @Override
    public void act(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            transformComponent.x -= 10;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            transformComponent.x += 10;
    }

    @Override
    public void dispose() {

    }
}
