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
 * Detects and resolves collisions between the player and food items.
 * When a collision occurs, the player consumes the food and gains or loses health/points.
 */
public class PlayerCollideFoodDetector extends EntityCollisionDetector<Player, Food> implements Removable {
    private final Player player;
    private final Food food;
    private final DialogPopUpManager dialogPopUpManager;
    private boolean toBeRemoved;
    
    /**
     * Creates a new detector for player-food collisions.
     *
     * @param player The player entity
     * @param food The food entity
     * @param dialogManager The dialog popup manager for displaying food facts
     */
    public PlayerCollideFoodDetector(Player player, Food food, DialogPopUpManager dialogManager) {
        super(player, food);
        this.player = player;
        this.food = food;
        this.dialogPopUpManager = dialogManager;
        this.toBeRemoved = false;
    }

    /**
     * Resolves player-food collisions by applying the food's effects to the player
     * and showing an informational popup.
     */
    @Override
    public void resolveCollision() {
        if (food.shouldBeRemoved()) return; // Skip if food is already consumed

        // Apply effects based on food type
        if (food instanceof HealthyFood) {
            AudioPlayer.getInstance().playSFX("SFX/crunch.mp3");        
        }
        else if (food instanceof UnhealthyFood) {
            AudioPlayer.getInstance().playSFX("SFX/bad.mp3");
        }

        food.applyEffect(player);

        // Mark food as consumed
        food.setToBeRemoved();

        // Mark this collision detector to be removed
        setToBeRemoved();
        
        // Show popup with 25% probability
        if (MathUtils.random() < 0.25f) {
            dialogPopUpManager.showPopUp("Did you know?", food.getFunFact(), food.getTexture());
        }
    }

    /**
     * Marks this detector to be removed.
     */
    @Override
    public void setToBeRemoved() {
        toBeRemoved = true;
    }

    /**
     * Checks if this detector should be removed.
     * 
     * @return true if it should be removed, false otherwise
     */
    @Override
    public boolean shouldBeRemoved() {
        return toBeRemoved;
    }
}