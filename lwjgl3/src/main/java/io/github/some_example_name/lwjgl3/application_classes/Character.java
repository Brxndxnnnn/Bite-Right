package io.github.some_example_name.lwjgl3.application_classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import io.github.some_example_name.lwjgl3.abstract_engine.entity.Entity;

public class Character extends Entity{
    private float speed;

    public Character(){
        super();
        this.speed = 0;
    }

    public Character(String name, Vector2 position, Texture texture, Boolean collidable, float speed){
        super(name, position, texture, collidable);
        this.speed = speed;
    }

    @Override
    public void moveAIControlled() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void moveUserControlled() {
        Vector2 position = getPosition();

        if (Gdx.input.isKeyPressed(Keys.LEFT))
        {
            position.x -= (speed * 50) * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT)){
            position.x += (speed * 50) * Gdx.graphics.getDeltaTime();
        } 
        if (Gdx.input.isKeyPressed(Keys.UP))
        {
            position.y += (speed * 50) * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Keys.DOWN)){
            position.y -= (speed * 50) * Gdx.graphics.getDeltaTime();
        }
        
        // player character stay within the screen boundary
        float screenWidth = com.badlogic.gdx.Gdx.graphics.getWidth();
        float screenHeight = com.badlogic.gdx.Gdx.graphics.getHeight();
        float textureWidth = getTexture().getWidth();
        float textureHeight = getTexture().getHeight();
        
        if (position.x < 0) {
            position.x = 0;
        }
        if (position.x > screenWidth - textureWidth) {
            position.x = screenWidth - textureWidth;
        }
        if (position.y < 0) {
            position.y = 0;
        }
        if (position.y > screenHeight - textureHeight) {
            position.y = screenHeight - textureHeight;
        }

        setPosition(position);
    }

    @Override
    public boolean collisionDetection(Entity Entity1) {
        return getBoundingBox().overlaps(Entity1.getBoundingBox());  
    }
	

	@Override
    public void collisionResolution(Entity entity1) {
    	System.out.println("Player character collision resolution");	
    }

	@Override
    public Rectangle getBoundingBox() {
        return new Rectangle(getPosition().x, getPosition().y, getTexture().getWidth(), getTexture().getHeight());
    }

}
