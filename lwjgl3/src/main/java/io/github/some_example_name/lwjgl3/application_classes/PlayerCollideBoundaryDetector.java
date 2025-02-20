package io.github.some_example_name.lwjgl3.application_classes;

import io.github.some_example_name.lwjgl3.abstract_engine.collision.BoundaryCollisionDetector;

public class PlayerCollideBoundaryDetector extends BoundaryCollisionDetector<Character>{
	
	PlayerCollideBoundaryDetector(Character player){
		super(player);
	}
	
	@Override
	public void resolveCollision() {
		super.resolveCollision();
	}
}
