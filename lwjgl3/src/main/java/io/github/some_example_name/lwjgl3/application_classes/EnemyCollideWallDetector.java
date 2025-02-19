package io.github.some_example_name.lwjgl3.application_classes;

import io.github.some_example_name.lwjgl3.abstract_engine.collision.EntityCollisionDetector;
import io.github.some_example_name.lwjgl3.abstract_engine.entity.Entity;

public class EnemyCollideWallDetector extends EntityCollisionDetector<NonPlayableCharacter, Wall>{
	EnemyCollideWallDetector(NonPlayableCharacter a, Wall b){
		super(a, b);
	}

// remove Enemy from list
	@Override
	public void resolveCollision() {
		// set Collision Detector to be removed
		super.setToBeRemoved();
		// set Enemy (Non-playable character) to be removed
		((Entity) super.getEntityB()).setToBeRemoved();
		System.out.println("Enemy collided with Wall");
	}
}
