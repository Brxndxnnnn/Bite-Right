package io.github.some_example_name.lwjgl3.application_classes.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import io.github.some_example_name.lwjgl3.abstract_engine.entity.NonMovableEntity;

public class Floor extends NonMovableEntity {
    private float width;
    private float height;

    public Floor(String name, Vector2 position, Texture texture) {
        super(name, position, texture);
        this.width = texture.getWidth();
        this.height = texture.getHeight();
    }

    /**
     * Sets the size of the floor
     * @param width The width of the floor
     * @param height The height of the floor
     */
    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }
    
    /**
     * Overrides the default draw method to scale the texture to the desired size
     */
    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(getTexture(), getPosition().x, getPosition().y, width, height);
    }
}