package io.github.some_example_name.lwjgl3.abstract_engine.collision;

public abstract class EntityCollisionDetector <T extends Collidable, U extends Collidable> extends CollisionDetector{
	private final T entityA;
	private final U entityB;
	
	// Constructor
	protected EntityCollisionDetector(T a, U b){
		this.entityA = a;
		this.entityB = b;
	}
	
	// This function checks for collision
	@Override
	public boolean checkCollision() {
		return entityA.getBoundingBox().overlaps(entityB.getBoundingBox());
	}
	
	@Override
	public abstract void resolveCollision();
	
	// Getter for EntityA
	public Collidable getEntityA() {
		return entityA;
	}
	
	//Getter for EntityB
	public Collidable getEntityB() {
		return entityB;
	}
}
