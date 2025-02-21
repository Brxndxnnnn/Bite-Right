package io.github.some_example_name.lwjgl3.application_classes.collision_detectors;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import io.github.some_example_name.lwjgl3.abstract_engine.collision.EntityCollisionDetector;
import io.github.some_example_name.lwjgl3.application_classes.entities.NonPlayableCharacter;

public class EnemyCollideEnemyDetector extends EntityCollisionDetector<NonPlayableCharacter, NonPlayableCharacter> {
    private final NonPlayableCharacter npc1;
    private final NonPlayableCharacter npc2;

    public EnemyCollideEnemyDetector(NonPlayableCharacter npc1, NonPlayableCharacter npc2) {
        super(npc1, npc2);
        this.npc1 = npc1;
        this.npc2 = npc2;
    }

    // This collision event is resolved by ensuring Enemy cant pass through other Enemy
    @Override
    public void resolveCollision() {
        // Get bounding boxes
        Rectangle npc1Bounds = npc1.getBoundingBox();
        Rectangle npc2Bounds = npc2.getBoundingBox();

        // Check collision
        if (npc1Bounds.overlaps(npc2Bounds)) {
            System.out.println("Enemy collided with Enemy");

            // Get NPC positions and velocities
            Vector2 pos1 = npc1.getPosition();
            Vector2 pos2 = npc2.getPosition();
            Vector2 vel1 = npc1.getVelocity();
            Vector2 vel2 = npc2.getVelocity();

            // Calculate the direction vector between the two NPCs
            Vector2 collisionNormal = new Vector2(pos2).sub(pos1).nor(); // Unit vector direction

            // Determine overlap distance (push apart)
            float overlapX = Math.min(npc1Bounds.x + npc1Bounds.width, npc2Bounds.x + npc2Bounds.width) - 
                             Math.max(npc1Bounds.x, npc2Bounds.x);
            float overlapY = Math.min(npc1Bounds.y + npc1Bounds.height, npc2Bounds.y + npc2Bounds.height) - 
                             Math.max(npc1Bounds.y, npc2Bounds.y);

            // Determine which axis has the smaller overlap to push away accordingly
            if (overlapX < overlapY) {
                // Horizontal push
                float pushAmount = overlapX / 2; // Each NPC moves half the overlap distance
                npc1.setPosition(new Vector2(pos1.x - pushAmount * collisionNormal.x, pos1.y));
                npc2.setPosition(new Vector2(pos2.x + pushAmount * collisionNormal.x, pos2.y));

                // Reverse X velocity component
                npc1.setVelocity(new Vector2(-vel1.x, vel1.y));
                npc2.setVelocity(new Vector2(-vel2.x, vel2.y));
            } else {
                // Vertical push
                float pushAmount = overlapY / 2;
                npc1.setPosition(new Vector2(pos1.x, pos1.y - pushAmount * collisionNormal.y));
                npc2.setPosition(new Vector2(pos2.x, pos2.y + pushAmount * collisionNormal.y));

                // Reverse Y velocity component
                npc1.setVelocity(new Vector2(vel1.x, -vel1.y));
                npc2.setVelocity(new Vector2(vel2.x, -vel2.y));
            }
        }
    }
}
