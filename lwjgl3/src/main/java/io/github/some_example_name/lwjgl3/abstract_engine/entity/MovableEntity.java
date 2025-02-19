package io.github.some_example_name.lwjgl3.abstract_engine.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import io.github.some_example_name.lwjgl3.abstract_engine.movement.iMovable;

public abstract class MovableEntity extends Entity implements iMovable {
	protected float speed;

    public MovableEntity(){
        super();
        this.speed = 0;
    }
    
    public MovableEntity(String name, Vector2 position, Texture texture, float speed){
        super(name, position, texture);
        this.speed = speed;
    }
    
    public MovableEntity(String name, Vector2 position, Texture texture){
        super(name, position, texture);
    }
}
