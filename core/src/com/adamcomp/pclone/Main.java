package com.adamcomp.pclone;

import com.adamcomp.pclone.Sprites.Clone;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.data.SimpleImageVO;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;
import com.uwsoft.editor.renderer.utils.ItemWrapper;

import java.util.Stack;

public class Main extends ApplicationAdapter {
	SceneLoader sceneLoader;
	Player player;
	Viewport viewport;

	//Finish flag
	TransformComponent finishTransform;
	TransformComponent startTransform;
	TransformComponent playerTransform;

	Stack<CloneComponent> cloneComponentStack = new Stack<CloneComponent>();

	@Override
	public void create () {
		viewport = new FitViewport(320, 240);
		sceneLoader = new SceneLoader();
		sceneLoader.loadScene("MainScene", viewport);
		ItemWrapper root = new ItemWrapper(sceneLoader.getRoot());

		player = new Player(sceneLoader.world);
		root.getChild("player").addScript(player);

		//Goal / Finish position
		finishTransform = ComponentRetriever.get(root.getChild("end").getEntity(), TransformComponent.class);
		startTransform = ComponentRetriever.get(root.getChild("start").getEntity(), TransformComponent.class);
		playerTransform = ComponentRetriever.get(root.getChild("player").getEntity(), TransformComponent.class);
		playerTransform.x = startTransform.x;
		playerTransform.y = startTransform.y;

		sceneLoader.getEngine().addSystem(new CloneSystem());


	}

	private void addClone(Stack<String> input){
		SimpleImageVO vo = new SimpleImageVO();
		vo.imageName = "p1front";
		vo.x = startTransform.x;
		vo.y = startTransform.y;

		Entity entity = sceneLoader.entityFactory.createEntity(sceneLoader.getRoot(), vo);

		Stack<String> movement = new Stack<String>();
        for (int i = 0; i < input.size(); i++){
            movement.push(input.get(i));
        }

		CloneComponent cloneComponent = new CloneComponent(movement, sceneLoader.world);
		cloneComponent.coord = new Vector2(vo.x, vo.y);
		entity.add(cloneComponent);
		sceneLoader.getEngine().addEntity(entity);
		cloneComponentStack.push(cloneComponent);
	}

	private void checkWin(){
		if (player.getX() >= finishTransform.x - 5 && player.getX() < finishTransform.x + 5 &&
				player.getY() >= finishTransform.y && player.getY() < finishTransform.y + 20)
			System.out.println("WIN");
	}

	private void followCamera(){
		((OrthographicCamera)viewport.getCamera()).position.x = player.getX() + player.getWidth() / 2;
		((OrthographicCamera)viewport.getCamera()).position.y = player.getY() + 2 * player.getHeight() / 2;

		/* Boundery */
		if (((OrthographicCamera)viewport.getCamera()).position.x < 160)
			((OrthographicCamera)viewport.getCamera()).position.x = 160;
		if (((OrthographicCamera)viewport.getCamera()).position.x > 640 - 160)
			((OrthographicCamera)viewport.getCamera()).position.x = 640 - 160;
		if (((OrthographicCamera)viewport.getCamera()).position.y < 120)
			((OrthographicCamera)viewport.getCamera()).position.y = 120;
		if (((OrthographicCamera)viewport.getCamera()).position.y > 360)
			((OrthographicCamera)viewport.getCamera()).position.y = 360;
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sceneLoader.getEngine().update(Gdx.graphics.getDeltaTime());

		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) &&
				playerTransform.x >= startTransform.x - 10 && playerTransform.x <= startTransform.x + 12 &&
				playerTransform.y >= startTransform.y - 10 && playerTransform.y <= startTransform.y + 12) {
					playerTransform = startTransform;
					addClone(player.getStack());
					player.getStack().clear();
					for (int i = 1; i < cloneComponentStack.size() + 1; i++){
						cloneComponentStack.get(i-1).coord.x = startTransform.x;
						cloneComponentStack.get(i-1).coord.y = startTransform.y;
						cloneComponentStack.get(i-1).frame = i * -60;
					}
		}

		checkWin();
		followCamera();
	}
}
