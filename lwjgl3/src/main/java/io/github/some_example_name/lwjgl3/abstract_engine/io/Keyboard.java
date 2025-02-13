package io.github.some_example_name.lwjgl3.abstract_engine.io;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.IntMap.Entry;
import java.util.ArrayList;
import java.util.List;
import io.github.some_example_name.lwjgl3.abstract_engine.audio.Audio;

public class Keyboard {
    private IntMap<KeyBinding> keyBindings;
    private List<InputObserver> observers;

    // Constructor
    public Keyboard() {
        keyBindings = new IntMap<>();
        observers = new ArrayList<>();
    }

    // Handle input by checking for key presses and running the associated action
    public void handleInput() {
        for (Entry<KeyBinding> entry : keyBindings.entries()) {
            KeyBinding keyBinding = entry.value;
            boolean isKeyPressed = false;

            if (keyBinding.isJustPressed) {
                // Use justPressed if specified
                isKeyPressed = Gdx.input.isKeyJustPressed(entry.key);
            } 
            
            else {
                // Use isPressed by default
                isKeyPressed = Gdx.input.isKeyPressed(entry.key);
            }

            if (isKeyPressed) {
                keyBinding.action.run();  // Execute the corresponding action
                notifyObservers(entry.key, isKeyPressed); // Notify observers
            }
        }
    }

    // Add a key binding with an option to choose justPressed or isPressed
    public void addKeyBinding(int key, Runnable action, boolean isJustPressed) {
    	// function is (key, function, true (press once), false (hold))
        keyBindings.put(key, new KeyBinding(action, isJustPressed)); 
    }

    // Remove key binding
    public void removeKeyBinding(int key) {
        keyBindings.remove(key);
    }

    // KeyBinding class to store the action and the flag for key press method
    private static class KeyBinding {
        Runnable action;
        boolean isJustPressed;

        KeyBinding(Runnable action, boolean isJustPressed) {
            this.action = action;
            this.isJustPressed = isJustPressed;
        }
    }

    // General method for adding key bindings dynamically
    public void addClassBindings(Object allclass) {
        if (allclass instanceof Audio) {
            Audio audio = (Audio) allclass;
            addKeyBinding(Keys.D, () -> audio.setVolume(audio.getVolume() + 5), false); 
            addKeyBinding(Keys.A, () -> audio.setVolume(audio.getVolume() - 5), false); 
            addKeyBinding(Keys.S, () -> audio.stopAudio(), true); 
            addKeyBinding(Keys.W, () -> audio.pauseAudio(), true);
            addKeyBinding(Keys.Q, () -> audio.resumeAudio(), true); 
            addKeyBinding(Keys.NUM_1, () -> audio.loopAudio(true), true); 
            addKeyBinding(Keys.NUM_2, () -> audio.loopAudio(false), true); 
            addKeyBinding(Keys.NUM_3, () -> audio.changeAudio("audio2.mp3"), true); 
        }
    }
    
    // Add an object to listen for keyboard input
    public void addObserver(InputObserver observer) {
        observers.add(observer);
    }
    
    public void removeObserver(InputObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers(int key, boolean isPressed) {
        for (InputObserver observer : observers) {
            observer.keyboardEvent(key, isPressed);
        }
    }
}
