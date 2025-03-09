package io.github.some_example_name.lwjgl3.application_classes.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import io.github.some_example_name.lwjgl3.abstract_engine.collision.Collidable;
import io.github.some_example_name.lwjgl3.abstract_engine.entity.MovableEntity;
import io.github.some_example_name.lwjgl3.abstract_engine.movement.UserMovable;

public class Player extends MovableEntity implements Collidable, UserMovable {
    private final float friction; // Friction for smooth deceleration
    private Vector2 lastDirection; // Track last movement direction for animation
    
    // Player stats
    private int maxHealth = 100;
    private int health = 100;
    private float strength = 1.0f; // Multiplier for Player abilities

    public Player(String name, Vector2 position, Texture texture, float speed) {
        super(name, position, texture, speed);
        this.velocity = new Vector2(0, 0);
        this.friction = 0.85f;
        this.lastDirection = new Vector2(0, 0); // No initial direction
    }

    @Override
    public Rectangle getBoundingBox() {
        // Use the custom width and height if set, otherwise use texture dimensions
        float boxWidth = getWidth() > 0 ? getWidth() : getTexture().getWidth();
        float boxHeight = getHeight() > 0 ? getHeight() : getTexture().getHeight();
        return new Rectangle(getPosition().x, getPosition().y, boxWidth, boxHeight);
    }

    @Override
    public void moveUserUp() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        // Set velocity for upward movement
        velocity.y = speed * 200; // Increased speed for grid-based movement
        velocity.x = 0;          // Ensure no diagonal movement for grid-based games
        lastDirection.set(0, 1); // Update last direction
        
        // Move the Player
        Vector2 position = getPosition();
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        
        // Apply friction to gradually slow down
        velocity.scl(friction);
    }

    @Override
    public void moveUserDown() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        // Set velocity for downward movement
        velocity.y = -speed * 200;
        velocity.x = 0;           // Ensure no diagonal movement for grid-based games
        lastDirection.set(0, -1); // Update last direction
        
        // Move the Player
        Vector2 position = getPosition();
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        
        // Apply friction to gradually slow down
        velocity.scl(friction);
    }

    @Override
    public void moveUserLeft() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        // Set velocity for leftward movement
        velocity.x = -speed * 200;
        velocity.y = 0;           // Ensure no diagonal movement for grid-based games
        lastDirection.set(-1, 0); // Update last direction
        
        // Move the Player
        Vector2 position = getPosition();
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        
        // Apply friction to gradually slow down
        velocity.scl(friction);
    }

    @Override
    public void moveUserRight() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        // Set velocity for rightward movement
        velocity.x = speed * 200;
        velocity.y = 0;          // Ensure no diagonal movement for grid-based games
        lastDirection.set(1, 0); // Update last direction
        
        // Move the Player
        Vector2 position = getPosition();
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        
        // Apply friction to gradually slow down
        velocity.scl(friction);
    }
    
    /**
     * Gets the last movement direction vector
     * @return Direction vector
     */
    public Vector2 getLastDirection() {
        return lastDirection;
    }
    
    /**
     * Gets the current health of the Player
     * @return Current health
     */
    public int getHealth() {
        return health;
    }
    
    /**
     * Gets the maximum health of the Player
     * @return Maximum health
     */
    public int getMaxHealth() {
        return maxHealth;
    }
    
    /**
     * Gets the current strength of the Player
     * @return Current strength multiplier
     */
    public float getStrength() {
        return strength;
    }
    
    /**
     * Adjusts the Player's health by the specified amount
     * @param amount Amount to adjust health by (positive heals, negative damages)
     */
    public void adjustHealth(int amount) {
        health += amount;
        if (health > maxHealth) {
            health = maxHealth;
        } else if (health < 0) {
            health = 0;
            // Player death could be handled here
            System.out.println("Player died!");
        }
        System.out.println("Player health: " + health + "/" + maxHealth);
    }
    
    /**
     * Adjusts the Player's strength by the specified amount
     * @param amount Amount to adjust strength by
     */
    public void adjustStrength(float amount) {
        strength += amount;
        System.out.println("Player strength: " + strength);
    }
    
    /**
     * Increases the Player's maximum health
     * @param amount Amount to increase max health by
     */
    public void increaseMaxHealth(int amount) {
        maxHealth += amount;
        health += amount; // Also increase current health
        System.out.println("Max health increased to: " + maxHealth);
    }
}