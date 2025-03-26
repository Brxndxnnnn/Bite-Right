package io.github.some_example_name.lwjgl3.application_classes;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneTransitionType;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneType;
import io.github.some_example_name.lwjgl3.application_classes.scenes.GameSceneManager;

public class GameMaster extends ApplicationAdapter {
    
    private GameSceneManager sceneManager;
    private SpriteBatch batch;

    @Override
    public void create() {
        sceneManager = new GameSceneManager(); // Create a new instance of GameSceneManager
        sceneManager.changeScene(SceneType.HOME, SceneTransitionType.NORMAL); // Render the first scene accordingly
        batch = new SpriteBatch();        
    }

    @Override
    public void render() {
        batch.begin();
        sceneManager.render(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        if (sceneManager != null) {
            sceneManager.dispose();
        }
        
        if (batch != null) {
            batch.dispose();
        }
    }
}