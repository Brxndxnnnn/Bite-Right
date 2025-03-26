package io.github.some_example_name.lwjgl3.application_classes.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Unhealthy food that harms the player
 */
public class UnhealthyFood extends Food {
    private int damage;
    
    /**
     * Constructor for unhealthy food
     * 
     * @param name Food name
     * @param position Position in the maze
     * @param texture Food texture
     * @param damage Amount of damage caused
     * @param lifespanInSeconds How long until the food expires
     */
    public UnhealthyFood(String name, Vector2 position, Texture texture, int damage, float lifespanInSeconds, String funFact) {
        super(name, position, texture, FoodType.UNHEALTHY, lifespanInSeconds, funFact);
        this.damage = damage;
    }
    
    /**
     * Alternate constructor with default values and 10 second lifespan
     * 
     * @param position Position in the maze
     * @param texture Food texture
     */
    public UnhealthyFood(Vector2 position, Texture texture, String funFact) {
        super("UnhealthyFood", position, texture, FoodType.UNHEALTHY, 10f, funFact);
        this.damage = 10; // Default damage
    }
    
    /**
     * Get damage caused by this food
     * @return Damage value
     */
    public int getDamage() {
        return damage;
    }
    
    @Override
    public int getEffectValue() {
        return -damage; // Negative to indicate damage
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