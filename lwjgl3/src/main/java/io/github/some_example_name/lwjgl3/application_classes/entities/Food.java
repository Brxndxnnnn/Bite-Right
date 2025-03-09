package io.github.some_example_name.lwjgl3.application_classes.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import io.github.some_example_name.lwjgl3.abstract_engine.collision.Collidable;
import io.github.some_example_name.lwjgl3.abstract_engine.entity.NonMovableEntity;

/**
 * Base class for food items in the maze
 */
public abstract class Food extends NonMovableEntity implements Collidable {
    protected float width;
    protected float height;
    protected boolean consumed;
    protected FoodType type;
    
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
     */
    public Food(String name, Vector2 position, Texture texture, FoodType type) {
        super(name, position, texture);
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        this.consumed = false;
        this.type = type;
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
     * @return Effect value when consumed
     */
    public abstract int getEffectValue();
    
    /**
     * Marks the food as consumed
     */
    public void consume() {
        this.consumed = true;
        setToBeRemoved();
    }
    
    /**
     * Checks if the food has been consumed
     * @return True if the food is consumed
     */
    public boolean isConsumed() {
        return consumed;
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
    
    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(getPosition().x, getPosition().y, width, height);
    }
    
    @Override
    public void draw(SpriteBatch batch) {
        if (!consumed) {
            batch.draw(getTexture(), getPosition().x, getPosition().y, width, height);
        }
    }
}