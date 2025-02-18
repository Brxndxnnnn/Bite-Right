package io.github.some_example_name.lwjgl3.abstract_engine.collision;
import com.badlogic.gdx.math.Rectangle;

public interface iCollidable {
	public Rectangle getBoundingBox();
} // can use general class to accept other types like Circle/Triangle etc. if needed later on
