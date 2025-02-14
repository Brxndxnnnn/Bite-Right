package io.github.some_example_name.lwjgl3.application_classes;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.some_example_name.lwjgl3.abstract_engine.io.Keyboard;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneManager;
public class GameMaster extends ApplicationAdapter {
	
    private SceneManager sceneManager;
    private SpriteBatch batch;
	private Keyboard keyboard;

    @Override
    public void create(){
        sceneManager = new SceneManager();
        batch = new SpriteBatch();
		keyboard = new Keyboard();
		// keyboard.addClassBindings(audio);
		// audio.playAudio("test.mp3", 60);
		// System.out.printf("volume %d\n", audio.getVolume());
    }

    @Override
    public void render(){		
		batch.begin();
        sceneManager.render(batch);
		batch.end();

        keyboard.handleInput();
    }

    @Override
    public void dispose(){
        // dispose scenemanager here if needed
    }
    
    @Override
	public void resize(int width, int height) {
    	sceneManager.resize(width, height);
	}

}
