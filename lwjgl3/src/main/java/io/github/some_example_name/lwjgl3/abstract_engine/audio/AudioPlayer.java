package io.github.some_example_name.lwjgl3.abstract_engine.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Array;

// Audioplayer manages basic audio features such as Playing Audio, Pausing, Setting volume, etc

public class AudioPlayer {
    private static AudioPlayer instance = null;
    private float volume;
    private Music bgm;
    private Music sfx;
    private Music sound;
    private String currentTrack;
    private boolean isSFXPlaying = false;
    private Array<Music> activeSFX;;

    // Default Constructor
    private AudioPlayer() {
        bgm = null;
        sound = null;
        volume = 100;
        activeSFX = new Array<>();
        currentTrack = null;
    }

    public static synchronized AudioPlayer getInstance() {
        if (instance == null) {
            instance = new AudioPlayer();
        }
        return instance;
    }

    public void setSceneBGM(String fileName) {
        if (fileName.equals(currentTrack)) {
            return;
        }
        stopBGM(); // Stop previous audio
        bgm = Gdx.audio.newMusic(Gdx.files.internal(fileName));
        bgm.setLooping(true);
        bgm.setVolume(volume / 100.0f);
        bgm.play();
        currentTrack = fileName;
    }


    public void playSFX(String fileName) {
        if(sfx != null) {
            sfx.stop();
            sfx.dispose();
        }

        sfx = Gdx.audio.newMusic(Gdx.files.internal(fileName));
        sfx.setLooping(false);
        sfx.setVolume(volume / 100.0f);
        sfx.play();

        activeSFX.add(sfx); // Add to active SFX list

        // Dispose the SFX after it finishes playing
        sfx.setOnCompletionListener(music -> {
            activeSFX.removeValue(music, true);
            music.dispose();
        });
    }

//	// Constructor 1 (Overloading)
//	public void playAudio(String fileName) {
//		sound = Gdx.audio.newMusic(Gdx.files.internal(fileName));
//		volume = 100;
//		sound.setVolume(volume / 100.0f);
//		sound.play();
//	}
//
//
//    public void playSFX(String fileName, Boolean loopAudio) {
//        sound = Gdx.audio.newMusic(Gdx.files.internal(fileName));
//        volume = 100;
//        sound.setVolume(volume / 100.0f);
//        sound.play();
//        System.out.println("Play Audio" + fileName);
//    }

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

    public void stopBGM() {
        if (bgm != null) {
            bgm.stop();
            bgm.dispose();
            bgm = null;
            currentTrack = null;
            System.out.println("Stop and Dispose BGM");
        }
    }

    public void stopAllAudio() {
        stopBGM();
        if (sfx != null) {
            sfx.stop();
            sfx.dispose();
            sfx = null;
        }
        isSFXPlaying = false;
    }

    // Getter for Volume
    public float getVolume() {
        return volume;
    }

    // Setter for Volume
    public void setVolume(float volume) {
        if (volume < 0) {
            volume = 0;
        } else if (volume > 100) {
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

