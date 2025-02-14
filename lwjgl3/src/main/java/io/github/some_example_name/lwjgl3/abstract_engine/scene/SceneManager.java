package io.github.some_example_name.lwjgl3.abstract_engine.scene;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.some_example_name.lwjgl3.application_classes.HomeScene;

public class SceneManager {
	private Scene currentScene;
	
	public SceneManager() {
		// currentScene = new HomeScene(this);
		changeScene(new HomeScene(this));
	}
	
	public void changeScene(Scene scene) {
		if (currentScene != null) {
			currentScene.dispose();
		}

		currentScene = scene;
	}
	
	public void render(SpriteBatch batch) {
		currentScene.render(batch);
	}
    
	public void resize(int width, int height) {
		currentScene.getStage().getViewport().update(width, height, true);
	}
}