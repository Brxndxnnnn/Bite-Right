package io.github.some_example_name.lwjgl3.application_classes.utilities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import io.github.some_example_name.lwjgl3.abstract_engine.entity.Entity;
import io.github.some_example_name.lwjgl3.abstract_engine.entity.EntityManager;
import io.github.some_example_name.lwjgl3.application_classes.entities.Food;
import io.github.some_example_name.lwjgl3.application_classes.entities.HealthyFood;
import io.github.some_example_name.lwjgl3.application_classes.entities.UnhealthyFood;

/**
 * Utility class to generate food items in the maze
 */
public class FoodGenerator {
    private final EntityManager entityManager;
    private final MazeGenerator mazeGenerator;
    private final Texture healthyFoodTexture;
    private final Texture unhealthyFoodTexture;
    private final float cellSize;
    
    // Food generation parameters
    private static final float HEALTHY_FOOD_RATIO = 0.7f; // 70% healthy, 30% unhealthy
    private static final int MAX_FOOD_ATTEMPTS = 10; // Maximum attempts to place food
    
    /**
     * Constructor for food generator
     * 
     * @param entityManager Entity manager to add food to
     * @param mazeGenerator Maze generator to get grid information
     * @param healthyFoodTexture Texture for healthy food
     * @param unhealthyFoodTexture Texture for unhealthy food
     */
    public FoodGenerator(EntityManager entityManager, MazeGenerator mazeGenerator, 
                         Texture healthyFoodTexture, Texture unhealthyFoodTexture) {
        this.entityManager = entityManager;
        this.mazeGenerator = mazeGenerator;
        this.healthyFoodTexture = healthyFoodTexture;
        this.unhealthyFoodTexture = unhealthyFoodTexture;
        this.cellSize = mazeGenerator.getCellSize();
    }
    
    /**
     * Generates a specified number of food items in the maze
     * 
     * @param count Number of food items to generate
     */
    public void generateFood(int count) {
        int foodsGenerated = 0;
        int attempts = 0;
        
        while (foodsGenerated < count && attempts < MAX_FOOD_ATTEMPTS) {
            attempts++;
            
            // Generate a random position in the grid
            int gridX = MathUtils.random(1, MazeGenerator.getGridWidth() - 2);
            int gridY = MathUtils.random(1, MazeGenerator.getGridHeight() - 2);
            
            // Skip if it's a wall
            if (MazeGenerator.isWall(gridX, gridY)) {
                continue;
            }
            
            // Convert to screen coordinates
            Vector2 position = mazeGenerator.gridToScreenTopLeft(gridX, gridY);
            
            // Add offset to center food in cell
            float offset = cellSize * 0.25f; // 25% offset to create a smaller food item
            position.add(offset, offset);
            
            // Check for collision with existing entities
            Rectangle foodBounds = new Rectangle(position.x, position.y, cellSize * 0.5f, cellSize * 0.5f);
            boolean collides = false;
            
            // Check existing entities for collision
            for (int i = 0; i < entityManager.getEntitiesSize(); i++) {
                Entity entity = entityManager.getEntity(i);
                if (entity instanceof Food) {
                    Food existingFood = (Food) entity;
                    if (existingFood.getBoundingBox().overlaps(foodBounds)) {
                        collides = true;
                        break;
                    }
                }
            }
            
            if (collides) {
                continue;
            }
            
            // Determine food type
            boolean isHealthy = MathUtils.random() < HEALTHY_FOOD_RATIO;
            Food food;
            
            if (isHealthy) {
                // Create healthy food with random health boost (5-15)
                int healthBoost = MathUtils.random(5, 15);
                float strengthBoost = MathUtils.random(0.05f, 0.15f);
                food = new HealthyFood("HealthyFood_" + foodsGenerated, position, healthyFoodTexture, 
                                       healthBoost, strengthBoost);
            } else {
                // Create unhealthy food with random damage (5-15)
                int damage = MathUtils.random(5, 15);
                food = new UnhealthyFood("UnhealthyFood_" + foodsGenerated, position, unhealthyFoodTexture,
                                         damage);
            }
            
            // Set food size to half a cell
            food.setSize(cellSize * 0.5f, cellSize * 0.5f);
            
            // Add to entity manager
            entityManager.addEntities(food);
            foodsGenerated++;
        }
        
        System.out.println("Generated " + foodsGenerated + " food items in " + attempts + " attempts");
    }
    
    /**
     * Gets a list of all food entities currently in the entity manager
     * 
     * @return Array of food entities
     */
    public Array<Food> getAllFood() {
        Array<Food> foods = new Array<>();
        
        for (int i = 0; i < entityManager.getEntitiesSize(); i++) {
            Entity entity = entityManager.getEntity(i);
            if (entity instanceof Food) {
                foods.add((Food) entity);
            }
        }
        
        return foods;
    }
}