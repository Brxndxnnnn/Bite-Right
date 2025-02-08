package io.github.some_example_name.lwjgl3.application_classes;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneManager;
public class GameMaster extends ApplicationAdapter {
	
    private SceneManager sceneManager;
    private SpriteBatch batch;
    
    @Override
    public void create(){
        batch = new SpriteBatch();
        sceneManager = new SceneManager(batch);
    }

    @Override
    public void render(){		
		batch.begin();

        sceneManager.render(batch);
		batch.end();

    }

    @Override
    public void dispose(){
        // dispose scenemanager here if needed
    }
}
