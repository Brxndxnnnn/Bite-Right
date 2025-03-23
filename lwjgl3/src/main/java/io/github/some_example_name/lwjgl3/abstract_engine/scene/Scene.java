package io.github.some_example_name.lwjgl3.abstract_engine.scene;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Scene {
	public void render(SpriteBatch batch);
    public void dispose();
}
