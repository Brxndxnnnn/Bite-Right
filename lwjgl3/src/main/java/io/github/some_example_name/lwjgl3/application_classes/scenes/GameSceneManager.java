package io.github.some_example_name.lwjgl3.application_classes.scenes;

import io.github.some_example_name.lwjgl3.abstract_engine.scene.Scene;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneManager;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneTransitionType;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneType;

/**
 * Game-specific implementation of the SceneManager
 */
public class GameSceneManager extends SceneManager {
    private SceneFactory sceneFactory;
    
    /**
     * Constructor
     */
    public GameSceneManager() {
        super();
        this.sceneFactory = new SceneFactory(this);
    }
    
    /**
     * Changes the current scene with the specified transition type
     * 
     * @param sceneType The type of scene to change to
     * @param transitionType How the transition should be handled
     */
    @Override
    public void changeScene(SceneType sceneType, SceneTransitionType transitionType) {
        Scene newScene = getScene(sceneType);
        handleSceneTransition(newScene, transitionType);
    }
    
    /**
     * Gets a scene of the specified type using the scene factory
     * 
     * @param sceneType The type of scene to create
     * @return A new instance of the requested scene
     */
    @Override
    protected Scene getScene(SceneType sceneType) {
        return sceneFactory.createScene(sceneType);
    }
}