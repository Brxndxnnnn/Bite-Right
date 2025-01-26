package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.some_example_name.lwjgl3.entity.EntityManager;
import io.github.some_example_name.lwjgl3.inputoutput.InputOutputManager;
import io.github.some_example_name.lwjgl3.movement.MovementManager;
import io.github.some_example_name.lwjgl3.scene.SceneManager;

public class GameMaster extends ApplicationAdapter {
    private EntityManager entityManager;
    private MovementManager movementManager;
    private SceneManager sceneManager;
    private InputOutputManager inputOutputManager;
    private SpriteBatch batch;

    @Override
    public void create(){
        entityManager = new EntityManager();
        movementManager = new MovementManager();
        sceneManager = new SceneManager();
        inputOutputManager = new InputOutputManager(movementManager);
        batch = new SpriteBatch();
    }

    @Override
    public void render(){
        entityManager.initializeEntities();
        entityManager.drawEntities(batch);
        entityManager.detectEntitiesCollision();

        // track scenemanager current screen
        // Main Menu (Scene, InputOutput)

        // In-Game Screen (Scene, InputOutput, Entity, Movement, Collision )
        // render entities
        // movement listener
        // collision listener

        // Paused Screen (Scene, InputOutput, Entity)

        // Result Screen (Scene, InputOutput)
    }

    @Override
    public void dispose(){}
}
