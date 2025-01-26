package io.github.some_example_name.lwjgl3.scene;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuScene extends Scene{
    public MenuScene(SceneManager sceneManager){
        super(sceneManager);
    }

    @Override
    public void show() {
        System.out.println("Main Menu Screen is active.");
    }

    @Override
    public void render(float delta, SpriteBatch batch) {
        batch.begin();
        // Draw main menu elements
        System.out.println("Rendering Main Menu...");
        batch.end();

        // Example: Transition to GameplayScreen on input
        // if (/* Detect user input, e.g., Enter key */) {
        //     sceneManager.changeScreen("Gameplay");
        // }
    }

    @Override
    public void update(float delta) {
        // Handle input or animations
        System.out.println("Updating Main Menu...");
    }

    @Override
    public void hide() {
        System.out.println("Main Menu Screen is deactivated.");
    }

    @Override
    public void dispose() {
        System.out.println("Cleaning up Main Menu resources.");
    }
}
