package io.github.some_example_name.lwjgl3.application_classes.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

import io.github.some_example_name.lwjgl3.abstract_engine.collision.Collidable;
import io.github.some_example_name.lwjgl3.abstract_engine.collision.Removable;
import io.github.some_example_name.lwjgl3.abstract_engine.entity.MovableEntity;
import io.github.some_example_name.lwjgl3.abstract_engine.movement.AIMovable;

public class Nutritionist extends MovableEntity implements Collidable, AIMovable, Removable {
    private float directionChangeTimer = 0;
    private static final float DIRECTION_CHANGE_INTERVAL = 2.0f; // seconds between random direction changes
    private static float MOVEMENT_SPEED = 100f; // Speed for nutritionist movement
    private String funFact;
    private boolean toBeRemoved;

    private Vector2 previousDirection; // Track previous direction to avoid 180-degree turns
    private long lastCollisionTime = 0; // To prevent rapid direction changes
    private static final long COLLISION_COOLDOWN = 500; // milliseconds
    private boolean inCollisionState = false; // Flag to indicate if nutritionist is responding to a collision
    private float collisionStateDuration = 0; // Time spent in collision state
    private static final float COLLISION_STATE_MAX_DURATION = 0.5f; // Max time to stay in collision state (seconds)
    
    /**
     * Returns the base speed used for nutritionist movement
     * @return The base nutritionist speed
     */
    public static float getBaseSpeed() {
        return MOVEMENT_SPEED;
    }
    
    public static void setBaseSpeed(float speed) {
        MOVEMENT_SPEED = speed;
    }

    public Nutritionist() {
        super();
        this.velocity = getRandomCardinalDirection().scl(MOVEMENT_SPEED);
        this.previousDirection = new Vector2(velocity).nor();
        this.toBeRemoved = false;
    }

    public Nutritionist(String name, Vector2 position, Texture texture, float speed, String funFact) {
        super(name, position, texture, speed);
        setBaseSpeed(speed);
        this.velocity = getRandomCardinalDirection().scl(MOVEMENT_SPEED);
        this.previousDirection = new Vector2(velocity).nor();
        this.funFact = funFact;
        this.toBeRemoved = false;
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
        
        // Update timers
        directionChangeTimer += deltaTime;
        
        // If in collision state, update collision state timer
        if (inCollisionState) {
            collisionStateDuration += deltaTime;
            if (collisionStateDuration >= COLLISION_STATE_MAX_DURATION) {
                // Exit collision state after max duration
                inCollisionState = false;
                collisionStateDuration = 0;
            } else {
                // Continue with current velocity while in collision state
                Vector2 position = getPosition();
                position.add(velocity.x * deltaTime, velocity.y * deltaTime);
                return; // Skip normal movement logic while in collision state
            }
        }
        
        // Move randomly throughout the maze
        moveRandomly(deltaTime);
        
        // Store normalized direction for collision resolution
        previousDirection.set(velocity).nor();
    }
    
    /**
     * Move randomly throughout the maze
     */
    private void moveRandomly(float deltaTime) {
        // Only change direction at intervals or when velocity is zero
        if (directionChangeTimer >= DIRECTION_CHANGE_INTERVAL || velocity.isZero()) {
            // Try to change direction
            changeDirection();
            directionChangeTimer = 0; // Reset timer
        }
        
        // Update position based on current velocity
        Vector2 position = getPosition();
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
    }
    
    /**
     * Changes the nutritionist's direction while avoiding 180-degree turns
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
        com.badlogic.gdx.utils.Array<Vector2> validDirections = new com.badlogic.gdx.utils.Array<>();
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
            velocity.set(newDirection.x * MOVEMENT_SPEED, newDirection.y * MOVEMENT_SPEED);
        }
    }
    
    /**
     * Forces the nutritionist to choose a new direction that isn't the current one
     * Used after collisions to prevent getting stuck
     */
    public void forceDirectionChange() {
        // Check if we're in cooldown period to prevent rapid changes
        long currentTime = TimeUtils.millis();
        if (currentTime - lastCollisionTime < COLLISION_COOLDOWN) {
            return;
        }
        
        lastCollisionTime = currentTime;
        
        // Get current direction
        Vector2 currentDir = new Vector2(velocity).nor();
        
        // Available cardinal directions
        Vector2[] cardinalDirections = {
            new Vector2(1, 0),  // Right
            new Vector2(-1, 0), // Left
            new Vector2(0, 1),  // Up
            new Vector2(0, -1)  // Down
        };
        
        // Find perpendicular directions (to avoid turning back)
        com.badlogic.gdx.utils.Array<Vector2> perpendicularDirs = new com.badlogic.gdx.utils.Array<>();
        for (Vector2 dir : cardinalDirections) {
            // Only consider directions perpendicular to current
            if (dir.x * currentDir.x == 0 && dir.y * currentDir.y == 0 &&
                !(dir.x == -currentDir.x && dir.y == -currentDir.y)) {
                perpendicularDirs.add(new Vector2(dir));
            }
        }
        
        // Choose a random perpendicular direction if available
        if (perpendicularDirs.size > 0) {
            int randomIndex = MathUtils.random(perpendicularDirs.size - 1);
            Vector2 newDirection = perpendicularDirs.get(randomIndex);
            
            // Set new velocity based on direction
            velocity.set(newDirection.x * MOVEMENT_SPEED, newDirection.y * MOVEMENT_SPEED);
            previousDirection.set(newDirection);
            
            // Reset direction change timer
            directionChangeTimer = 0;
        }
    }
    
    @Override
    public Rectangle getBoundingBox() {
        // Use the custom width and height if set, otherwise use texture dimensions
        float boxWidth = getWidth() > 0 ? getWidth() : getTexture().getWidth();
        float boxHeight = getHeight() > 0 ? getHeight() : getTexture().getHeight();
        return new Rectangle(getPosition().x, getPosition().y, boxWidth, boxHeight);
    }

    /**
     * Sets the collision state of the nutritionist
     * @param inCollision Whether the nutritionist is in a collision state
     */
    public void setCollisionState(boolean inCollision) {
        this.inCollisionState = inCollision;
        this.collisionStateDuration = 0;
    }
    
    /**
     * Checks whether the nutritionist is currently responding to a collision
     * @return True if nutritionist is in collision state
     */
    public boolean isInCollisionState() {
        return inCollisionState;
    }

    public String getFunFact(){
        return funFact;
    }

    
    @Override
    public void setToBeRemoved() {
        toBeRemoved = true;
    }

    @Override
    public boolean shouldBeRemoved() {
        return toBeRemoved;
    }
}