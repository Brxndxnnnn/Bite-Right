package io.github.some_example_name.lwjgl3.abstract_engine.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class NonMovableEntity extends Entity {
	
	public NonMovableEntity(String name, Vector2 position, Texture texture){
        super(name, position, texture);
    }
}
