package io.github.some_example_name.lwjgl3.application_classes.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import io.github.some_example_name.lwjgl3.abstract_engine.collision.Collidable;
import io.github.some_example_name.lwjgl3.abstract_engine.entity.MovableEntity;
import io.github.some_example_name.lwjgl3.abstract_engine.movement.AIMovable;
import io.github.some_example_name.lwjgl3.application_classes.MazeGenerator;

public class NonPlayableCharacter extends MovableEntity implements Collidable, AIMovable {
    private float directionChangeTimer = 0;
    private static final float DIRECTION_CHANGE_INTERVAL = 1.0f; // changes direction every 1 sec
    private static final float GHOST_SPEED = 150f; // Speed for ghost movement
    private Vector2 previousDirection; // Track previous direction to avoid 180-degree turns

    public NonPlayableCharacter() {
        super();
        this.velocity = getRandomCardinalDirection().scl(GHOST_SPEED);
        this.previousDirection = new Vector2(velocity).nor();
    }

    public NonPlayableCharacter(String name, Vector2 position, Texture texture) {
        super(name, position, texture);
        this.velocity = getRandomCardinalDirection().scl(GHOST_SPEED);
        this.previousDirection = new Vector2(velocity).nor();
    }

    /**
     * Returns a random cardinal direction (up, down, left, right)
     * @return Vector2 representing the direction
     */
    private Vector2 getRandomCardinalDirection() {
        int direction = MathUtils.random(3); // 0-3 for four directions
        switch (direction) {
            case 0: return new Vector2(1, 0);  // Right
            case 1: return new Vector2(-1, 0); // Left
            case 2: return new Vector2(0, 1);  // Up
            case 3: return new Vector2(0, -1); // Down
            default: return new Vector2(1, 0); // Default right
        }
    }
    
    @Override
    public void moveAIControlled() {
        float deltaTime = com.badlogic.gdx.Gdx.graphics.getDeltaTime(); 
        directionChangeTimer += deltaTime;

        // Update position based on current velocity
        Vector2 position = getPosition();
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        
        // Store normalized direction for collision resolution
        previousDirection.set(velocity).nor();

        // Change direction at intervals or when needed (handled in collision resolution)
        if (directionChangeTimer >= DIRECTION_CHANGE_INTERVAL) {
            // Try to change direction
            changeDirection();
            directionChangeTimer = 0; // Reset timer
        }
    }
    
    /**
     * Changes the ghost's direction while avoiding 180-degree turns
     */
    public void changeDirection() {
        // Available cardinal directions
        Vector2[] cardinalDirections = {
            new Vector2(1, 0),  // Right
            new Vector2(-1, 0), // Left
            new Vector2(0, 1),  // Up
            new Vector2(0, -1)  // Down
        };
        
        // Create list of valid directions (avoiding 180-degree turns)
        Array<Vector2> validDirections = new Array<>();
        for (Vector2 dir : cardinalDirections) {
            // Don't allow 180-degree turns (prevents "stuttering")
            if (!(dir.x == -previousDirection.x && dir.y == -previousDirection.y)) {
                validDirections.add(new Vector2(dir));
            }
        }
        
        // Choose a random valid direction
        if (validDirections.size > 0) {
            int randomIndex = MathUtils.random(validDirections.size - 1);
            Vector2 newDirection = validDirections.get(randomIndex);
            
            // Set new velocity based on direction
            velocity.set(newDirection.x * GHOST_SPEED, newDirection.y * GHOST_SPEED);
        }
    }
    
    /**
     * Forces the ghost to choose a new direction that isn't the current one
     * Used after collisions to prevent getting stuck
     */
    public void forceDirectionChange() {
        // Reset timer and force a direction change
        directionChangeTimer = DIRECTION_CHANGE_INTERVAL;
        moveAIControlled();
    }
    
    @Override
    public Rectangle getBoundingBox() {
        // Use the custom width and height if set, otherwise use texture dimensions
        float boxWidth = getWidth() > 0 ? getWidth() : getTexture().getWidth();
        float boxHeight = getHeight() > 0 ? getHeight() : getTexture().getHeight();
        return new Rectangle(getPosition().x, getPosition().y, boxWidth, boxHeight);
    }
}