package io.github.some_example_name.lwjgl3.abstract_engine.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class AudioPlayer {
	private float volume;
	private Music sound;

	public AudioPlayer() {
		sound = null;
		volume = 100;
	}

	// Method Overloading
	public void playAudio(String fileName) {
		sound = Gdx.audio.newMusic(Gdx.files.internal(fileName));
		sound.setVolume(1);
		sound.play();
	}

	// Method Overloading
	public void playAudio(String fileName, Boolean loopAudio) {
		sound = Gdx.audio.newMusic(Gdx.files.internal(fileName));
		loopAudio(loopAudio);
		sound.setVolume(1);
		sound.play();
		System.out.println("Play Audio");
	}

	public void pauseAudio() {
        if (sound != null && sound.isPlaying()) {
            sound.pause();
            System.out.println("Pause Audio");
        }
	}

	public void resumeAudio() {
        if (sound != null && !sound.isPlaying()) {
            sound.play();
            System.out.println("Resume Audio");
        }
	}

	public void stopAudio() {
        if (sound != null) {
            sound.stop();
			disposeAudio();
            System.out.println("Stop and Dispose Audio");
        }
	}
	
	public float getVolume() {
		return volume;
	}
	
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

	public void loopAudio(boolean loop) {
		sound.setLooping(loop);
		System.out.printf("loop %s\n", loop);
	}
	
	// currently, by changing audio, the loop variable is not stored since
	// it is disposed. therefore by default it will not loop
	// modification is needed to store the loop variable
	// public void changeAudio(String fileName) {
	// 	if (sound != null) {
	// 		sound.stop();
	// 		sound.dispose();
	// 		System.out.printf("change to %s\n", fileName);

	// 	}
		
	// 	System.out.printf("volume %f\n", getVolume());
	//     playAudio(fileName, getVolume());
	// }

	public void disposeAudio() {
		sound.dispose();
	}
}
