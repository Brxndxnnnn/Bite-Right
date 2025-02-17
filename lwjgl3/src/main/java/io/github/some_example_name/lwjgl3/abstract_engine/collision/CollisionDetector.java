package io.github.some_example_name.lwjgl3.abstract_engine.collision;

public abstract class CollisionDetector {
	private boolean toBeRemoved = false;
	public abstract boolean checkCollision();
	public abstract void resolveCollision();
	
	public void setToBeRemoved() {
		this.toBeRemoved = true;
	}
	
	public boolean shouldBeRemoved() {
		return this.toBeRemoved;
	}
}