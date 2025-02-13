package io.github.some_example_name.lwjgl3.abstract_engine.scene;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.some_example_name.lwjgl3.application_classes.HomeScene;
import io.github.some_example_name.lwjgl3.application_classes.PauseScene;
import io.github.some_example_name.lwjgl3.application_classes.PlayScene;

public class SceneManager {
	private Map<String, Scene> scenes;
	private String currentSceneKey;
	
	public SceneManager() {
		scenes = new HashMap<>();
		currentSceneKey = "HOME";
		initialiseScenes();
	}
	
	public void initialiseScenes() {
	   addScene("HOME", new HomeScene(this));
	   addScene("PLAY", new PlayScene(this));
	   addScene("PAUSE", new PauseScene(this));
	}
	
	public void addScene(String key, Scene scene) {
		scenes.put(key, scene);
	}
	
	public void changeScene(String sceneName) {
		currentSceneKey = sceneName;
	}
	
	public void render(SpriteBatch batch) {
		scenes.get(currentSceneKey).render(batch);
	}
	
    public void dispose() {
    	for (Scene scene : scenes.values()) {
			scene.dispose();
		}
    }
    
	public void resize(int width, int height) {
		scenes.get(currentSceneKey).getStage().getViewport().update(width, height, true);
	}
}