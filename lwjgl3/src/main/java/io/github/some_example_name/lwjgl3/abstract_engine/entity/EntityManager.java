package io.github.some_example_name.lwjgl3.abstract_engine.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.some_example_name.lwjgl3.application_classes.Character;
import io.github.some_example_name.lwjgl3.application_classes.NonPlayableCharacter;

public class EntityManager {
    private List<Entity> entities;

    public EntityManager(){
        this.entities = new ArrayList<>();
    }

//    public void intializeEntities(){
//        Random random = new Random();
//        
//        // louis added
//        Vector2 playerPosition = new Vector2(100, 100); // initial position for the player
//        Character player = new Character("Player", playerPosition, new Texture("player.png"), true, 2f);
//        entities.add(player);
//        
//        
//        
//        // ROUGH DRAFT, NEED CHANGE LATER ON - BRANDON //
//        for (int i = 0; i < 10; i++){
//            float randomX = random.nextFloat();
//            float randomY = random.nextFloat();
//            Vector2 position = new Vector2(randomX, randomY);
//            Entity entity = new NonPlayableCharacter("lol", position, new Texture("demon.png"), false);
//            entities.add(entity);
//        }
//    }

    public void addEntities(Entity entity){
        entities.add(entity);
    }

    public void checkForEntitiesToBeRemoved(){
		// Creates an Iterator for the list of entities - to remove/access safely
		Iterator<Entity> iterator = this.entities.iterator();
		
		// Checks if there are more elements in the collection
		while(iterator.hasNext()) {
			Entity entity = iterator.next();
			if(entity.shouldBeRemoved()) {
				iterator.remove();
			}
		}
	}
    
    public Entity getEntity(int i) {
		return this.entities.get(i);
	}

    public void drawEntites(SpriteBatch batch){
        for (Entity entity : entities) {
            entity.draw(batch);
        }
    }

    public void moveEntities(){
    	for (Entity entity : entities) {
    	    // determine entity type and call the appropriate movement method
    	    if (entity instanceof Character) {
    	        // ((Character) entity).moveUserControlled();
    	    } else if (entity instanceof NonPlayableCharacter) {
    	        ((NonPlayableCharacter) entity).moveAIControlled();
    	    }
    	}
    }
}
