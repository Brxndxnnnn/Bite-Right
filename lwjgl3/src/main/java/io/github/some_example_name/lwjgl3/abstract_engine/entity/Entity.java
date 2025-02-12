package io.github.some_example_name.lwjgl3.abstract_engine.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import io.github.some_example_name.lwjgl3.abstract_engine.collision.iCollidable;
import io.github.some_example_name.lwjgl3.abstract_engine.movement.iMovable;

public abstract class Entity implements iMovable, iCollidable{
    private String name;
    private Vector2 position;
    private Texture texture;
    private Boolean collidable;

    public Entity(){
        this.name = "Empty name";
        this.position = new Vector2(0, 0);
        this.texture = null;
        this.collidable = false;
    }

    public Entity(String name, Vector2 position, Texture texture, Boolean collidable){
        this.name = name;
        this.position = position;
        this.texture = texture;
        this.collidable = collidable;
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

    public Boolean isCollidable(){
        return collidable;
    }

    public void setCollidable(Boolean collidable){
        this.collidable = collidable;
    }

    public void draw(SpriteBatch batch){
        batch.draw(texture, position.x, position.y);
    }

    public void update(){}

    @Override
    public void moveAIControlled() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void moveUserControlled() {}

	public abstract Rectangle getBoundingBox();
}
