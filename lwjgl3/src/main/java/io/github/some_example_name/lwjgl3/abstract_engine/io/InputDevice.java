package io.github.some_example_name.lwjgl3.abstract_engine.io;

public interface InputDevice {
    void handleInput();  // Method to update input for the device
    void addBind(int keyOrButton, Runnable action, boolean isJustPressed);  // Method to bind actions to keys/buttons
    void removeBind(int keyOrButton);  // Method to remove key/button bindings
}
