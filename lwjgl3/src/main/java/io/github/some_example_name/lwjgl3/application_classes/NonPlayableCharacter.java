package io.github.some_example_name.lwjgl3.application_classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import io.github.some_example_name.lwjgl3.abstract_engine.entity.Entity;

public class NonPlayableCharacter extends Entity{
    private static final float SPEED = 150f;
    private Vector2 velocity;
    private float directionChangeTimer = 0;
    private static final float DIRECTION_CHANGE_INTERVAL = 0.5f; // changes direction every 0.5 sec

    public NonPlayableCharacter() {
        super();
        this.velocity = getRandomVelocity();
    }

    public NonPlayableCharacter(String name, Vector2 position, Texture texture, Boolean collidable) {
        super(name, position, texture, collidable);
        this.velocity = getRandomVelocity();
    }

    private Vector2 getRandomVelocity() {
        float angle = MathUtils.random(360f); // randomize angle
        return new Vector2(MathUtils.cosDeg(angle), MathUtils.sinDeg(angle)).nor().scl(SPEED);
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

        float screenWidth = com.badlogic.gdx.Gdx.graphics.getWidth();
        float screenHeight = com.badlogic.gdx.Gdx.graphics.getHeight();

        // reverse direction if collide with screen boundary
        if (position.x < 0 || position.x > screenWidth - getTexture().getWidth()) {
            velocity.x *= -1;
            position.x = MathUtils.clamp(position.x, 0, screenWidth - getTexture().getWidth());
        }
        if (position.y < 0 || position.y > screenHeight - getTexture().getHeight()) {
            velocity.y *= -1;
            position.y = MathUtils.clamp(position.y, 0, screenHeight - getTexture().getHeight());
        }

        setPosition(position);
    }

    @Override
    public void moveUserControlled() {
        throw new UnsupportedOperationException("Not supported yet.");
    } 	

    @Override
    public boolean collisionDetection(Entity Entity1) {
        return getBoundingBox().overlaps(Entity1.getBoundingBox());  
    }

    @Override
    public void collisionResolution(Entity entity1) {
    	System.out.println("NPC collision resolution");	
    }
    
    public Rectangle getBoundingBox() {
        return new Rectangle(getPosition().x, getPosition().y, getTexture().getWidth(), getTexture().getHeight());
    }
}
