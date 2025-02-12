package io.github.some_example_name.lwjgl3.abstract_engine.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import io.github.some_example_name.lwjgl3.application_classes.NonPlayableCharacter;
import io.github.some_example_name.lwjgl3.application_classes.Character;

public class EntityManager {
    private List<Entity> entities;

    public EntityManager(){
        this.entities = new ArrayList<>();
    }

    public void intializeEntities(){
        Random random = new Random();
        
        // louis added
        Vector2 playerPosition = new Vector2(100, 100); // initial position for the player
        Character player = new Character("Player", playerPosition, new Texture("player.png"), true, 2f);
        entities.add(player);
        
        
        
        // ROUGH DRAFT, NEED CHANGE LATER ON - BRANDON //
        for (int i = 0; i < 10; i++){
            float randomX = random.nextFloat();
            float randomY = random.nextFloat();
            Vector2 position = new Vector2(randomX, randomY);
            Entity entity = new NonPlayableCharacter("lol", position, new Texture("demon.png"), false);
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
        
        // louis added
    	for (Entity entity : entities) {
    	    // determine entity type and call the appropriate movement method
    	    if (entity instanceof Character) {
    	        ((Character) entity).moveUserControlled();
    	    } else if (entity instanceof NonPlayableCharacter) {
    	        ((NonPlayableCharacter) entity).moveAIControlled();
    	    }
    	}

    	// collision detection
    	for (int i = 0; i < entities.size(); i++) {
    	    Entity entity1 = entities.get(i);

    	    for (int j = i + 1; j < entities.size(); j++) {
    	        Entity entity2 = entities.get(j);

    	        // check for collision between character and npc in both directions
    	        if ((entity1 instanceof Character && entity2 instanceof NonPlayableCharacter)) {

    	            Character character = entity1 instanceof Character ? (Character) entity1 : (Character) entity2;
    	            NonPlayableCharacter npc = entity1 instanceof NonPlayableCharacter ? (NonPlayableCharacter) entity1 : (NonPlayableCharacter) entity2;

    	            if (character.collisionDetection(npc)) {
    	                System.out.println("Collision detected between Player and NPC");
    	                character.collisionResolution(npc);
    	                npc.collisionResolution(character);
    	            }
    	        }
    	    }
    	}

    }
}
