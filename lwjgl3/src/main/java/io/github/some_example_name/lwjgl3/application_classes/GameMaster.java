package io.github.some_example_name.lwjgl3.application_classes;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import io.github.some_example_name.lwjgl3.abstract_engine.audio.Audio;
import io.github.some_example_name.lwjgl3.abstract_engine.io.Keyboard;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneManager;
public class GameMaster extends ApplicationAdapter {
    SceneManager sceneManager;
	private Audio audio;
	private Keyboard keyboard;

    @Override
    public void create(){
        sceneManager = new SceneManager();
		audio = new Audio();
		keyboard = new Keyboard();
		keyboard.addClassBindings(audio);
		audio.playAudio("test.mp3", 60);
		System.out.printf("volume %d\n", audio.getVolume());

    }

    @Override
    public void render(){
        sceneManager.render();
        keyboard.handleInput();
    }

    @Override
    public void dispose(){
        // dipose scenemanager here if needed
    	audio.dispose();
    }

}
