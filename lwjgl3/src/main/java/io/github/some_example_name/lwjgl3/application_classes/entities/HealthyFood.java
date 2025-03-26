package io.github.some_example_name.lwjgl3.application_classes.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Healthy food that provides positive effects to the player
 */
public class HealthyFood extends Food {
    private int healthPoints;
    private float strengthBoost;
    
    /**
     * Constructor for healthy food
     * 
     * @param name Food name
     * @param position Position in the maze
     * @param texture Food texture
     * @param healthPoints Amount of health restored
     * @param strengthBoost Strength boost provided
     * @param lifespanInSeconds How long until the food expires
     */
    public HealthyFood(String name, Vector2 position, Texture texture, int healthPoints, 
                      float strengthBoost, float lifespanInSeconds, String funFact) {
        super(name, position, texture, FoodType.HEALTHY, lifespanInSeconds, funFact);
        this.healthPoints = healthPoints;
        this.strengthBoost = strengthBoost;
    }
    
    /**
     * Alternate constructor with default values and 10 second lifespan
     * 
     * @param position Position in the maze
     * @param texture Food texture
     */
    public HealthyFood(Vector2 position, Texture texture, String funFact) {
        super("HealthyFood", position, texture, FoodType.HEALTHY, 10f, funFact);
        this.healthPoints = 10; // Default health restoration
        this.strengthBoost = 0.1f; // Default strength boost
    }
    
    /**
     * Get health points restored by this food
     * @return Health points value
     */
    public int getHealthPoints() {
        return healthPoints;
    }
    
    /**
     * Get strength boost provided by this food
     * @return Strength boost value
     */
    public float getStrengthBoost() {
        return strengthBoost;
    }
    
    @Override
    public int getEffectValue() {
        return healthPoints;
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