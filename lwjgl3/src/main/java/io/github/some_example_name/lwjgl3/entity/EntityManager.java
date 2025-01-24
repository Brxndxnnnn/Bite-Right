package io.github.some_example_name.lwjgl3.entity;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.some_example_name.lwjgl3.collision.CollisionManager;

public class EntityManager {
    private CollisionManager collisionManager;
    private Entity player;
    private List<Entity> enemies;

    public EntityManager() {
        this.collisionManager = new CollisionManager();
        this.player = new Player();
        this.enemies = new ArrayList<>();
    }

    public void initializeEntities(){
        Texture playerTexture = new Texture(Gdx.files.internal("player.png"));
        Texture monsterTexture = new Texture(Gdx.files.internal("demon.png")); 
        player = new Player("Player", 10, 10, 2, 100, 200, 2, true, playerTexture);

        for (int i = 0; i < 10; i++){
            enemies.add(i, new Enemy("Enemy", 10, 10, 2, 200, 400, 2, true, monsterTexture));
        }
    }

    public void drawEntities(SpriteBatch batch){
        batch.begin();
        batch.draw(player.getTexture(), player.getX(), player.getY(), player.getTexture().getWidth(), player.getTexture().getHeight());
        for (Entity enemy : enemies){
            batch.draw(enemy.getTexture(), enemy.getX(), enemy.getY(), enemy.getTexture().getWidth(), enemy.getTexture().getHeight());
        }
        batch.end();
    }

    public void removeEntities(){}

    public void detectEntitiesCollision(){
        collisionManager.detectCollisions(player, enemies);
    }
}
