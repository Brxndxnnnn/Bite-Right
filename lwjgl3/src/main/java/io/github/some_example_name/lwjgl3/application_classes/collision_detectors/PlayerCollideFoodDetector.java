package io.github.some_example_name.lwjgl3.application_classes.collision_detectors;

import com.badlogic.gdx.math.Rectangle;

import io.github.some_example_name.lwjgl3.abstract_engine.collision.EntityCollisionDetector;
import io.github.some_example_name.lwjgl3.application_classes.entities.Player;
import io.github.some_example_name.lwjgl3.application_classes.entities.Food;
import io.github.some_example_name.lwjgl3.application_classes.entities.HealthyFood;
import io.github.some_example_name.lwjgl3.application_classes.entities.UnhealthyFood;

/**
 * Handles collisions between the player and food entities
 */
public class PlayerCollideFoodDetector extends EntityCollisionDetector<Player, Food> {
    private final Player player;
    private final Food food;

    /**
     * Constructor for food collision detector
     * 
     * @param player The player Player
     * @param food The food entity
     */
    public PlayerCollideFoodDetector(Player player, Food food) {
        super(player, food);
        this.player = player;
        this.food = food;
    }

    @Override
    public boolean checkCollision() {
        // Only check collision if the food hasn't been consumed yet
        if (food.isConsumed()) {
            return false;
        }
        return super.checkCollision();
    }

    @Override
    public void resolveCollision() {
        // Get bounding boxes
        Rectangle playerBounds = player.getBoundingBox();
        Rectangle foodBounds = food.getBoundingBox();

        // Check collision
        if (playerBounds.overlaps(foodBounds) && !food.isConsumed()) {
            // Handle based on food type
            if (food instanceof HealthyFood) {
                HealthyFood healthyFood = (HealthyFood) food;
                System.out.println("Player consumed healthy food: +" + healthyFood.getHealthPoints() + " HP, +" 
                                  + healthyFood.getStrengthBoost() + " strength");
                
                // Add health to player
                player.adjustHealth(healthyFood.getHealthPoints());
                
                // Boost player strength
                player.adjustStrength(healthyFood.getStrengthBoost());
            }
            else if (food instanceof UnhealthyFood) {
                UnhealthyFood unhealthyFood = (UnhealthyFood) food;
                System.out.println("Player consumed unhealthy food: -" + unhealthyFood.getDamage() + " HP");
                
                // Reduce player health
                player.adjustHealth(-unhealthyFood.getDamage());
                
                // Check if player died
                if (player.getHealth() <= 0) {
                    System.out.println("Player died from unhealthy food!");
                    // Potentially trigger game over here
                }
            }
            
            // Mark food as consumed
            food.consume();
            
            // Mark this collision detector to be removed
            setToBeRemoved();
        }
    }
}