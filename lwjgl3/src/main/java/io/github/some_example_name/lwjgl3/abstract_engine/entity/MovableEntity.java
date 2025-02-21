package io.github.some_example_name.lwjgl3.abstract_engine.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public abstract class MovableEntity extends Entity {
	protected float speed;
    protected Vector2 velocity;

    public MovableEntity(){
        super();
        this.speed = 0;
        this.velocity = new Vector2(0,0);
    }
    
    public MovableEntity(String name, Vector2 position, Texture texture, float speed){
        super(name, position, texture);
        this.speed = speed;
        this.velocity = new Vector2(0,0);
    }
    
    public MovableEntity(String name, Vector2 position, Texture texture){
        super(name, position, texture);
        this.speed = 0;
        this.velocity = new Vector2(0,0);
    }

    public Vector2 getVelocity(){
        return velocity;
    }

    public void setVelocity(Vector2 velocity){
        this.velocity = velocity;
    }
}
