package io.github.some_example_name.lwjgl3.abstract_engine.scene;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SceneManager {
    private Scene currentScene;
    private Scene storedScene; // Store PlayScene when pausing

    // Constructor
    public SceneManager() {}

    // This function changes the scene
    // Usage: changeScene(new PauseScene(), SceneTransitionType.PAUSE);
    //        changeScene(new PlayScene(), SceneTransitionType.NORMAL);
    public void changeScene(Scene newScene, SceneTransitionType transitionType) {
        switch (transitionType) {
            case PAUSE:
                storedScene = currentScene;
                break;

            case RESUME:
                if (storedScene != null) {
                    currentScene.dispose();
                    newScene = storedScene; // Restore PlayScene
                    storedScene = null; // Clear reference
                }
                break;

            case RESTART:
                if (storedScene != null) {
                    storedScene.dispose(); // Dispose stored PlayScene
                    storedScene = null;
                }
                if (currentScene != null) {
                    currentScene.dispose(); // Dispose PauseScene
                }
                break;

            case NORMAL:
                if (currentScene != null) {
                    currentScene.dispose(); // Dispose as usual
                }
                break;
        }

        currentScene = newScene;
    }

    // This function render the current scene
    public void render(SpriteBatch batch) {
        if (currentScene != null) {
            currentScene.render(batch);
        }
    }
}
