package io.github.some_example_name.lwjgl3.abstract_engine.io;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input.Keys;
//import io.github.some_example_name.lwjgl3.abstract_engine.audio.Audio;
//import com.badlogic.gdx.utils.IntMap;
//
//public class Keyboard {
//    private Audio audio;
//    private IntMap<Runnable> keyBindings;
//
//    // Constructor
//    public Keyboard(Audio audio) {
//        this.audio = audio;
//        keyBindings = new IntMap<>();
//        initializeBindings();
//    }
//
//    // Initialize key bindings using a Map of Keys to Runnable actions
//    private void initializeBindings() {
//        keyBindings.put(Keys.D, () -> {
//            audio.setVolume(audio.getVolume() + 5);
//            System.out.printf("volume %d\n", audio.getVolume());
//        });
//
//        keyBindings.put(Keys.A, () -> {
//            audio.setVolume(audio.getVolume() - 5);
//            System.out.printf("volume %d\n", audio.getVolume());
//        });
//
//        keyBindings.put(Keys.S, () -> audio.stopAudio());
//        
//        keyBindings.put(Keys.W, () -> audio.pauseAudio());
//
//        keyBindings.put(Keys.Q, () -> audio.resumeAudio());
//
//        keyBindings.put(Keys.NUM_1, () -> audio.loopAudio(true));
//
//        keyBindings.put(Keys.NUM_2, () -> audio.loopAudio(false));
//    }
//
//    // Handle input by checking for key presses and running the associated action
//    public void handleInput() {
//        // Iterate through all key bindings and run corresponding action if key is pressed
//    	 for (IntMap.Entry<Runnable> entry : keyBindings.entries()) {
//             if (Gdx.input.isKeyPressed(entry.key)) {
//                 entry.value.run();  // Execute the corresponding action
//             }
//         }
//    }
//    
//    public void addKeyBinding(int key, Runnable action) {
//        keyBindings.put(key, action);
//    }
//
//    public void removeKeyBinding(int key) {
//        keyBindings.remove(key);
//    }
//}

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.utils.IntMap;

import io.github.some_example_name.lwjgl3.abstract_engine.audio.Audio;

public class Keyboard {
    private IntMap<Runnable> keyBindings;

    // Constructor
    public Keyboard() {
        keyBindings = new IntMap<>();
    }

    // Handle input by checking for key presses and running the associated action
    public void handleInput() {
        for (IntMap.Entry<Runnable> entry : keyBindings.entries()) {
            if (Gdx.input.isKeyPressed(entry.key)) {
                entry.value.run();  // Execute the corresponding action
            }
        }
    }

    public void addKeyBinding(int key, Runnable action) {
        keyBindings.put(key, action);
    }

    public void removeKeyBinding(int key) {
        keyBindings.remove(key);
    }

    // General method for adding key bindings dynamically
    public void addClassBindings(Object allclass) {
        if (allclass instanceof Audio) {
            Audio audio = (Audio) allclass;
            addKeyBinding(Keys.D, () -> {
                audio.setVolume(audio.getVolume() + 5);
                System.out.printf("volume %d\n", audio.getVolume());
            }); 
            addKeyBinding(Keys.A, () -> {
                audio.setVolume(audio.getVolume() - 5);
                System.out.printf("volume %d\n", audio.getVolume());
            }); 
            addKeyBinding(Keys.S, () -> audio.stopAudio());  
            addKeyBinding(Keys.W, () -> audio.pauseAudio()); 
            addKeyBinding(Keys.Q, () -> audio.resumeAudio()); 
            addKeyBinding(Keys.NUM_1, () -> audio.loopAudio(true)); 
            addKeyBinding(Keys.NUM_2, () -> audio.loopAudio(false));
        } 
//        else if (system instanceof otherclass) {

//        }

    }
}



