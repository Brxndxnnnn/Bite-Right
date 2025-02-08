package io.github.some_example_name.lwjgl3.abstract_engine.scene;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.some_example_name.lwjgl3.abstract_engine.audio.Audio;

public abstract class Scene {
	private String sceneName;
	private Texture backgroundImg;
	private Audio backgroundMusic;
	
	public Scene() {
		
	}
	
	public Scene(String sceneName, Texture backgroundImg, Audio backgroundMusic) {
		
	}
	
	
	public void update() {
		
	}
	
	public void hide() {
			
	}
	
	public void dispose() {
		
	}
	
	public abstract void render(SpriteBatch sb);

}
