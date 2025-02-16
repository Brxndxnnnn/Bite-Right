package io.github.some_example_name.lwjgl3.abstract_engine.scene;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.some_example_name.lwjgl3.abstract_engine.audio.AudioPlayer;
import io.github.some_example_name.lwjgl3.application_classes.PauseScene;
import io.github.some_example_name.lwjgl3.application_classes.PlayScene;

public class SceneManager {
	private Scene currentScene;
	private PlayScene storedPlayScene; // Store PlayScene when pausing
	private AudioPlayer audio;

	public SceneManager() {
		audio = new AudioPlayer();
	}

	//! NEED TO CHANGE TO A GENERIC IMPLEMENTATION
	public void changeScene(Scene scene) {
		// From PlayScene to PauseScene
        if (scene instanceof PauseScene && currentScene instanceof PlayScene) {
            // Store PlayScene when transitioning to PauseScene
            storedPlayScene = (PlayScene) currentScene;
        }
		// From PauseScene to PlayScene
		else if (scene instanceof PlayScene && currentScene instanceof PauseScene) {
            // Restore the stored PlayScene instead of creating a new one
            scene = storedPlayScene;
            storedPlayScene = null;// Clear reference after restoring

        }

		// Else just dispose as per normal
		else if (currentScene != null) {
            // Dispose only if not transitioning between Play and Pause
            currentScene.dispose();
        }

        currentScene = scene;

        audioChecker();
    }

    public void audioChecker() {
		audio.stopAudio();
		// Checks if Audio is intiatlised, currentScene is not null, and there is a audio file path for the scene
        if (audio != null && currentScene != null && currentScene.getAudioFilePath() != null) {
            audio.playAudio(currentScene.getAudioFilePath());
            System.out.println("Track Playing: " + currentScene.getAudioFilePath());
        } else {
            System.out.println("No audio available in the scene.");
        }
    }

	public void render(SpriteBatch batch) {
		currentScene.render(batch);
	}

	public void resize(int width, int height) {
		currentScene.getStage().getViewport().update(width, height, true);
	}
}
