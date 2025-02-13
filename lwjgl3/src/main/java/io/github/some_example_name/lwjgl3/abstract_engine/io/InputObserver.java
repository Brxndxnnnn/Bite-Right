package io.github.some_example_name.lwjgl3.abstract_engine.io;

public interface InputObserver {
	void keyboardEvent(int key, boolean isPressed);
    void mouseEvent(float x, float y, int button, boolean isPressed); 
}
