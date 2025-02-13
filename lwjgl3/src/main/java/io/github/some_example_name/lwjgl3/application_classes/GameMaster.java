package io.github.some_example_name.lwjgl3.application_classes;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.some_example_name.lwjgl3.abstract_engine.audio.Audio;
import io.github.some_example_name.lwjgl3.abstract_engine.io.Keyboard;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneManager;
public class GameMaster extends ApplicationAdapter {
	
    private SceneManager sceneManager;
    private SpriteBatch batch;
    private Audio audio;
	private Keyboard keyboard;

    @Override
    public void create(){
        batch = new SpriteBatch();
        sceneManager = new SceneManager(batch);
		audio = new Audio();
		keyboard = new Keyboard();
		keyboard.addClassBindings(audio);
		audio.playAudio("audio1.mp3", 60);
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
    	audio.dispose();
    }

}
