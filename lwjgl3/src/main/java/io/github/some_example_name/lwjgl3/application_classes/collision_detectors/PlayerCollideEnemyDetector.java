package io.github.some_example_name.lwjgl3.application_classes.collision_detectors;

import io.github.some_example_name.lwjgl3.abstract_engine.collision.EntityCollisionDetector;
import io.github.some_example_name.lwjgl3.abstract_engine.entity.Entity;
import io.github.some_example_name.lwjgl3.application_classes.entities.Enemy;
import io.github.some_example_name.lwjgl3.application_classes.entities.Player;

public class PlayerCollideEnemyDetector extends EntityCollisionDetector<Player, Enemy>{
	public PlayerCollideEnemyDetector(Player a, Enemy b){
		super(a, b);
	}

	// This collision event is resolved by remove Enemy that Player collide with
	@Override
	public void resolveCollision() {
		// set Collision Detector to be removed
		super.setToBeRemoved();
		// set Enemy (Non-playable character) to be removed
		((Entity) super.getEntityB()).setToBeRemoved();
		System.out.println("Player collided with Enemy");

	}
}
