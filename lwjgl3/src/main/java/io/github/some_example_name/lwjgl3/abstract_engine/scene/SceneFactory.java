package io.github.some_example_name.lwjgl3.abstract_engine.scene;

import io.github.some_example_name.lwjgl3.application_classes.scenes.DifficultyScene;
import io.github.some_example_name.lwjgl3.application_classes.scenes.HomeScene;
import io.github.some_example_name.lwjgl3.application_classes.scenes.PauseScene;
import io.github.some_example_name.lwjgl3.application_classes.scenes.PlayScene;
import io.github.some_example_name.lwjgl3.application_classes.scenes.ResultsScene;
import io.github.some_example_name.lwjgl3.application_classes.scenes.TutorialScene;

public class SceneFactory {
	public Scene getScene(SceneType sceneType, SceneManager sceneManager) {
		switch (sceneType) {
	        case HOME:
	            return new HomeScene(sceneManager);
	        case TUTORIAL:
	            return new TutorialScene(sceneManager);
	        case DIFFICULTY:
	            return new DifficultyScene(sceneManager);
	        case PLAY:
	            return new PlayScene(sceneManager);
	        case PAUSE:
	            return new PauseScene(sceneManager);
	        case RESULTS:
	            return new ResultsScene(sceneManager);
	        default:
	            throw new IllegalArgumentException("Unknown SceneType: " + sceneType);
		}
	}
}
