package io.github.some_example_name.lwjgl3.application_classes.collision_detectors;

import io.github.some_example_name.lwjgl3.abstract_engine.collision.BoundaryCollisionDetector;
import io.github.some_example_name.lwjgl3.application_classes.entities.Character;

public class PlayerCollideBoundaryDetector extends BoundaryCollisionDetector<Character>{
	
	public PlayerCollideBoundaryDetector(Character player){
		super(player);
	}
	
	// This collision event is resolved by ensuring Player cant go past screen boundary
	@Override
	public void resolveCollision() {
		super.resolveCollision();
	}
}
