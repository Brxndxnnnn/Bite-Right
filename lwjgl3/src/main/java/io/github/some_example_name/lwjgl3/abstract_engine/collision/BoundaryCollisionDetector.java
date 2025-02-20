package io.github.some_example_name.lwjgl3.abstract_engine.collision;

import com.badlogic.gdx.math.MathUtils;

import io.github.some_example_name.lwjgl3.abstract_engine.entity.Entity;

public abstract class BoundaryCollisionDetector <T extends iCollidable> extends CollisionDetector{
	private T entity;
	private float screenWidth, screenHeight, textureWidth, textureHeight;
	
	protected BoundaryCollisionDetector(T entity){
		this.entity = entity;
		this.screenWidth = com.badlogic.gdx.Gdx.graphics.getWidth();
        this.screenHeight = com.badlogic.gdx.Gdx.graphics.getHeight();
        this.textureWidth = ((Entity) entity).getTexture().getWidth();
        this.textureHeight = ((Entity) entity).getTexture().getHeight();
	}
	
	public boolean checkCollision() {
		return ((Entity) entity).getPosition().x < 0 || ((Entity) entity).getPosition().x > screenWidth - this.textureWidth
			|| ((Entity) entity).getPosition().y < 0 || ((Entity) entity).getPosition().y > screenHeight - this.textureHeight;      
	}
	
	public void resolveCollision() {
		if(((Entity) entity).getPosition().x < 0 || 
		   ((Entity) entity).getPosition().x > this.getScreenWidth() - this.getTextureWidth()) {
			
			((Entity) entity).setPositionX(MathUtils.clamp(((Entity) entity).getPosition().x, 0, this.getScreenWidth() - this.getTextureWidth()));
			
		}
		
		if(((Entity) entity).getPosition().y < 0 || 
		   ((Entity) entity).getPosition().y > this.getScreenHeight() - this.getTextureHeight()) {
					
			((Entity) entity).setPositionY(MathUtils.clamp(((Entity) entity).getPosition().y, 0, this.getScreenHeight() - this.getTextureHeight()));
			
		}
				
	}
	
	public iCollidable getEntity() {
		return this.entity;
	}
	
	public float getScreenWidth() {
		return this.screenWidth;
	}
	
	public float getScreenHeight() {
		return this.screenHeight;
	}
	
	public float getTextureWidth() {
		return this.textureWidth;
	}
	
	public float getTextureHeight() {
		return this.textureHeight;
	}
}
