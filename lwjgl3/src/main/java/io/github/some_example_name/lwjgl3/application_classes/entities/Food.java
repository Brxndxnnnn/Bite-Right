package io.github.some_example_name.lwjgl3.application_classes.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import io.github.some_example_name.lwjgl3.abstract_engine.collision.Collidable;
import io.github.some_example_name.lwjgl3.abstract_engine.collision.Removable;
import io.github.some_example_name.lwjgl3.abstract_engine.entity.NonMovableEntity;

/**
 * Base class for food items in the maze
 */
public abstract class Food extends NonMovableEntity implements Collidable, Removable {
    protected float width;
    protected float height;
    protected boolean toBeRemoved;
    protected FoodType type;
    protected String funFact;
    
    // Expiration related fields
    protected float lifespanInSeconds;
    protected float elapsedTime;
    protected boolean expired;
    
    /**
     * Enumeration for different food types
     */
    public enum FoodType {
        HEALTHY,
        UNHEALTHY
    }
    
    /**
     * Constructor for food entity
     * 
     * @param name Food name
     * @param position Position in the maze
     * @param texture Food texture
     * @param type Food type (healthy or unhealthy)
     * @param lifespanInSeconds How long the food will exist before expiring
     */
    public Food(String name, Vector2 position, Texture texture, FoodType type, float lifespanInSeconds, String funFact) {
        super(name, position, texture);
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        this.toBeRemoved = false;
        this.type = type;
        this.lifespanInSeconds = lifespanInSeconds;
        this.elapsedTime = 0f;
        this.expired = false;
        this.funFact = funFact;
    }
    
    /**
     * Returns the food type
     * @return FoodType of this food
     */
    public FoodType getType() {
        return type;
    }
    
    /**
     * Gets the effect value of this food (positive for healthy, negative for unhealthy)
     * @return Effect value when toBeRemoved
     */
    public abstract int getEffectValue();
    
    /**
     * Marks the food as toBeRemoved
     */
    // public void consume() {
    //     this.toBeRemoved = true;
    //     setToBeRemoved();
    // }
    
    /**
     * Checks if the food has been toBeRemoved
     * @return True if the food is toBeRemoved
     */
    public boolean shouldBeRemoved() {
        return toBeRemoved;
    }
    
    /**
     * Sets the size of the food
     * @param width Width of the food
     * @param height Height of the food
     */
    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }
    
    /**
     * Updates the food's elapsed time and checks for expiration
     * @param deltaTime Time elapsed since last update
     */
    public void update(float deltaTime) {
        if (!toBeRemoved && !expired) {
            elapsedTime += deltaTime;
            
            // Check for expiration
            if (elapsedTime >= lifespanInSeconds) {
                expired = true;
                setToBeRemoved();
            }
        }
    }
    
    /**
     * Checks if the food has expired
     * @return True if the food has expired
     */
    public boolean isExpired() {
        return expired;
    }
    
    /**
     * Gets the remaining lifetime as a percentage (0-1)
     * @return Percentage of life remaining
     */
    public float getRemainingLifePercentage() {
        return Math.max(0, 1 - (elapsedTime / lifespanInSeconds));
    }
    
    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(getPosition().x, getPosition().y, width, height);
    }
    
    public String getFunFact() {
        return funFact;
    }
    
    @Override
    public void draw(SpriteBatch batch) {
        if (!toBeRemoved && !expired) {
            batch.draw(getTexture(), getPosition().x, getPosition().y, width, height);
        }
    }
    
}