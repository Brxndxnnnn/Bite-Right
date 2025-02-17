package io.github.some_example_name.lwjgl3.abstract_engine.scene;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class PrimaryScene extends Scene {

	private Boolean isHome;
	
	public PrimaryScene(SceneManager sceneManager, Boolean isHome) {
		super(sceneManager);
		this.isHome = isHome;
	}
	
	public Boolean getIsHome() {
		return isHome;
	}

	public abstract void render(SpriteBatch batch);

}
