package io.github.some_example_name.lwjgl3.abstract_engine.scene;

import java.util.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.some_example_name.lwjgl3.abstract_engine.entity.EntityManager;

public class SceneManager {
	private EntityManager em;
	private Map<String, Scene> scenes;
	private Scene currentScene;
	private Scene previousScene;
	
	SceneManager() {
		
	}
	
	SceneManager(EntityManager em) {
		
	}
	
	public void initialiseScenes() {
		
	}
	
	public void addScene() {
		
	}
	
	public void changeScene() {
		
	}
	
	public void render(SpriteBatch sb) {
		
	}
	
    public void dispose() {
    	
    }
}