package io.github.some_example_name.lwjgl3.application_classes.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import io.github.some_example_name.lwjgl3.abstract_engine.collision.Collidable;
import io.github.some_example_name.lwjgl3.abstract_engine.entity.MovableEntity;
import io.github.some_example_name.lwjgl3.abstract_engine.movement.UserMovable;

public class Character extends MovableEntity implements Collidable, UserMovable {
    private final float friction; // Friction for smooth deceleration
    private Vector2 lastDirection; // Track last movement direction for animation

    public Character(String name, Vector2 position, Texture texture, float speed) {
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
        
        // Move the character
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
        
        // Move the character
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
        
        // Move the character
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
        
        // Move the character
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
}