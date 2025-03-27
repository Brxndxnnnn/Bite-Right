package io.github.some_example_name.lwjgl3.application_classes.collision_detectors;

import com.badlogic.gdx.math.MathUtils;

import io.github.some_example_name.lwjgl3.abstract_engine.audio.AudioPlayer;
import io.github.some_example_name.lwjgl3.abstract_engine.collision.EntityCollisionDetector;
import io.github.some_example_name.lwjgl3.abstract_engine.collision.Removable;
import io.github.some_example_name.lwjgl3.application_classes.entities.Food;
import io.github.some_example_name.lwjgl3.application_classes.entities.HealthyFood;
import io.github.some_example_name.lwjgl3.application_classes.entities.Player;
import io.github.some_example_name.lwjgl3.application_classes.entities.UnhealthyFood;
import io.github.some_example_name.lwjgl3.application_classes.utilities.DialogPopUpManager;

/**
 * Handles collisions between the player and food entities
 */
public class PlayerCollideFoodDetector extends EntityCollisionDetector<Player, Food> implements Removable {
    private final Player player;
    private final Food food;
    private DialogPopUpManager dialogPopUpManager;
    private boolean toBeRemoved;
    /**
     * Constructor for food collision detector
     *
     * @param player The player Player
     * @param food The food entity
     */
    public PlayerCollideFoodDetector(Player player, Food food, DialogPopUpManager dialogManager) {
        super(player, food);
        this.player = player;
        this.food = food;
        this.dialogPopUpManager = dialogManager;
        this.toBeRemoved = false;
    }

    @Override
    public void resolveCollision() {
        if (food.shouldBeRemoved()) return; // Check if food should be removed, if true then stop here
        // Check collision
        // Handle based on food type
        if (food instanceof HealthyFood) {
            AudioPlayer.getInstance().playSFX("sfx/crunch.mp3");
            HealthyFood healthyFood = (HealthyFood) food;
            System.out.println("Player consumed healthy food: +" + healthyFood.getHealthPoints() + " HP, +"
                                + healthyFood.getStrengthBoost() + " strength");

            // Add health to player
            player.adjustHealth(healthyFood.getHealthPoints());

            // Boost player strength
            player.adjustStrength(healthyFood.getStrengthBoost());

            // Add points to player
            player.adjustPoints(20);
        }
        else if (food instanceof UnhealthyFood) {
            AudioPlayer.getInstance().playSFX("sfx/bad.mp3");
            UnhealthyFood unhealthyFood = (UnhealthyFood) food;
            System.out.println("Player consumed unhealthy food: -" + unhealthyFood.getDamage() + " HP");

            // Reduce player health
            player.adjustHealth(-unhealthyFood.getDamage());
            
            // Reduce player points
            player.adjustPoints(-10);

            // Check if player died
            if (player.getHealth() <= 0) {
                System.out.println("Player died from unhealthy food!");
                // Potentially trigger game over here
            }
        }

        // Mark food as consumed
        food.setToBeRemoved();

        // Mark this collision detector to be removed
        setToBeRemoved();
        
        // Show popup with 20% probability - single line check
        if (MathUtils.random() < 0.25f) {
            dialogPopUpManager.showPopUp("Did you know?", food.getFunFact(), food.getTexture());
        }
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