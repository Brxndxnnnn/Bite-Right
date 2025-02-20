package io.github.some_example_name.lwjgl3.application_classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import io.github.some_example_name.lwjgl3.abstract_engine.collision.iCollidable;
import io.github.some_example_name.lwjgl3.abstract_engine.entity.MovableEntity;

public class Character extends MovableEntity implements iCollidable {
    private Vector2 velocity;
    private static final float FRICTION = 0.85f; // Adjust friction for smooth deceleration

    public Character(String name, Vector2 position, Texture texture, float speed) {
        super(name, position, texture, speed);
        this.velocity = new Vector2(0, 0);
    }

    @Override
    public void moveAIControlled() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void moveUserControlled() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        float acceleration = speed * 50; // Determines movement speed

        // Apply acceleration based on key input
        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            velocity.x = -acceleration;
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            velocity.x = acceleration;
        } 
        if (Gdx.input.isKeyPressed(Keys.UP)) {
            velocity.y = acceleration;
        }
        if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            velocity.y = -acceleration;
        }

        // Update position using velocity
        Vector2 position = getPosition();
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);

        // Apply friction to slow down movement gradually
        velocity.scl(FRICTION);

        // Keep player within screen boundaries
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float textureWidth = getTexture().getWidth();
        float textureHeight = getTexture().getHeight();

        if (position.x < 0) {
            position.x = 0;
            velocity.x = 0; // Stop movement at boundary
        }
        if (position.x > screenWidth - textureWidth) {
            position.x = screenWidth - textureWidth;
            velocity.x = 0;
        }
        if (position.y < 0) {
            position.y = 0;
            velocity.y = 0;
        }
        if (position.y > screenHeight - textureHeight) {
            position.y = screenHeight - textureHeight;
            velocity.y = 0;
        }

        setPosition(position);
    }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(getPosition().x, getPosition().y, getTexture().getWidth(), getTexture().getHeight());
    }

    @Override
    public void moveUp() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        velocity.y = speed * 50;
        Vector2 position = getPosition();
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        velocity.scl(FRICTION);
    }

    @Override
    public void moveDown() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        velocity.y = -speed * 50;
        Vector2 position = getPosition();
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        velocity.scl(FRICTION);
    }

    @Override
    public void moveLeft() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        velocity.x = -speed * 50;
        Vector2 position = getPosition();
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        velocity.scl(FRICTION);
    }

    @Override
    public void moveRight() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        velocity.x = speed * 50;
        Vector2 position = getPosition();
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        velocity.scl(FRICTION);
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 newVelocity) {
        this.velocity.set(newVelocity);
    }
}
