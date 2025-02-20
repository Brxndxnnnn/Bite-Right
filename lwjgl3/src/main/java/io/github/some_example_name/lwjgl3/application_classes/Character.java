package io.github.some_example_name.lwjgl3.application_classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import io.github.some_example_name.lwjgl3.abstract_engine.collision.iCollidable;
import io.github.some_example_name.lwjgl3.abstract_engine.entity.MovableEntity;

public class Character extends MovableEntity implements iCollidable {
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
    public Rectangle getBoundingBox() {
        return new Rectangle(getPosition().x, getPosition().y, getTexture().getWidth(), getTexture().getHeight());
    }

    @Override
    public void moveUserUp() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        velocity.y = speed * 50;
        Vector2 position = getPosition();
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        velocity.scl(FRICTION);
    }

    @Override
    public void moveUserDown() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        velocity.y = -speed * 50;
        Vector2 position = getPosition();
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        velocity.scl(FRICTION);
    }

    @Override
    public void moveUserLeft() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        velocity.x = -speed * 50;
        Vector2 position = getPosition();
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        velocity.scl(FRICTION);
    }

    @Override
    public void moveUserRight() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        velocity.x = speed * 50;
        Vector2 position = getPosition();
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        velocity.scl(FRICTION);
    }
}
