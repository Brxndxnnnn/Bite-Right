package io.github.some_example_name.lwjgl3.abstract_engine.io;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.IntMap;

public class Mouse implements InputDevice {
    private IntMap<Object[]> mouseBindings;
    private float x;
    private float y;

    public Mouse() {
        mouseBindings = new IntMap<>();
    }

    @Override
    public void handleInput() {
        for (IntMap.Entry<Object[]> entry : mouseBindings.entries()) {
            Object[] binding = entry.value;
            Runnable action = (Runnable) binding[0];
            boolean isJustPressed = (boolean) binding[1];
            boolean isButtonPressed;
            if (isJustPressed) {
                isButtonPressed = Gdx.input.isButtonJustPressed(entry.key);
            } 
            else {
                isButtonPressed = Gdx.input.isButtonPressed(entry.key);
            }

            if (isButtonPressed) {
                action.run();  
            }
        }
    }
    
    public float getX() {
    	this.x = Gdx.input.getX();
    	return x;
    }
    
    public float getY() {
    	this.y = Gdx.input.getY();
    	return y;
    }

    @Override
    public void addBind(int button, Runnable action, boolean isJustPressed) {
        mouseBindings.put(button, new Object[]{action, isJustPressed});
    }

    @Override
    public void removeBind(int button) {
        mouseBindings.remove(button);
        System.out.printf("removed key %s\n", button);
    }
    
    public void printX() {
    	System.out.printf("x %f\n", getX());
    }
}
