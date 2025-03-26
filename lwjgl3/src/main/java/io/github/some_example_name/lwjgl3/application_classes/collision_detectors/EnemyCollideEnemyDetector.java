package io.github.some_example_name.lwjgl3.application_classes.collision_detectors;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import io.github.some_example_name.lwjgl3.abstract_engine.collision.EntityCollisionDetector;
import io.github.some_example_name.lwjgl3.application_classes.entities.Enemy;

public class EnemyCollideEnemyDetector extends EntityCollisionDetector<Enemy, Enemy> {
    private final Enemy npc1;
    private final Enemy npc2;

    public EnemyCollideEnemyDetector(Enemy npc1, Enemy npc2) {
        super(npc1, npc2);
        this.npc1 = npc1;
        this.npc2 = npc2;
    }

    // This collision event is resolved by ensuring ghosts smoothly move away from each other
    @Override
    public void resolveCollision() {
        // Get bounding boxes
        Rectangle npc1Bounds = npc1.getBoundingBox();
        Rectangle npc2Bounds = npc2.getBoundingBox();

        // Check collision
            // Get NPC positions
            Vector2 pos1 = npc1.getPosition();
            Vector2 pos2 = npc2.getPosition();
            
            // Calculate the direction vector between the two NPCs
            Vector2 collisionNormal = new Vector2(pos2).sub(pos1).nor(); // Unit vector direction
            
            // Determine overlap distance
            float overlapX = Math.min(npc1Bounds.x + npc1Bounds.width, npc2Bounds.x + npc2Bounds.width) - 
                             Math.max(npc1Bounds.x, npc2Bounds.x);
            float overlapY = Math.min(npc1Bounds.y + npc1Bounds.height, npc2Bounds.y + npc2Bounds.height) - 
                             Math.max(npc1Bounds.y, npc2Bounds.y);

            // Determine which axis has the smaller overlap to move away accordingly
            if (overlapX < overlapY) {
                // Minimal horizontal separation to resolve collision
                float pushAmount = overlapX / 2 + 1; // Add 1px buffer to ensure separation
                
                // Move ghosts apart horizontally
                npc1.setPosition(new Vector2(pos1.x - pushAmount * collisionNormal.x, pos1.y));
                npc2.setPosition(new Vector2(pos2.x + pushAmount * collisionNormal.x, pos2.y));
                
                // Set horizontal velocity components to move ghosts apart
                setRepulsionVelocities(-collisionNormal.x, 0, collisionNormal.x, 0);
            } else {
                // Minimal vertical separation to resolve collision
                float pushAmount = overlapY / 2 + 1; // Add 1px buffer to ensure separation
                
                // Move ghosts apart vertically
                npc1.setPosition(new Vector2(pos1.x, pos1.y - pushAmount * collisionNormal.y));
                npc2.setPosition(new Vector2(pos2.x, pos2.y + pushAmount * collisionNormal.y));
                
                // Set vertical velocity components to move ghosts apart
                setRepulsionVelocities(0, -collisionNormal.y, 0, collisionNormal.y);
            }
            
            // Mark both ghosts as recently collided to prevent behavior changes
            npc1.setCollisionState(true);
            npc2.setCollisionState(true);
    }
    
    /**
     * Sets velocity components for both NPCs to create a smooth repulsion effect
     */
    private void setRepulsionVelocities(float x1, float y1, float x2, float y2) {
        // Determine repulsion direction based on the sign of the input values
        Vector2 dir1 = new Vector2(Math.signum(x1), Math.signum(y1));
        Vector2 dir2 = new Vector2(Math.signum(x2), Math.signum(y2));
        
        // Set velocities using the base speed of ghosts
        float speed = Enemy.getBaseSpeed();
        
        if (dir1.x != 0) {
            npc1.setVelocity(new Vector2(dir1.x * speed, 0));
        } else if (dir1.y != 0) {
            npc1.setVelocity(new Vector2(0, dir1.y * speed));
        }
        
        if (dir2.x != 0) {
            npc2.setVelocity(new Vector2(dir2.x * speed, 0));
        } else if (dir2.y != 0) {
            npc2.setVelocity(new Vector2(0, dir2.y * speed));
        }
    }
}