package io.github.some_example_name.lwjgl3.abstract_engine.scene;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.some_example_name.lwjgl3.abstract_engine.audio.Audio;
import io.github.some_example_name.lwjgl3.application_classes.HomeScene;
import io.github.some_example_name.lwjgl3.application_classes.PauseScene;
import io.github.some_example_name.lwjgl3.application_classes.PlayScene;

public class SceneManager {
	private Scene currentScene;
	private PlayScene storedPlayScene; // Store PlayScene when pausing
	private Audio audio;

    private String currentTrack = "No Audio Playing";

	public SceneManager() {
		changeScene(new HomeScene(this));
		audio = new Audio();
		audio.playAudio(currentScene.getAudioFilePath());
        System.out.printf(currentTrack = "Track Playing: " + currentScene.getAudioFilePath());
	}

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

        AudioChecker();
    }


    public void AudioChecker() {
        if (audio != null && currentScene != null && currentScene.getAudioFilePath() != null) {
            audio.stopAudio();
            audio.playAudio(currentScene.getAudioFilePath());
            System.out.println("Track Playing: " + currentScene.getAudioFilePath());
        }  else {
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
