package io.github.some_example_name.lwjgl3.abstract_engine.collision;

import java.util.ArrayList;
import java.util.Iterator;

public class CollisionManager {
	private ArrayList<CollisionDetector> detectors;
	
	public CollisionManager(){
		detectors = new ArrayList<CollisionDetector>();
	}
	
	public void addDetectors(CollisionDetector detector){
		this.detectors.add(detector);
	}
	
	public void checkAllCollisions(){
		// for each detector in the detector list
		int i = 1;
		for(CollisionDetector detector:this.detectors) {
			System.out.printf("detector %d:", i);
			if(detector.checkCollision() == true) {
				detector.resolveCollision();
				System.out.println("yes");
			} else {
				System.out.println("no");
			}
			i += 1;
		}
	}
	
	public void checkForDetectorsToBeRemoved() {
		// Creates an Iterator for the list of entities - to remove/access safely
		Iterator<CollisionDetector> iterator = this.detectors.iterator();
		// Checks if there are more elements in the collection
		while(iterator.hasNext()) {
			CollisionDetector detector = iterator.next();
			if(detector.shouldBeRemoved()) {
				iterator.remove();
			}
		}
	}
}