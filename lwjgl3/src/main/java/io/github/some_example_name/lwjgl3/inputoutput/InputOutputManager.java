package io.github.some_example_name.lwjgl3.inputoutput;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import io.github.some_example_name.lwjgl3.movement.MovementManager;


public class InputOutputManager {

    public enum MovementCommand {
        MOVE_UP,
        MOVE_DOWN,
        MOVE_LEFT,
        MOVE_RIGHT,
        NONE // Default or no movement
    }
    
    private MovementManager movementManager;

    public InputOutputManager(MovementManager movementManager) {
        this.movementManager = movementManager;
    }

    public void processInput() {
        MovementCommand command = getMovementCommand();
        if (command != MovementCommand.NONE) {
            // movementManager.handleMovementCommand(command);
        }
    }

    private MovementCommand getMovementCommand() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            return MovementCommand.MOVE_UP;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            return MovementCommand.MOVE_DOWN;
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            return MovementCommand.MOVE_LEFT;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            return MovementCommand.MOVE_RIGHT;
        }
        return MovementCommand.NONE;
    }
}
