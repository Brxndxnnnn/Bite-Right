package io.github.some_example_name.lwjgl3.scene;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Scene {
    protected SceneManager sceneManager;

    public Scene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public abstract void show();       // Called when the screen becomes active
    public abstract void render(float delta, SpriteBatch batch); // Render logic
    public abstract void update(float delta); // Update logic
    public abstract void hide();       // Called when the screen is deactivated
    public abstract void dispose();    // Clean up resources
}
