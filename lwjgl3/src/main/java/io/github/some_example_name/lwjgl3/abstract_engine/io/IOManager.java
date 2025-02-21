package io.github.some_example_name.lwjgl3.abstract_engine.io;

public class IOManager {
    private Keyboard keyboard;
    private Mouse mouse;

    // Default Constructor
    public IOManager() {
        this.keyboard = new Keyboard();
        this.mouse = new Mouse();
    }
    
    // Getter for Keyboard
    public Keyboard getKeyboard() {
    	return keyboard;
    }
    
    // Getter for Mouse
    public Mouse getMouse() {
    	return mouse;
    }

    // This function listens for input for devices
    public void handleInput() {
        keyboard.handleInput();  
        mouse.handleInput(); 
    }

    // This function set keybinds to action
    // Usage: addIOBind(kb, key, function, true(press once)/false(hold) (default is false))
    public void addIOBind(InputDevice device, int keyOrButton, Runnable action, boolean isJustPressed) {
        device.addBind(keyOrButton, action, isJustPressed);
    }

    // This function remove keybinds
    public void removeIOBind(InputDevice device, int keyOrButton) {
        device.removeBind(keyOrButton);
    }
}
