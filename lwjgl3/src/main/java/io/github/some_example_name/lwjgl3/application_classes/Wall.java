package io.github.some_example_name.lwjgl3.application_classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import io.github.some_example_name.lwjgl3.abstract_engine.collision.iCollidable;
import io.github.some_example_name.lwjgl3.abstract_engine.entity.NonMovableEntity;

public class Wall extends NonMovableEntity implements iCollidable{

    public Wall(String name, Vector2 position, Texture texture){
        super(name, position, texture);
    }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(getPosition().x, getPosition().y, getTexture().getWidth(), getTexture().getHeight());
    }
}
