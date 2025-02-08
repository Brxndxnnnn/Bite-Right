package io.github.some_example_name.lwjgl3.application_classes;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneManager;
public class GameMaster extends ApplicationAdapter {
    SceneManager sceneManager;
    SpriteBatch sb;
    
    @Override
    public void create(){
        sceneManager = new SceneManager();
    }

    @Override
    public void render(){
        sceneManager.render(sb);
    }

    @Override
    public void dispose(){
        // dispose scenemanager here if needed
    }
}
