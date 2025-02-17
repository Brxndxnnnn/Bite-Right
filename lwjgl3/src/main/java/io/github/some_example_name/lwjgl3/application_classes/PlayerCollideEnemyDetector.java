package io.github.some_example_name.lwjgl3.application_classes;

import io.github.some_example_name.lwjgl3.abstract_engine.collision.EntityCollisionDetector;
import io.github.some_example_name.lwjgl3.abstract_engine.entity.Entity;

public class PlayerCollideEnemyDetector extends EntityCollisionDetector<Character, NonPlayableCharacter>{
	PlayerCollideEnemyDetector(Character a, NonPlayableCharacter b){
		super(a, b);
	}

// remove Enemy from list
	@Override
	public void resolveCollision() {
		// set Collision Detector to be removed
		super.setToBeRemoved();
		// set Enemy (Non-playable character) to be removed
		((Entity) super.getEntityB()).setToBeRemoved();
	}
}
