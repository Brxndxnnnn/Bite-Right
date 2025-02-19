package io.github.some_example_name.lwjgl3.abstract_engine.io;

public class IOManager {
    private Keyboard keyboard;
    private Mouse mouse;

    public IOManager() {
        this.keyboard = new Keyboard();
        this.mouse = new Mouse();
    }
    
    public Keyboard getKeyboard() {
    	return keyboard;
    }
    
    public Mouse getMouse() {
    	return mouse;
    }

    // listens for input for devices
    public void handleInput() {
        keyboard.handleInput();  
        mouse.handleInput(); 
    }

    // usage addIOBind(kb, key, function, true(press once)/false(hold) (default is false))
    public void addIOBind(InputDevice device, int keyOrButton, Runnable action, boolean isJustPressed) {
        device.addBind(keyOrButton, action, isJustPressed);
    }

    public void removeIOBind(InputDevice device, int keyOrButton) {
        device.removeBind(keyOrButton);
    }
}
