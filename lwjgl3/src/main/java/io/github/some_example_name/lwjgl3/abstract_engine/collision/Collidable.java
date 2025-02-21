package io.github.some_example_name.lwjgl3.abstract_engine.collision;
import com.badlogic.gdx.math.Rectangle;

// This interface is implemented by objects/classes that are collidable with other collidables
public interface Collidable {
	public Rectangle getBoundingBox();
}
