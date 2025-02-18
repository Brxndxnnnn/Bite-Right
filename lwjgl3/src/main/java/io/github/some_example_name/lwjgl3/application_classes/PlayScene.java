package io.github.some_example_name.lwjgl3.application_classes;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import io.github.some_example_name.lwjgl3.abstract_engine.collision.CollisionDetector;
import io.github.some_example_name.lwjgl3.abstract_engine.collision.CollisionManager;
import io.github.some_example_name.lwjgl3.abstract_engine.entity.Entity;
import io.github.some_example_name.lwjgl3.abstract_engine.entity.EntityManager;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.PrimaryScene;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneManager;

public class PlayScene extends PrimaryScene{
	private TextButton pauseBtn;
	private TextButton quitBtn;
	private EntityManager entityManager;
	private CollisionManager collisionManager;
	private final int NUM_ENEMIES = 10;

	public PlayScene(SceneManager sceneManager, Boolean isHome) {
		super(sceneManager, isHome);
		setAudioFilePath("music1.mp3");

		// INITIALIZE ENTITY MANAGER
	    entityManager = new EntityManager();
	    
	    // INITIALIZE ENTITIES FOR THE GAME
        Entity player = new Character("Player", new Vector2(100, 100), new Texture("player.png"), 2f);
        entityManager.addEntities(player);
        
        // ROUGH DRAFT, NEED CHANGE LATER ON - BRANDON //
        Random random = new Random();
        for (int i = 0; i < NUM_ENEMIES; i++){
            float randomX = random.nextFloat();
            float randomY = random.nextFloat();
            Vector2 position = new Vector2(randomX, randomY);
            Entity enemy = new NonPlayableCharacter("Enemy", position, new Texture("demon.png"));
            entityManager.addEntities(enemy);
        }

        // INITIALIZE COLLISION MANAGER 
	    collisionManager = new CollisionManager();
	    
	    // INITIALIZE COLLISION DETECTORS
	    for (int i = 1; i <= NUM_ENEMIES; i++) {
        	CollisionDetector playerCollideEnemyDetector = new PlayerCollideEnemyDetector((Character) player, (NonPlayableCharacter) entityManager.getEntity(i));
        	collisionManager.addDetectors(playerCollideEnemyDetector);
        }
	    
		// INITIALIZE BUTTONS
		pauseBtn = new TextButton("Pause", skin);
		quitBtn = new TextButton("Quit", skin);

		// INITIALIZE MAIN TABLE FOR THE TITLE
		Table titleTable = new Table();
		titleTable.setFillParent(true);
		titleTable.top(); // Align content to the top
		stage.addActor(titleTable);

		Label label = new Label("Play Scene", skin);
		titleTable.add(label).center().padTop(20); // Center the title at the top with padding

		// INITIALIZE BUTTON TABLE
		Table buttonTable = new Table();
		buttonTable.setFillParent(true);
		buttonTable.top().left(); // Align to top-left
		stage.addActor(buttonTable);

		// ADD BUTTONS TO THE BUTTON TABLE
		buttonTable.add(pauseBtn).pad(10);
		buttonTable.row(); // Move to the next row
		buttonTable.add(quitBtn).pad(5);

		// INITIALIZE BUTTON LISTENERS
		pauseBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				sceneManager.changeScene(new PauseScene(sceneManager));
			}
		});

		quitBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				sceneManager.changeScene(new HomeScene(sceneManager, true));
			}
		});
	}

	public void create(){}

	public void render(SpriteBatch batch) {
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();

		// Draw Entities on Screen
		entityManager.drawEntites(batch);
		entityManager.moveEntities();
		entityManager.checkForEntitiesToBeRemoved();
		collisionManager.checkAllCollisions();
		collisionManager.checkForDetectorsToBeRemoved();
	}

}
