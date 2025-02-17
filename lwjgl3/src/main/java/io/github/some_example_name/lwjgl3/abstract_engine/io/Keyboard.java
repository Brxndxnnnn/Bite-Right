package io.github.some_example_name.lwjgl3.abstract_engine.io;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.IntMap;

public class Keyboard implements InputDevice {
    private IntMap<Object[]> keyBindings;

    public Keyboard() {
        keyBindings = new IntMap<>();
    }

    @Override
    public void handleInput() {
        for (IntMap.Entry<Object[]> entry : keyBindings.entries()) {
            Object[] binding = entry.value;
            Runnable action = (Runnable) binding[0];
            boolean isJustPressed = (boolean) binding[1];
            boolean isKeyPressed;
            if (isJustPressed) {
                isKeyPressed = Gdx.input.isKeyJustPressed(entry.key);
            } 
            else {
                isKeyPressed = Gdx.input.isKeyPressed(entry.key);
            }

            if (isKeyPressed) {
                action.run();  
            }
        }
    }

    @Override
    public void addIOBind(int key, Runnable action, boolean isJustPressed) {
        keyBindings.put(key, new Object[]{action, isJustPressed});
    }

    @Override
    public void removeIOBind(int key) {
        keyBindings.remove(key);
        System.out.printf("removed key %s\n", key);
    }
}

