package io.github.some_example_name.lwjgl3.application_classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import io.github.some_example_name.lwjgl3.abstract_engine.entity.Entity;

public class NonPlayableCharacter extends Entity{

    public NonPlayableCharacter(){
        super();
    }

    public NonPlayableCharacter(String name, Vector2 position, Texture texture, Boolean collidable){
        super(name, position, texture, collidable);
    }

    @Override
    public void moveAIControlled() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void moveUserControlled() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
