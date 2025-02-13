package io.github.some_example_name.lwjgl3.abstract_engine.io;

public class IOManager {
    private Keyboard keyboard;
    private Mouse mouse;

    public IOManager(Keyboard keyboard, Mouse mouse) {
        this.keyboard = keyboard;
        this.mouse = mouse;
    }

    public void updateInput() {
        // Handle input for keyboard
        keyboard.handleInput();

        // remove comment once mouse has functionality
        //mouse.handleInput();
    }

    // Add observers for keyboard
    public void addKeyboardObserver(InputObserver observer) {
        keyboard.addObserver(observer);
    }

    public void removeKeyboardObserver(InputObserver observer) {
        keyboard.removeObserver(observer);
    }

    // Add observers for mouse
    public void addMouseObserver(InputObserver observer) {
        mouse.addObserver(observer);
    }

    public void removeMouseObserver(InputObserver observer) {
        mouse.removeObserver(observer);
    }
}
