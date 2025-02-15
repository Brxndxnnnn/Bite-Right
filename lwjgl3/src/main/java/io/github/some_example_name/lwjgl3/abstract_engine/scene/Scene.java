package io.github.some_example_name.lwjgl3.abstract_engine.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public abstract class Scene {
	private SceneManager sceneManager;
	protected Stage stage;
	protected Skin skin;

	public Scene(SceneManager sceneManager) {
		this.sceneManager = sceneManager;
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		skin = new Skin(Gdx.files.internal("uiskin.json"));
	}

	public void dispose() {
		stage.dispose();
		skin.dispose();
	}

	public abstract void render(SpriteBatch batch);

}
