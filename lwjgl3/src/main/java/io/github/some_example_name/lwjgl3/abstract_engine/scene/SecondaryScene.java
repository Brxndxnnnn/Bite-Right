package io.github.some_example_name.lwjgl3.abstract_engine.scene;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class SecondaryScene extends Scene {

	public SecondaryScene(SceneManager sceneManager) {
		super(sceneManager);
	}

	public abstract void render(SpriteBatch batch);

}
