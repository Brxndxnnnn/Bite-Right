package io.github.some_example_name.lwjgl3.abstract_engine.scene;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.some_example_name.lwjgl3.application_classes.scenes.HomeScene;
import io.github.some_example_name.lwjgl3.application_classes.scenes.PauseScene;
import io.github.some_example_name.lwjgl3.application_classes.scenes.PlayScene;
import io.github.some_example_name.lwjgl3.application_classes.scenes.ResultsScene;

public class SceneManager {
    private Scene currentScene;
    private Scene storedScene; // Store PlayScene when pausing

    // Constructor
    public SceneManager() {}

//    private Scene createScene(SceneType sceneType) {
//    	switch (sceneType) {
//	        case HOME:
//	            return new HomeScene(this);
//	        case PLAY:
//	            return new PlayScene(this);
//	        case SETTINGS:
//	            return new SettingsScene(this);
//	        case PAUSE:
//	            return new PauseScene(this);
//	        case RESULTS:
//	            return new ResultsScene(this);
//	        default:
//	            throw new IllegalArgumentException("Unknown SceneType: " + sceneType);
//    	}
//    }

    // This function changes the scene
    // Usage: changeScene(new PauseScene(), SceneTransitionType.PAUSE);
    //        changeScene(new PlayScene(), SceneTransitionType.NORMAL);
    public void changeScene(SceneType sceneType, SceneTransitionType transitionType) {
//        Scene newScene = createScene(sceneType);
    	SceneFactory sceneFactory = new SceneFactory();
    	Scene newScene = sceneFactory.getScene(sceneType, this);

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
