package io.github.some_example_name.lwjgl3.application_classes.collision_detectors;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import io.github.some_example_name.lwjgl3.abstract_engine.collision.EntityCollisionDetector;
import io.github.some_example_name.lwjgl3.application_classes.Wall;
import io.github.some_example_name.lwjgl3.application_classes.entities.Character;

public class PlayerCollideWallDetector extends EntityCollisionDetector<Character, Wall>{
	private Character character;
	private Wall wall;

    public PlayerCollideWallDetector(Character character, Wall wall) {
        super(character, wall);
		this.character = character;
		this.wall = wall;
    }

    @Override
    public void resolveCollision() {

        // Get bounding boxes
        Rectangle npcBounds = character.getBoundingBox();
        Rectangle wallBounds = wall.getBoundingBox();

        // Check collision
        if (npcBounds.overlaps(wallBounds)) {
            System.out.println("Enemy collided with Wall");

            // Determine which side the collision happened on
            Vector2 npcVelocity = character.getVelocity();
            Vector2 npcPosition = character.getPosition();

            float overlapX = Math.min(npcBounds.x + npcBounds.width, wallBounds.x + wallBounds.width) - 
                             Math.max(npcBounds.x, wallBounds.x);
            float overlapY = Math.min(npcBounds.y + npcBounds.height, wallBounds.y + wallBounds.height) - 
                             Math.max(npcBounds.y, wallBounds.y);

            if (overlapX < overlapY) {
                // Horizontal collision
                if (npcPosition.x < wallBounds.x) {
                    character.setPosition(new Vector2(wallBounds.x - npcBounds.width, npcPosition.y)); // Left side
                } else {
                    character.setPosition(new Vector2(wallBounds.x + wallBounds.width, npcPosition.y)); // Right side
                }
                character.setVelocity(new Vector2(-npcVelocity.x, npcVelocity.y)); // Reverse X movement
            } else {
                // Vertical collision
                if (npcPosition.y < wallBounds.y) {
                    character.setPosition(new Vector2(npcPosition.x, wallBounds.y - npcBounds.height)); // Bottom side
                } else {
                    character.setPosition(new Vector2(npcPosition.x, wallBounds.y + wallBounds.height)); // Top side
                }
                character.setVelocity(new Vector2(npcVelocity.x, -npcVelocity.y)); // Reverse Y movement
            }
        }
    }
}
