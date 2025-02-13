package io.github.some_example_name.lwjgl3.abstract_engine.io;

import java.util.ArrayList;
import java.util.List;

public class Mouse {
    private float x, y;
    private List<InputObserver> observers;

    // Constructor
    public Mouse() {
        observers = new ArrayList<>();
    }

    // Add observer to listen for mouse events
    public void addObserver(InputObserver observer) {
        observers.add(observer);
    }

    // Remove observer
    public void removeObserver(InputObserver observer) {
        observers.remove(observer);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public boolean isPressed() {
        // Add logic to check if mouse is pressed
        return false;
    }

    // create functions for using the mouse first before using listeners
    
//    // Notify observers when mouse is moved
//    public void notifyObserversMouseMoved() {
//        for (InputObserver observer : observers) {
//            observer.onMouseMoved(x, y);
//        }
//    }
//
//    // Notify observers when mouse button is pressed
//    public void notifyObserversMousePressed(int buttonCode) {
//        for (InputObserver observer : observers) {
//            observer.onMousePressed(buttonCode);
//        }
//    }
//
//    // Notify observers when mouse button is released
//    public void notifyObserversMouseReleased(int buttonCode) {
//        for (InputObserver observer : observers) {
//            observer.onMouseReleased(buttonCode);
//        }
//    }
//
//    // Update mouse position (this could be called in the update method)
//    public void setPosition(float x, float y) {
//        this.x = x;
//        this.y = y;
//        notifyObserversMouseMoved();
//    }
}
