package io.github.some_example_name.lwjgl3.abstract_engine.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.some_example_name.lwjgl3.abstract_engine.movement.AIMovable;
import io.github.some_example_name.lwjgl3.application_classes.entities.NonPlayableCharacter;

public class EntityManager {
    private final List<Entity> entities;

	// Constructor
    public EntityManager(){
        this.entities = new ArrayList<>();
    }

	// This function add Entity into list
    public void addEntities(Entity entity){
        entities.add(entity);
    }

	// This function checks for Entity to remove
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
    
	// This function retrieve Entity from list based on index
    public Entity getEntity(int i) {
		return entities.get(i);
	}

	// Getter for EntityList
	public List<Entity> getEntities(){
		return entities;
	}

	// This function draw all Entity in list
    public void drawEntites(SpriteBatch batch){
        for (Entity entity : entities) {
            entity.draw(batch);
        }
    }

	// This function move all Entity in list
    public void moveEntities(){
    	for (Entity entity : entities) {
			// Move NPC's in Entity List as Player movement is handled by IO Keybinds instead
    	    if (entity instanceof AIMovable) {
    	        ((NonPlayableCharacter) entity).moveAIControlled();
    	    }
    	}
    }
}
