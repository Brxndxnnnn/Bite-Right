package io.github.some_example_name.lwjgl3.application_classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
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
            setPosition(position);
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT)){
            position.x += (speed * 50) * Gdx.graphics.getDeltaTime();
            setPosition(position);
        } 
        if (Gdx.input.isKeyPressed(Keys.UP))
        {
            position.y += (speed * 50) * Gdx.graphics.getDeltaTime();
            setPosition(position);
        }
        if (Gdx.input.isKeyPressed(Keys.DOWN)){
            position.y -= (speed * 50) * Gdx.graphics.getDeltaTime();
            setPosition(position);
        } 
    }

}
