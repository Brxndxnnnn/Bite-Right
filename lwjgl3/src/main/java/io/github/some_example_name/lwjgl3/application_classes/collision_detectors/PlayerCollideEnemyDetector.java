package io.github.some_example_name.lwjgl3.application_classes.collision_detectors;

import io.github.some_example_name.lwjgl3.abstract_engine.audio.AudioPlayer;
import io.github.some_example_name.lwjgl3.abstract_engine.collision.EntityCollisionDetector;
import io.github.some_example_name.lwjgl3.abstract_engine.entity.Entity;
import io.github.some_example_name.lwjgl3.application_classes.entities.Character;
import io.github.some_example_name.lwjgl3.application_classes.entities.NonPlayableCharacter;

public class PlayerCollideEnemyDetector extends EntityCollisionDetector<Character, NonPlayableCharacter>{
	public PlayerCollideEnemyDetector(Character a, NonPlayableCharacter b){
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
        AudioPlayer.getInstance().playSFX("SFX/crunch.mp3");
	}
}
