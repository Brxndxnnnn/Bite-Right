package io.github.some_example_name.lwjgl3.scene;


import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SceneManager {
    private Map<String, Scene> scenes;
    private Scene currentScreen;

    public SceneManager() {
        scenes = new HashMap<>();
    }

    public void addScreen(String name, Scene scene) {
        scenes.put(name, scene);
    }

    public void changeScreen(String name) {
        if (currentScreen != null) {
            currentScreen.hide();
        }
        currentScreen = scenes.get(name);
        if (currentScreen != null) {
            currentScreen.show();
        }
    }

    public void renderCurrentScreen(float delta, SpriteBatch batch) {
        if (currentScreen != null) {
            currentScreen.update(delta);
            currentScreen.render(delta, batch);
        }
    }

    public void dispose() {
        for (Scene scene : scenes.values()) {
            scene.dispose();
        }
    }
}
