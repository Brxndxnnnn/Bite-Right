package io.github.some_example_name.lwjgl3.abstract_engine.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class MovableEntity extends Entity {
    protected float speed;
    protected Vector2 velocity;
    protected float width;
    protected float height;

    // Default Constructor
    public MovableEntity(){
        super();
        this.speed = 0;
        this.velocity = new Vector2(0,0);
        this.width = 0;
        this.height = 0;
    }
    
    // Constructor 1 (Overloading)
    public MovableEntity(String name, Vector2 position, Texture texture, float speed){
        super(name, position, texture);
        this.speed = speed;
        this.velocity = new Vector2(0,0);
        
        // Initialize width and height from texture if available
        if (texture != null) {
            this.width = texture.getWidth();
            this.height = texture.getHeight();
        } else {
            this.width = 0;
            this.height = 0;
        }
    }

    // Constructor 2 (Overloading)
    public MovableEntity(String name, Vector2 position, Texture texture){
        super(name, position, texture);
        this.speed = 0;
        this.velocity = new Vector2(0,0);
        
        // Initialize width and height from texture if available
        if (texture != null) {
            this.width = texture.getWidth();
            this.height = texture.getHeight();
        } else {
            this.width = 0;
            this.height = 0;
        }
    }

    // Getter for Velocity
    public Vector2 getVelocity(){
        return velocity;
    }

    // Setter for Velocity
    public void setVelocity(Vector2 velocity){
        this.velocity = velocity;
    }
    
    /**
     * Sets the size of the entity
     * @param width The width of the entity
     * @param height The height of the entity
     */
    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }
    
    /**
     * Gets the current width of the entity
     * @return The width
     */
    public float getWidth() {
        return width;
    }
    
    /**
     * Gets the current height of the entity
     * @return The height
     */
    public float getHeight() {
        return height;
    }
    
    /**
     * Overrides the default draw method to use custom size
     */
    @Override
    public void draw(SpriteBatch batch) {
        // If custom size is set, use it, otherwise use texture's original size
        if (width > 0 && height > 0) {
            batch.draw(getTexture(), getPosition().x, getPosition().y, width, height);
        } else {
            // Fall back to original texture size
            super.draw(batch);
        }
    }
}