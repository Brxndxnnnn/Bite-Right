package io.github.some_example_name.lwjgl3.application_classes.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import io.github.some_example_name.lwjgl3.abstract_engine.collision.Collidable;
import io.github.some_example_name.lwjgl3.abstract_engine.entity.Entity;
import io.github.some_example_name.lwjgl3.abstract_engine.entity.MovableEntity;
import io.github.some_example_name.lwjgl3.abstract_engine.movement.AIMovable;

public class Enemy extends MovableEntity implements Collidable, AIMovable {
    private float directionChangeTimer = 0;
    private static final float DIRECTION_CHANGE_INTERVAL = 1.0f; // seconds between random direction changes
    private static float GHOST_SPEED = 150f; // Speed for ghost movement
    
    /**
     * Returns the base speed used for ghost movement
     * @return The base ghost speed
     */
    public static float getBaseSpeed() {
        return GHOST_SPEED;
    }
    public static void setBaseSpeed(float speed) {
        GHOST_SPEED = speed;
    }
    
    private Vector2 previousDirection; // Track previous direction to avoid 180-degree turns
    private long lastCollisionTime = 0; // To prevent rapid direction changes
    private static final long COLLISION_COOLDOWN = 500; // milliseconds
    private boolean inCollisionState = false; // Flag to indicate if ghost is responding to a collision
    private float collisionStateDuration = 0; // Time spent in collision state
    private static final float COLLISION_STATE_MAX_DURATION = 0.5f; // Max time to stay in collision state (seconds)
    
    
    // Ghost behavior state
    private Entity targetEntity; // The entity this ghost will chase
    private float chaseChance = 0.7f; // 70% chance to chase when making decisions
    private GhostBehavior currentBehavior = GhostBehavior.RANDOM; // Default behavior
    private float behaviorTimer = 0;
    private static final float BEHAVIOR_CHANGE_INTERVAL = 5.0f; // seconds before considering behavior change
    
    // Enumeration for ghost behaviors
    public enum GhostBehavior {
        RANDOM,  // Move randomly
        CHASE,   // Chase the player
        SCATTER  // Scatter to predefined corner
    }

    public Enemy() {
        super();
        this.velocity = getRandomCardinalDirection().scl(GHOST_SPEED);
        this.previousDirection = new Vector2(velocity).nor();
    }

    public Enemy(String name, Vector2 position, Texture texture, float speed) {
        super(name, position, texture, speed);
    	if (speed != 0) {
        	setBaseSpeed(speed);
    	}
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
    
    /**
     * Sets the target entity for this ghost to chase
     * @param target The entity to chase
     */
    public void setTargetEntity(Entity target) {
        this.targetEntity = target;
    }
    
    @Override
    public void moveAIControlled() {
        float deltaTime = com.badlogic.gdx.Gdx.graphics.getDeltaTime(); 
        
        // Update timers
        behaviorTimer += deltaTime;
        directionChangeTimer += deltaTime;
        
        // If in collision state, update collision state timer
        if (inCollisionState) {
            collisionStateDuration += deltaTime;
            if (collisionStateDuration >= COLLISION_STATE_MAX_DURATION) {
                // Exit collision state after max duration
                inCollisionState = false;
                collisionStateDuration = 0;
                
                // Return to normal behavior
                decideBehavior();
            } else {
                // Continue with current velocity while in collision state
                Vector2 position = getPosition();
                position.add(velocity.x * deltaTime, velocity.y * deltaTime);
                return; // Skip normal movement logic while in collision state
            }
        }
        
        // Consider changing behavior
        if (behaviorTimer >= BEHAVIOR_CHANGE_INTERVAL) {
            decideBehavior();
            behaviorTimer = 0;
        }
        
        // Update movement based on current behavior
        switch (currentBehavior) {
            case CHASE:
                if (targetEntity != null) {
                    moveTowardsTarget(deltaTime);
                } else {
                    moveRandomly(deltaTime);
                }
                break;
            case SCATTER:
                // In a full implementation, each ghost would have a specific corner to move to
                moveRandomly(deltaTime);
                break;
            case RANDOM:
            default:
                moveRandomly(deltaTime);
                break;
        }
        
        // Store normalized direction for collision resolution
        previousDirection.set(velocity).nor();
    }
    
    /**
     * Decide which behavior to use
     */
    private void decideBehavior() {
        float roll = MathUtils.random();
        
        if (targetEntity != null && roll < chaseChance) {
            currentBehavior = GhostBehavior.CHASE;
        } else if (roll < 0.85f) { // 15% chance to scatter if not chasing
            currentBehavior = GhostBehavior.SCATTER;
        } else {
            currentBehavior = GhostBehavior.RANDOM;
        }
    }
    
    /**
     * Move towards the target entity
     */
    private void moveTowardsTarget(float deltaTime) {
        if (targetEntity == null) return;
        
        // Get direction to target
        Vector2 targetPos = targetEntity.getPosition();
        Vector2 myPos = getPosition();
        
        // Find the direction vector to the target
        Vector2 direction = new Vector2();
        
        // Simple implementation: only move in cardinal directions (no diagonals)
        // Decide whether to move horizontally or vertically based on which distance is greater
        float dx = targetPos.x - myPos.x;
        float dy = targetPos.y - myPos.y;
        
        // Don't make direction decisions on every frame - only when timer is up
        // or when we don't currently have a valid movement direction
        if (directionChangeTimer >= DIRECTION_CHANGE_INTERVAL || velocity.isZero()) {
            // We'll prioritize the axis with the greater distance
            if (Math.abs(dx) > Math.abs(dy)) {
                // Move horizontally
                direction.set(Math.signum(dx), 0);
            } else {
                // Move vertically
                direction.set(0, Math.signum(dy));
            }
            
            // Avoid 180-degree turns which cause "shaking"
            if (previousDirection.x != 0 && direction.x == -previousDirection.x ||
                previousDirection.y != 0 && direction.y == -previousDirection.y) {
                // If we would reverse direction, try the other axis instead
                if (Math.abs(dx) > Math.abs(dy)) {
                    direction.set(0, Math.signum(dy));
                } else {
                    direction.set(Math.signum(dx), 0);
                }
                
                // If we would still reverse, just keep current direction
                if (previousDirection.x != 0 && direction.x == -previousDirection.x ||
                    previousDirection.y != 0 && direction.y == -previousDirection.y) {
                    direction.set(previousDirection);
                }
            }
            
            velocity.set(direction.nor().scl(GHOST_SPEED));
            directionChangeTimer = 0;
        }
        
        // Update position
        Vector2 position = getPosition();
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
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
        Array<Vector2> perpendicularDirs = new Array<>();
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
            velocity.set(newDirection.x * GHOST_SPEED, newDirection.y * GHOST_SPEED);
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
     * Sets the collision state of the ghost
     * @param inCollision Whether the ghost is in a collision state
     */
    public void setCollisionState(boolean inCollision) {
        this.inCollisionState = inCollision;
        this.collisionStateDuration = 0;
    }
    
    /**
     * Checks whether the ghost is currently responding to a collision
     * @return True if ghost is in collision state
     */
    public boolean isInCollisionState() {
        return inCollisionState;
    }
}