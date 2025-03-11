package io.github.some_example_name.lwjgl3.abstract_engine.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public abstract class Scene {
//	private String audioFilePath;
	protected Stage stage;
	protected Skin skin;

	// Constructor
	public Scene(SceneManager sceneManager) {
//		audioFilePath = null;
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		skin = new Skin(Gdx.files.internal("pixthulhu/skin/pixthulhu-ui.json"));
	}

	// Getter for AudioFilePath
//	public String getAudioFilePath() {
//		return audioFilePath;
//	}

	// Setter for AudioFilePath
//	public void setAudioFilePath(String audioFilePath){
//		this.audioFilePath = audioFilePath;
//	}

	// This function disposes the Scene
	public void dispose() {
		stage.dispose();
		skin.dispose();
	}

	public abstract void render(SpriteBatch batch);
}
