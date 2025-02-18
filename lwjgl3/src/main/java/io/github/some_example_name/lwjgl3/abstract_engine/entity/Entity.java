package io.github.some_example_name.lwjgl3.abstract_engine.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity{
    private String name;
    private Vector2 position;
    private Texture texture;
    private boolean toBeRemoved = false;

    public Entity(){
        this.name = "Empty name";
        this.position = new Vector2(0, 0);
        this.texture = null;
    }

    public Entity(String name, Vector2 position, Texture texture){
        this.name = name;
        this.position = position;
        this.texture = texture;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Vector2 getPosition(){
        return position;
    }

    public void setPosition(Vector2 position){
        this.position = position;
    }	

    public Texture getTexture(){
        return texture;
    }

    public void setTexture(Texture texture){
        this.texture = texture;
    }

    public void draw(SpriteBatch batch){
        batch.draw(texture, position.x, position.y);
    }

    public void update(){}

    // For Collision Handling	
	public void setToBeRemoved() {
		this.toBeRemoved = true;
	}
	
	public boolean shouldBeRemoved() {
		return this.toBeRemoved;
	}
	
}
