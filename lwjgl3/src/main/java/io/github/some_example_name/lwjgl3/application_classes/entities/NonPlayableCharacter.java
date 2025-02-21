package io.github.some_example_name.lwjgl3.application_classes.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import io.github.some_example_name.lwjgl3.abstract_engine.collision.Collidable;
import io.github.some_example_name.lwjgl3.abstract_engine.entity.MovableEntity;
import io.github.some_example_name.lwjgl3.abstract_engine.movement.AIMovable;

public class NonPlayableCharacter extends MovableEntity implements Collidable, AIMovable{
    private float directionChangeTimer = 0;
    private static final float DIRECTION_CHANGE_INTERVAL = 0.5f; // changes direction every 0.5 sec

    public NonPlayableCharacter() {
        super();
        this.velocity = getRandomVelocity();
    }

    public NonPlayableCharacter(String name, Vector2 position, Texture texture) {
        super(name, position, texture);
        this.velocity = getRandomVelocity();
    }

    private Vector2 getRandomVelocity() {
        float angle = MathUtils.random(360f); // randomize angle
        return new Vector2(MathUtils.cosDeg(angle), MathUtils.sinDeg(angle)).nor().scl(150f);
    }
    
    @Override
    public void moveAIControlled() {
        float deltaTime = com.badlogic.gdx.Gdx.graphics.getDeltaTime(); 
        directionChangeTimer += deltaTime;

        // change direction randomly at set intervals
        if (directionChangeTimer >= DIRECTION_CHANGE_INTERVAL) {
            velocity = getRandomVelocity();
            directionChangeTimer = 0; // Reset timer
        }

        Vector2 position = getPosition();
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
    }
    
    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(getPosition().x, getPosition().y, getTexture().getWidth(), getTexture().getHeight());
    }
}
