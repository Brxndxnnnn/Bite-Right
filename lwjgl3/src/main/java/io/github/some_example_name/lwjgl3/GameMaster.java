package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.ApplicationAdapter;

import io.github.some_example_name.lwjgl3.collision.CollisionManager;
import io.github.some_example_name.lwjgl3.entity.EntityManager;
import io.github.some_example_name.lwjgl3.inputoutput.InputOutputManager;
import io.github.some_example_name.lwjgl3.movement.MovementManager;
import io.github.some_example_name.lwjgl3.scene.SceneManager;

public class GameMaster extends ApplicationAdapter {
    private EntityManager entityManager;
    private CollisionManager collisionManager;
    private MovementManager movementManager;
    private SceneManager sceneManager;
    private InputOutputManager inputOutputManager;

    @Override
    public void create(){
        entityManager = EntityManager();
    }
}
