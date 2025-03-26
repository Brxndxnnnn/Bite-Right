package io.github.some_example_name.lwjgl3.application_classes.collision_detectors;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import io.github.some_example_name.lwjgl3.abstract_engine.collision.EntityCollisionDetector;
import io.github.some_example_name.lwjgl3.application_classes.entities.Enemy;
import io.github.some_example_name.lwjgl3.application_classes.entities.Wall;

public class EnemyCollideWallDetector extends EntityCollisionDetector<Enemy, Wall> {
    private final Enemy npc;
    private final Wall wall;

    public EnemyCollideWallDetector(Enemy npc, Wall wall) {
        super(npc, wall);
        this.npc = npc;
        this.wall = wall;
    }

    // This collision event is resolved by ensuring Enemy cant pass through walls
    @Override
    public void resolveCollision() {
        // Get bounding boxes
        Rectangle npcBounds = npc.getBoundingBox();
        Rectangle wallBounds = wall.getBoundingBox();

        // Check collision
            System.out.println("Ghost collided with Wall");

            // Get ghost position and velocity
            Vector2 npcVelocity = npc.getVelocity();
            Vector2 npcPosition = npc.getPosition();

            // Calculate overlap
            float overlapX = Math.min(npcBounds.x + npcBounds.width, wallBounds.x + wallBounds.width) - 
                             Math.max(npcBounds.x, wallBounds.x);
            float overlapY = Math.min(npcBounds.y + npcBounds.height, wallBounds.y + wallBounds.height) - 
                             Math.max(npcBounds.y, wallBounds.y);

            // Calculate new position and velocity based on the direction of collision
            if (overlapX < overlapY) {
                // Horizontal collision
                if (npcPosition.x < wallBounds.x) {
                    // Left side collision - slight adjustment to prevent sticking
                    npc.setPosition(new Vector2(wallBounds.x - npcBounds.width - 1, npcPosition.y));
                    
                    // Set new velocity - move vertically
                    if (Math.abs(npcVelocity.y) < 0.1f) {
                        // If not already moving vertically, choose a vertical direction
                        float yDir = Math.random() > 0.5f ? 1f : -1f;
                        npc.setVelocity(new Vector2(0, yDir * Enemy.getBaseSpeed()));
                    } else {
                        // Continue with current vertical movement
                        npc.setVelocity(new Vector2(0, npcVelocity.y));
                    }
                } else {
                    // Right side collision
                    npc.setPosition(new Vector2(wallBounds.x + wallBounds.width + 1, npcPosition.y));
                    
                    // Set new velocity - move vertically
                    if (Math.abs(npcVelocity.y) < 0.1f) {
                        // If not already moving vertically, choose a vertical direction
                        float yDir = Math.random() > 0.5f ? 1f : -1f;
                        npc.setVelocity(new Vector2(0, yDir * Enemy.getBaseSpeed()));
                    } else {
                        // Continue with current vertical movement
                        npc.setVelocity(new Vector2(0, npcVelocity.y));
                    }
                }
            } else {
                // Vertical collision
                if (npcPosition.y < wallBounds.y) {
                    // Bottom side collision
                    npc.setPosition(new Vector2(npcPosition.x, wallBounds.y - npcBounds.height - 1));
                    
                    // Set new velocity - move horizontally
                    if (Math.abs(npcVelocity.x) < 0.1f) {
                        // If not already moving horizontally, choose a horizontal direction
                        float xDir = Math.random() > 0.5f ? 1f : -1f;
                        npc.setVelocity(new Vector2(xDir * Enemy.getBaseSpeed(), 0));
                    } else {
                        // Continue with current horizontal movement
                        npc.setVelocity(new Vector2(npcVelocity.x, 0));
                    }
                } else {
                    // Top side collision
                    npc.setPosition(new Vector2(npcPosition.x, wallBounds.y + wallBounds.height + 1));
                    
                    // Set new velocity - move horizontally
                    if (Math.abs(npcVelocity.x) < 0.1f) {
                        // If not already moving horizontally, choose a horizontal direction
                        float xDir = Math.random() > 0.5f ? 1f : -1f;
                        npc.setVelocity(new Vector2(xDir * Enemy.getBaseSpeed(), 0));
                    } else {
                        // Continue with current horizontal movement
                        npc.setVelocity(new Vector2(npcVelocity.x, 0));
                    }
                }
            }
            
            // Put the ghost in collision state to ensure smooth transition
            npc.setCollisionState(true);
    }
}