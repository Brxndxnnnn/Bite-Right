package io.github.some_example_name.lwjgl3.entity;

import com.badlogic.gdx.graphics.Texture;

public class Entity {
    private String name;
    private int currentHealth;
    private int maxHealth;
    private int attack;
    private float x;
    private float y;
    private float speed;
    private boolean isAlive;
    private Texture texture;

    public Entity(){
        this.name = "Entity";
        this.currentHealth = 10;
        this.maxHealth = 10;
        this.attack = 10;
        this.x = 0;
        this.y = 0;
        this.speed = 0;
        this.isAlive = false;
        this.texture = null;
    }

    public Entity(String name, int currentHealth, int maxHealth, int attack, float x, float y, float speed, boolean isAlive, Texture texture){
        this.name = name;
        this.currentHealth = currentHealth;
        this.maxHealth = maxHealth;
        this.attack = attack;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.isAlive = isAlive;
        this.texture = texture;
    }

    public String getName(){
        return name;
    }
    
    void setName(String name){
        this.name = name;
    }

    int getCurrentHealth(){
        return currentHealth;
    }
    
    void setCurrentHealth(int currentHealth){
        this.currentHealth = currentHealth;
    }

    int getMaxHealth(){
        return maxHealth;
    }
    
    void setMaxHealth(int maxHealth){
        this.maxHealth = maxHealth;
    }

    public int getAttack(){
        return attack;
    }
    
    void setAttack(int attack){
        this.attack = attack;
    }

    public float getX(){
        return x;
    }

    void setX(float x){
        this.x = x;
    }

    public float getY(){
        return y;
    }

    void setY(float y){
        this.y = y;
    }

    float getSpeed(){
        return speed;
    }

    void setSpeed(float speed){
        this.speed = speed;
    }

    boolean getIsAlive(){
        return isAlive;
    }

    void setIsAlive(boolean isAlive){
        this.isAlive = isAlive;
    }

    public Texture getTexture(){
        return texture;
    }

    void setTexture(Texture texture){
        this.texture = texture;
    }

    public void reduceHealth(int damage){
        this.currentHealth -= damage;
    }
}
