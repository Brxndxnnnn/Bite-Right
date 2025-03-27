package io.github.some_example_name.lwjgl3.abstract_engine.movement;

// This interface is implemented by MovableEntity that is user-controlled
public interface UserMovable {
    /**
     * Moves the entity in the specified direction
     * @param direction The direction to move
     */
    void move(Direction direction);
}