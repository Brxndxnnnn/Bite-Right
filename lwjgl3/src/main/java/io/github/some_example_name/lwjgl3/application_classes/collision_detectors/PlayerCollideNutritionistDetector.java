package io.github.some_example_name.lwjgl3.application_classes.collision_detectors;

import com.badlogic.gdx.math.Rectangle;

import io.github.some_example_name.lwjgl3.abstract_engine.audio.AudioPlayer;
import io.github.some_example_name.lwjgl3.abstract_engine.collision.EntityCollisionDetector;
import io.github.some_example_name.lwjgl3.application_classes.entities.Nutritionist;
import io.github.some_example_name.lwjgl3.application_classes.entities.Player;

public class PlayerCollideNutritionistDetector extends EntityCollisionDetector<Player, Nutritionist> {
    private final Player player;
    private final Nutritionist nutritionist;
    private boolean collisionDetected;
    private float collisionCooldown = 2.0f; // Cooldown in seconds to prevent multiple rapid collisions
    private float timeSinceLastCollision = 0;

    public PlayerCollideNutritionistDetector(Player player, Nutritionist nutritionist) {
        super(player, nutritionist);
        this.player = player;
        this.nutritionist = nutritionist;
        this.collisionDetected = false;
    }

    @Override
    public boolean checkCollision() {
        // Check if we're still in cooldown
        if (collisionDetected) {
            timeSinceLastCollision += com.badlogic.gdx.Gdx.graphics.getDeltaTime();
            if (timeSinceLastCollision >= collisionCooldown) {
                collisionDetected = false;
                timeSinceLastCollision = 0;
            } else {
                return false; // Still in cooldown, don't detect collision
            }
        }
        
        // Get bounding boxes
        Rectangle playerBounds = player.getBoundingBox();
        Rectangle nutritionistBounds = nutritionist.getBoundingBox();
        
        // Return true if the bounding boxes overlap
        return playerBounds.overlaps(nutritionistBounds);
    }

    @Override
    public void resolveCollision() {
        // Get bounding boxes
        Rectangle playerBounds = player.getBoundingBox();
        Rectangle nutritionistBounds = nutritionist.getBoundingBox();
        
        // Check collision
        if (playerBounds.overlaps(nutritionistBounds) && !collisionDetected) {
            System.out.println("Player collided with Nutritionist!");
            
            // Play a sound effect for the collision
            AudioPlayer.getInstance().playSFX("SFX/crunch.mp3");
            
            // Set collision detected flag to start cooldown
            collisionDetected = true;
            
            // TODO: Add popup logic or other nutritionist-specific behavior here
            // For now, just apply a small health bonus
            player.adjustHealth(5);
            
            // Force the nutritionist to change direction
            nutritionist.forceDirectionChange();
            
            // Ensure the nutritionist enters collision state for smooth movement
            nutritionist.setCollisionState(true);
        }
    }
}