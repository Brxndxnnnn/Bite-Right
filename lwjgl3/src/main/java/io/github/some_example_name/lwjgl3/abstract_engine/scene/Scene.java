package io.github.some_example_name.lwjgl3.abstract_engine.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.github.some_example_name.lwjgl3.abstract_engine.audio.Audio;

public abstract class Scene {
	private String sceneName;
	private Texture backgroundImg;
	private Audio backgroundMusic;
	protected Stage stage;
	protected Skin skin;
    private Viewport viewport;
    private final Vector2 baseResolution = new Vector2(640, 480); // Base resolution for scaling

	public Scene() {
        viewport = new FitViewport(baseResolution.x, baseResolution.y);
		stage = new Stage(viewport);
		Gdx.input.setInputProcessor(stage);
		skin = new Skin(Gdx.files.internal("uiskin.json"));
	}
	
	public Scene(String sceneName, Texture backgroundImg, Audio backgroundMusic) {
		
	}
	
	public void update() {

	}
	
	public void hide() {
		
	}
	
	public void dispose() {
		stage.dispose();
		skin.dispose();
	}
	
	public Stage getStage() {
		return stage;
	}
	
	public abstract void render(SpriteBatch batch);

}
