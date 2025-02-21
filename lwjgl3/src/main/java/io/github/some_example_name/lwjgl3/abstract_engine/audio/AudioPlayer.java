package io.github.some_example_name.lwjgl3.abstract_engine.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

// Audioplayer manages basic audio features such as Playing Audio, Pausing, Setting volume, etc

public class AudioPlayer {
	private float volume;
	private Music sound;

	// Default Constructor
	public AudioPlayer() {
		sound = null;
		volume = 100;
	}

	// Constructor 1 (Overloading)
	public void playAudio(String fileName) {
		sound = Gdx.audio.newMusic(Gdx.files.internal(fileName));
		volume = 100;
		sound.setVolume(volume / 100.0f); 
		sound.play();
	}

	// Constructor 2 (Overloading)
	public void playAudio(String fileName, Boolean loopAudio) {
		sound = Gdx.audio.newMusic(Gdx.files.internal(fileName));
		volume = 100;
		loopAudio(loopAudio);
		sound.setVolume(volume / 100.0f); 
		sound.play();
		System.out.println("Play Audio");
	}

	// This function pauses the Audio
	public void pauseAudio() {
        if (sound != null && sound.isPlaying()) {
            sound.pause();
            System.out.println("Pause Audio");
        }
	}

	// This function resumes the Audio
	public void resumeAudio() {
        if (sound != null && !sound.isPlaying()) {
            sound.play();
            System.out.println("Resume Audio");
        }
	}

	// This function stops and dispose the Audio
	public void stopAudio() {
        if (sound != null) {
            sound.stop();
			disposeAudio();
            System.out.println("Stop and Dispose Audio");
        }
	}
	
	// Getter for Volume
	public float getVolume() {
		return volume;
	}
	
	// Setter for Volume
	public void setVolume(float volume) {
        if (volume < 0) {
            volume = 0;
        }

        else if (volume > 100) {
            volume = 100;
        }

        this.volume = volume;

        if (sound != null) {
            sound.setVolume(volume / 100.0f); 
            
        }
        
        System.out.printf("volume %f\n", volume);
	}

	// This function loops the current Audio
	public void loopAudio(boolean loop) {
		sound.setLooping(loop);
		System.out.printf("loop %s\n", loop);
	}

	// This function disposes the current audio;
	public void disposeAudio() {
		sound.dispose();
	}
}
