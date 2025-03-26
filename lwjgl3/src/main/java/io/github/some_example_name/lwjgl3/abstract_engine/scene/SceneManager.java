package io.github.some_example_name.lwjgl3.abstract_engine.scene;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Abstract base class for scene management
 */
public abstract class SceneManager {
    protected Scene currentScene;
    protected Scene storedScene; // For pausing/resuming scenarios

    /**
     * Constructor
     */
    public SceneManager() {}

    /**
     * Changes the current scene with the specified transition type
     * 
     * @param sceneType The type of scene to change to
     * @param transitionType How the transition should be handled
     */
    public abstract void changeScene(SceneType sceneType, SceneTransitionType transitionType);

    /**
     * Gets a scene of the specified type
     * 
     * @param sceneType The type of scene to create
     * @return A new instance of the requested scene
     */
    protected abstract Scene getScene(SceneType sceneType);

    /**
     * Renders the current scene using the provided batch
     * 
     * @param batch The sprite batch to use for rendering
     */
    public void render(SpriteBatch batch) {
        if (currentScene != null) {
            currentScene.render(batch);
        }
    }

    /**
     * Handles scene transitions based on the specified transition type
     * 
     * @param newScene The new scene to transition to
     * @param transitionType The type of transition to perform
     */
    protected void handleSceneTransition(Scene newScene, SceneTransitionType transitionType) {
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
                    currentScene.dispose(); // Dispose current scene
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
    
    /**
     * Cleans up resources when the manager is no longer needed
     */
    public void dispose() {
        if (currentScene != null) {
            currentScene.dispose();
        }
        
        if (storedScene != null) {
            storedScene.dispose();
        }
    }
}