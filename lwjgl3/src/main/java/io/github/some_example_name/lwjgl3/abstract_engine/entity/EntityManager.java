package io.github.some_example_name.lwjgl3.abstract_engine.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import io.github.some_example_name.lwjgl3.application_classes.NonPlayableCharacter;

public class EntityManager {
    private List<Entity> entities;

    public EntityManager(){
        this.entities = new ArrayList<>();
    }

    public void intializeEntities(){
        Random random = new Random();
        // ROUGH DRAFT, NEED CHANGE LATER ON - BRANDON //
        for (int i = 0; i < 10; i++){
            float randomX = random.nextFloat();
            float randomY = random.nextFloat();
            Vector2 position = new Vector2(randomX, randomY);
            Entity entity = new NonPlayableCharacter("lol", position, null, false);
            entities.add(entity);
        }
    }

    public void addEntities(Entity entity){
        entities.add(entity);
    }

    public void removeEntities(Entity entity){
        entities.remove(entity);
    }

    public void drawEntites(SpriteBatch batch){
        for (Entity entity : entities) {
            entity.draw(batch);
        }
    }

    public void updateEntities(){
        for (Entity entity : entities){
            entity.update();
        }
    }
}
