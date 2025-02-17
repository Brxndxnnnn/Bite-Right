package io.github.some_example_name.lwjgl3.abstract_engine.audio;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Gdx;

public class Audio {
	private String fileName;
	private float volume;
	private Music sound;
	private boolean loop;
	
	public Audio() {
		sound = null;
		volume = 100;
		loop = false;
	}
	
	// no idea if getter and setter is useful or needed
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void playAudio(String fileName, float volume) {
		this.setFileName(fileName);
		sound = Gdx.audio.newMusic(Gdx.files.internal(fileName));
		this.volume = volume;
		sound.setVolume(volume / 100.0f);
		sound.play();
	}
	
	public void pauseAudio() {
        if (sound != null && sound.isPlaying()) {
            sound.pause();
            System.out.printf("pause\n");
        }
	}
	
	public void resumeAudio() {
        if (sound != null && !sound.isPlaying()) {
            sound.play();
            System.out.printf("play\n");
        }
	}
	
	public void stopAudio() {
        if (sound != null) {
            sound.stop();
            System.out.printf("stop\n");
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
		this.loop = loop;
		sound.setLooping(loop);
		System.out.printf("loop %s\n", this.loop);
	}
	
	// currently, by changing audio, the loop variable is not stored since
	// it is disposed. therefore by default it will not loop
	// modification is needed to store the loop variable
	public void changeAudio(String fileName) {
		if (sound != null) {
			sound.stop();
			sound.dispose();
			System.out.printf("change to %s\n", fileName);

		}
		
		System.out.printf("volume %f\n", getVolume());
	    playAudio(fileName, getVolume());
	}
	
	public void dispose() {
		sound.dispose();
	}


}
