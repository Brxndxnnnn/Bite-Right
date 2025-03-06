package io.github.some_example_name.lwjgl3.abstract_engine.audio;

public interface iAudio {
	void playAudio(String fileName);
	void playAudio(String fileName, Boolean loopAudio);
	void pauseAudio();
	void resumeAudio();
	void stopAudio();
	void setVolume(float volume);
	void loopAudio(boolean loop);
	void disposeAudio();
	float getVolume();
}
