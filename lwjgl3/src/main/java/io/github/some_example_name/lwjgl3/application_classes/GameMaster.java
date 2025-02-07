package io.github.some_example_name.lwjgl3.application_classes;

import com.badlogic.gdx.ApplicationAdapter;

import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneManager;
public class GameMaster extends ApplicationAdapter {
    SceneManager sceneManager;

    @Override
    public void create(){
        sceneManager = new SceneManager();
    }

    @Override
    public void render(){
        sceneManager.render();
    }

    @Override
    public void dispose(){
        // dipose scenemanager here if needed
    }
}
