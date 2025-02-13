package io.github.some_example_name.lwjgl3.abstract_engine.scene;

import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.some_example_name.lwjgl3.abstract_engine.entity.EntityManager;
import io.github.some_example_name.lwjgl3.application_classes.HomeScene;
import io.github.some_example_name.lwjgl3.application_classes.PlayScene;

public class SceneManager {
	private EntityManager em;
	private Map<String, Scene> scenes;
	private Scene currentScene;
	private Scene previousScene;
	
    private HomeScene homeScene;
    private PlayScene playScene;
   
	
	public SceneManager(SpriteBatch batch) {
		initialiseScenes();
        changeScene(new HomeScene(this));
	}
	
	public SceneManager(EntityManager em) {
		
	}
	
	public void initialiseScenes() {
        homeScene = new HomeScene(this);
        playScene = new PlayScene(this);  
        
	}
	
	public void addScene() {
		
	}
	
	public void changeScene(Scene newScene) {
		if (currentScene != null) {
			currentScene.dispose();
		}
		
		currentScene = newScene;
		
	}
	
	public void render(SpriteBatch batch) {
		if (currentScene != null) {
	        currentScene.render(batch);
        }
	}
	
    public void dispose() {
    	
    }
}