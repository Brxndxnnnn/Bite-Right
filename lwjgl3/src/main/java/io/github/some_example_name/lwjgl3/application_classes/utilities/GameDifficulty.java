package io.github.some_example_name.lwjgl3.application_classes.utilities;

import io.github.some_example_name.lwjgl3.abstract_engine.audio.AudioPlayer;

public class GameDifficulty {
	
    	private static GameDifficulty instance = null;
    	
	    private Difficulty difficulty = Difficulty.EASY;

    	public enum Difficulty {
	        EASY, NORMAL, HARD;
	    }
    	
	 	public static synchronized GameDifficulty getInstance() {
	        if (instance == null) {
	            instance = new GameDifficulty();
	        }
	        return instance;
	    }

	    private GameDifficulty() {}

	    public Difficulty getDifficulty() {
	        return difficulty;
	    }

	    public void setDifficulty(Difficulty difficulty) {
	        this.difficulty = difficulty;
	    }
}
