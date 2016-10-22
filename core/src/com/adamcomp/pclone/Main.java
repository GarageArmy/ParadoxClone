package com.adamcomp.pclone;

import com.adamcomp.pclone.Sprites.Clone;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.data.SimpleImageVO;
import com.uwsoft.editor.renderer.utils.ItemWrapper;

import java.util.Stack;

public class Main extends ApplicationAdapter {
	SceneLoader sceneLoader;
	Player player;
	Viewport viewport;

	@Override
	public void create () {
		viewport = new FitViewport(320, 240);
		sceneLoader = new SceneLoader();
		sceneLoader.loadScene("MainScene", viewport);
		ItemWrapper root = new ItemWrapper(sceneLoader.getRoot());

		player = new Player(sceneLoader.world);
		root.getChild("player").addScript(player);

		sceneLoader.getEngine().addSystem(new CloneSystem());


	}

	private void addClone(Stack<String> input){
		SimpleImageVO vo = new SimpleImageVO();
		vo.imageName = "p1front";
		vo.x = 40;
		vo.y = 40;

		Entity entity = sceneLoader.entityFactory.createEntity(sceneLoader.getRoot(), vo);
		CloneComponent cloneComponent = new CloneComponent(input, sceneLoader.world);
		entity.add(cloneComponent);
		sceneLoader.getEngine().addEntity(entity);


	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sceneLoader.getEngine().update(Gdx.graphics.getDeltaTime());

		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			addClone(player.getStack());
			sceneLoader.getEngine().getSystem(CloneSystem.class).cloneFrame = 0;
		}

		((OrthographicCamera)viewport.getCamera()).position.x = player.getX() + player.getWidth() / 2;
	}
}
