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
		return entities.get(i);
	}

	public List<Entity> getEntities(){
		return entities;
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
