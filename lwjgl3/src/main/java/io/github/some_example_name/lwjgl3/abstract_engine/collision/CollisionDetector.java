package io.github.some_example_name.lwjgl3.abstract_engine.collision;

public abstract class CollisionDetector {
	private boolean toBeRemoved = false;
	public abstract boolean checkCollision();
	public abstract void resolveCollision();
	
	// Setter for toBeRemoved
	public void setToBeRemoved() {
		toBeRemoved = true;
	}
	
	// Getter for toBeRemoved
	public boolean shouldBeRemoved() {
		return toBeRemoved;
	}
}
