package io.github.some_example_name.lwjgl3.abstract_engine.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity{
    private String name;
    private Vector2 position;
    private Texture texture;
    private boolean toBeRemoved = false;

    // Default Constructor
    public Entity(){
        this.name = "Empty name";
        this.position = new Vector2(0, 0);
        this.texture = null;
    }

    // Constructor 1 (Overloading)
    public Entity(String name, Vector2 position, Texture texture){
        this.name = name;
        this.position = position;
        this.texture = texture;
    }

    // Getter for Name
    public String getName(){
        return name;
    }

    // Setter for Name
    public void setName(String name){
        this.name = name;
    }

    // Getter for Position
    public Vector2 getPosition(){
        return position;
    }

    // Setter for Position
    public void setPosition(Vector2 position){
        this.position = position;
    }	
    
    // Setter for Position X
    public void setPositionX(float x){
        position.x = x;
    }

    // Setter for Position Y
    public void setPositionY(float y){
        position.y = y;
    }

    // Getter for Texture
    public Texture getTexture(){
        return texture;
    }

    // Setter for Texture
    public void setTexture(Texture texture){
        this.texture = texture;
    }

    // This function draw the Entity
    public void draw(SpriteBatch batch){
        batch.draw(texture, position.x, position.y);
    }

    // For Collision Handling	
	public void setToBeRemoved() {
		toBeRemoved = true;
	}
	
	public boolean shouldBeRemoved() {
		return toBeRemoved;
	}

}
