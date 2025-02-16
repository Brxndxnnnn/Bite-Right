package io.github.some_example_name.lwjgl3.abstract_engine.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class AudioPlayer {
	private String fileName;
	private int volume;
	private Music sound;
	private boolean loop;

	public AudioPlayer() {
		sound = null;
		volume = 100;
		loop = false;
        fileName = null;
	}

	public void playAudio(String fileName) {
		sound = Gdx.audio.newMusic(Gdx.files.internal(fileName));
		sound.setVolume(1);
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

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
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
	}


	public void loopAudio(boolean loop) {
		sound.setLooping(loop);
		System.out.printf("loop %s\n", loop);
	}

	public void dispose() {
		sound.dispose();
	}
}
