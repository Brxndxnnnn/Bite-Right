package io.github.some_example_name.lwjgl3.application_classes.collision_detectors;

import io.github.some_example_name.lwjgl3.abstract_engine.collision.BoundaryCollisionDetector;
import io.github.some_example_name.lwjgl3.abstract_engine.entity.Entity;
import io.github.some_example_name.lwjgl3.application_classes.entities.NonPlayableCharacter;

public class EnemyCollideBoundaryDetector extends BoundaryCollisionDetector<NonPlayableCharacter>{
	
	public EnemyCollideBoundaryDetector(NonPlayableCharacter player){
		super(player);
	}
	
	@Override
	public void resolveCollision() {
		super.resolveCollision();
		if(((Entity) super.getEntity()).getPosition().x < 0 || 
		   ((Entity) super.getEntity()).getPosition().x > super.getScreenWidth() - this.getTextureWidth()) {
			
			float x = ((Entity) super.getEntity()).getPosition().x;
			((Entity) super.getEntity()).setPositionX(x * -1);
			
		}
		
		if(((Entity) super.getEntity()).getPosition().y < 0 || 
		   ((Entity) super.getEntity()).getPosition().y > super.getScreenHeight() - this.getTextureHeight()) {
					
			float y = ((Entity) super.getEntity()).getPosition().y;
			((Entity) super.getEntity()).setPositionY(y * -1);
			
		}
		
//		if(((Entity) super.getEntity()).getPosition().x < 0){
//			((Entity) super.getEntity()).setPositionX(0);
//		}
//		if(((Entity) super.getEntity()).getPosition().x > super.getScreenWidth() - this.getTextureWidth()) {
//			
//		}
	}
}
