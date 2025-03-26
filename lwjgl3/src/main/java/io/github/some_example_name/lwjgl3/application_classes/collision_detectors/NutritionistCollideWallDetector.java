package io.github.some_example_name.lwjgl3.application_classes.collision_detectors;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import io.github.some_example_name.lwjgl3.abstract_engine.collision.EntityCollisionDetector;
import io.github.some_example_name.lwjgl3.application_classes.entities.Nutritionist;
import io.github.some_example_name.lwjgl3.application_classes.entities.Wall;

public class NutritionistCollideWallDetector extends EntityCollisionDetector<Nutritionist, Wall> {
    private final Nutritionist nutritionist;
    private final Wall wall;

    public NutritionistCollideWallDetector(Nutritionist nutritionist, Wall wall) {
        super(nutritionist, wall);
        this.nutritionist = nutritionist;
        this.wall = wall;
    }

    // This collision event is resolved by ensuring Nutritionist can't pass through walls
    @Override
    public void resolveCollision() {
        // Get bounding boxes
        Rectangle nutritionistBounds = nutritionist.getBoundingBox();
        Rectangle wallBounds = wall.getBoundingBox();

        // Check collision
            System.out.println("Nutritionist collided with Wall");

            // Get nutritionist position and velocity
            Vector2 nutritionistVelocity = nutritionist.getVelocity();
            Vector2 nutritionistPosition = nutritionist.getPosition();

            // Calculate overlap
            float overlapX = Math.min(nutritionistBounds.x + nutritionistBounds.width, wallBounds.x + wallBounds.width) - 
                             Math.max(nutritionistBounds.x, wallBounds.x);
            float overlapY = Math.min(nutritionistBounds.y + nutritionistBounds.height, wallBounds.y + wallBounds.height) - 
                             Math.max(nutritionistBounds.y, wallBounds.y);

            // Calculate new position and velocity based on the direction of collision
            if (overlapX < overlapY) {
                // Horizontal collision
                if (nutritionistPosition.x < wallBounds.x) {
                    // Left side collision - slight adjustment to prevent sticking
                    nutritionist.setPosition(new Vector2(wallBounds.x - nutritionistBounds.width - 1, nutritionistPosition.y));
                    
                    // Set new velocity - move vertically
                    if (Math.abs(nutritionistVelocity.y) < 0.1f) {
                        // If not already moving vertically, choose a vertical direction
                        float yDir = Math.random() > 0.5f ? 1f : -1f;
                        nutritionist.setVelocity(new Vector2(0, yDir * Nutritionist.getBaseSpeed()));
                    } else {
                        // Continue with current vertical movement
                        nutritionist.setVelocity(new Vector2(0, nutritionistVelocity.y));
                    }
                } else {
                    // Right side collision
                    nutritionist.setPosition(new Vector2(wallBounds.x + wallBounds.width + 1, nutritionistPosition.y));
                    
                    // Set new velocity - move vertically
                    if (Math.abs(nutritionistVelocity.y) < 0.1f) {
                        // If not already moving vertically, choose a vertical direction
                        float yDir = Math.random() > 0.5f ? 1f : -1f;
                        nutritionist.setVelocity(new Vector2(0, yDir * Nutritionist.getBaseSpeed()));
                    } else {
                        // Continue with current vertical movement
                        nutritionist.setVelocity(new Vector2(0, nutritionistVelocity.y));
                    }
                }
            } else {
                // Vertical collision
                if (nutritionistPosition.y < wallBounds.y) {
                    // Bottom side collision
                    nutritionist.setPosition(new Vector2(nutritionistPosition.x, wallBounds.y - nutritionistBounds.height - 1));
                    
                    // Set new velocity - move horizontally
                    if (Math.abs(nutritionistVelocity.x) < 0.1f) {
                        // If not already moving horizontally, choose a horizontal direction
                        float xDir = Math.random() > 0.5f ? 1f : -1f;
                        nutritionist.setVelocity(new Vector2(xDir * Nutritionist.getBaseSpeed(), 0));
                    } else {
                        // Continue with current horizontal movement
                        nutritionist.setVelocity(new Vector2(nutritionistVelocity.x, 0));
                    }
                } else {
                    // Top side collision
                    nutritionist.setPosition(new Vector2(nutritionistPosition.x, wallBounds.y + wallBounds.height + 1));
                    
                    // Set new velocity - move horizontally
                    if (Math.abs(nutritionistVelocity.x) < 0.1f) {
                        // If not already moving horizontally, choose a horizontal direction
                        float xDir = Math.random() > 0.5f ? 1f : -1f;
                        nutritionist.setVelocity(new Vector2(xDir * Nutritionist.getBaseSpeed(), 0));
                    } else {
                        // Continue with current horizontal movement
                        nutritionist.setVelocity(new Vector2(nutritionistVelocity.x, 0));
                    }
                }
            }
            
            // Put the nutritionist in collision state to ensure smooth transition
            nutritionist.setCollisionState(true);
    }
}