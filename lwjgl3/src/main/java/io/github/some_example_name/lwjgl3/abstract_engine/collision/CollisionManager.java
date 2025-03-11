package io.github.some_example_name.lwjgl3.abstract_engine.collision;

import java.util.ArrayList;
import java.util.Iterator;

public class CollisionManager {
    private final ArrayList<CollisionDetector> detectors;
    
    // Default Constructor
    public CollisionManager(){
        detectors = new ArrayList<>();
    }
    
    // This function add CollisionDetector to list
    public void addDetectors(CollisionDetector detector){
        detectors.add(detector);
    }
    
    // This function clears all collision detectors
    public void clearDetectors() {
        detectors.clear();
    }
    
    // This function checks for each detector in list
    public void checkAllCollisions(){
        // for each detector in the detector list
        for(CollisionDetector detector : detectors) {
            if(detector.checkCollision() == true) {
                detector.resolveCollision();
            }
        }
    }
    
    // This function checks for detectors to be removed in the list
    public void checkForDetectorsToBeRemoved() {
        // Creates an Iterator for the list of entities - to remove/access safely
        Iterator<CollisionDetector> iterator = detectors.iterator();
        // Checks if there are more elements in the collection
        while(iterator.hasNext()) {
            CollisionDetector detector = iterator.next();
            if(detector.shouldBeRemoved()) {
                iterator.remove();
            }
        }
    }
}