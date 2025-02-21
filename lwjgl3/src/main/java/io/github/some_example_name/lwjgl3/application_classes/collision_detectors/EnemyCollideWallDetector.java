package io.github.some_example_name.lwjgl3.application_classes.collision_detectors;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import io.github.some_example_name.lwjgl3.abstract_engine.collision.EntityCollisionDetector;
import io.github.some_example_name.lwjgl3.application_classes.entities.NonPlayableCharacter;
import io.github.some_example_name.lwjgl3.application_classes.entities.Wall;

public class EnemyCollideWallDetector extends EntityCollisionDetector<NonPlayableCharacter, Wall> {
	private final NonPlayableCharacter npc;
	private final Wall wall;

    public EnemyCollideWallDetector(NonPlayableCharacter npc, Wall wall) {
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
        if (npcBounds.overlaps(wallBounds)) {
            System.out.println("Enemy collided with Wall");

            // Determine which side the collision happened on
            Vector2 npcVelocity = npc.getVelocity();
            Vector2 npcPosition = npc.getPosition();

            float overlapX = Math.min(npcBounds.x + npcBounds.width, wallBounds.x + wallBounds.width) - 
                             Math.max(npcBounds.x, wallBounds.x);
            float overlapY = Math.min(npcBounds.y + npcBounds.height, wallBounds.y + wallBounds.height) - 
                             Math.max(npcBounds.y, wallBounds.y);

            if (overlapX < overlapY) {
                // Horizontal collision
                if (npcPosition.x < wallBounds.x) {
                    npc.setPosition(new Vector2(wallBounds.x - npcBounds.width, npcPosition.y)); // Left side
                } else {
                    npc.setPosition(new Vector2(wallBounds.x + wallBounds.width, npcPosition.y)); // Right side
                }
                npc.setVelocity(new Vector2(-npcVelocity.x, npcVelocity.y)); // Reverse X movement
            } else {
                // Vertical collision
                if (npcPosition.y < wallBounds.y) {
                    npc.setPosition(new Vector2(npcPosition.x, wallBounds.y - npcBounds.height)); // Bottom side
                } else {
                    npc.setPosition(new Vector2(npcPosition.x, wallBounds.y + wallBounds.height)); // Top side
                }
                npc.setVelocity(new Vector2(npcVelocity.x, -npcVelocity.y)); // Reverse Y movement
            }
        }
    }
}
