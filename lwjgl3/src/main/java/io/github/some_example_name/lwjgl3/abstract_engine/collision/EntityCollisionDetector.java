package io.github.some_example_name.lwjgl3.abstract_engine.collision;

public abstract class EntityCollisionDetector <T extends iCollidable, U extends iCollidable> extends CollisionDetector{
	private T entityA;
	private U entityB;
	
	protected EntityCollisionDetector(T a, U b){
		this.entityA = a;
		this.entityB = b;
	}
	
	@Override
	public boolean checkCollision() {
		return this.entityA.getBoundingBox().overlaps(this.entityB.getBoundingBox());
	}
	
	@Override
	public abstract void resolveCollision();
	
	public iCollidable getEntityA() {
		return this.entityA;
	}
	
	public iCollidable getEntityB() {
		return this.entityB;
	}
}

