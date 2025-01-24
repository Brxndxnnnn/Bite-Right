package io.github.some_example_name.lwjgl3.entity;

import com.badlogic.gdx.graphics.Texture;

public class Player extends Entity {
    public Player(){
        super();
    }

    public Player(String name, int currentHealth, int maxHealth, int attack, float x, float y, float speed, boolean isAlive, Texture texture){
        super(name, currentHealth, maxHealth, attack, x, y, speed, isAlive, texture);
    }
}
