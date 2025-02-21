package io.github.some_example_name.lwjgl3.abstract_engine.collision;

import com.badlogic.gdx.math.MathUtils;

import io.github.some_example_name.lwjgl3.abstract_engine.entity.Entity;

public abstract class BoundaryCollisionDetector <T extends Collidable> extends CollisionDetector{
	private final T entity;
    private final float screenWidth, screenHeight, textureWidth, textureHeight;
	
	protected BoundaryCollisionDetector(T entity){
		this.entity = entity;
		this.screenWidth = com.badlogic.gdx.Gdx.graphics.getWidth();
        this.screenHeight = com.badlogic.gdx.Gdx.graphics.getHeight();
        this.textureWidth = ((Entity) entity).getTexture().getWidth();
        this.textureHeight = ((Entity) entity).getTexture().getHeight();
	}
	
	@Override
	public boolean checkCollision() {
		return ((Entity) entity).getPosition().x < 0 || ((Entity) entity).getPosition().x > screenWidth - textureWidth
			|| ((Entity) entity).getPosition().y < 0 || ((Entity) entity).getPosition().y > screenHeight - textureHeight;      
	}
	
	@Override
	public void resolveCollision() {
		if(((Entity) entity).getPosition().x < 0 || 
		   ((Entity) entity).getPosition().x > getScreenWidth() - getTextureWidth()) {
			
			((Entity) entity).setPositionX(MathUtils.clamp(((Entity) entity).getPosition().x, 0, getScreenWidth() - getTextureWidth()));
			
		}
		
		if(((Entity) entity).getPosition().y < 0 || 
		   ((Entity) entity).getPosition().y > getScreenHeight() - getTextureHeight()) {
					
			((Entity) entity).setPositionY(MathUtils.clamp(((Entity) entity).getPosition().y, 0, getScreenHeight() - getTextureHeight()));
			
		}
				
	}
	
	// Getter for Entity
	public Collidable getEntity() {
		return entity;
	}
	
	// Getter for ScreenWidth
	public float getScreenWidth() {
		return screenWidth;
	}
	
	// Getter for ScreenHeight
	public float getScreenHeight() {
		return screenHeight;
	}
	
	// Getter for TextureWidth
	public float getTextureWidth() {
		return textureWidth;
	}
	
	// Getter for TextureHeight
	public float getTextureHeight() {
		return textureHeight;
	}
}
