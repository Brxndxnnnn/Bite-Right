package io.github.some_example_name.lwjgl3.application_classes.scenes;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import io.github.some_example_name.lwjgl3.abstract_engine.audio.AudioPlayer;
import io.github.some_example_name.lwjgl3.abstract_engine.collision.CollisionDetector;
import io.github.some_example_name.lwjgl3.abstract_engine.collision.CollisionManager;
import io.github.some_example_name.lwjgl3.abstract_engine.entity.Entity;
import io.github.some_example_name.lwjgl3.abstract_engine.entity.EntityManager;
import io.github.some_example_name.lwjgl3.abstract_engine.entity.NonMovableEntity;
import io.github.some_example_name.lwjgl3.abstract_engine.io.IOManager;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.Scene;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneManager;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneTransitionType;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneType;
import io.github.some_example_name.lwjgl3.application_classes.collision_detectors.EnemyCollideBoundaryDetector;
import io.github.some_example_name.lwjgl3.application_classes.collision_detectors.EnemyCollideEnemyDetector;
import io.github.some_example_name.lwjgl3.application_classes.collision_detectors.EnemyCollideWallDetector;
import io.github.some_example_name.lwjgl3.application_classes.collision_detectors.PlayerCollideBoundaryDetector;
import io.github.some_example_name.lwjgl3.application_classes.collision_detectors.PlayerCollideEnemyDetector;
import io.github.some_example_name.lwjgl3.application_classes.collision_detectors.PlayerCollideWallDetector;
import io.github.some_example_name.lwjgl3.application_classes.entities.Character;
import io.github.some_example_name.lwjgl3.application_classes.entities.NonPlayableCharacter;
import io.github.some_example_name.lwjgl3.application_classes.entities.Wall;

public class PlayScene extends Scene {
    private final TextButton pauseBtn;
    private final TextButton resultsBtn;
    private final EntityManager entityManager;
    private final CollisionManager collisionManager;
    private final IOManager ioManager;
    private final int NUM_ENEMIES = 10;

    public PlayScene(SceneManager sceneManager) {
        super(sceneManager);
        
        setAudioFilePath("play.mp3");
        AudioPlayer.getInstance().playAudio(getAudioFilePath());

        // Get screen width and height
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        // INITIALIZE ENTITY MANAGER
        entityManager = new EntityManager();

        // Spawn the player in the center of the screen
        Vector2 playerPosition = new Vector2(screenWidth / 2f, screenHeight / 2f);
        Character player = new Character("Player", playerPosition, new Texture("player.png"), 2f);
        entityManager.addEntities(player);

        int numWalls = 6;
        float radius = screenWidth / 4f; // Distance from center
        Vector2 center = new Vector2(screenWidth / 2f, screenHeight / 2f);

        for (int i = 0; i < numWalls; i++) {
            float angle = (float) (i * (2 * Math.PI / numWalls)); // Evenly spaced angles
            float x = center.x + (float) Math.cos(angle) * radius;
            float y = center.y + (float) Math.sin(angle) * radius;

            NonMovableEntity wall = new Wall("Wall", new Vector2(x, y), new Texture("wall.png"));
            entityManager.addEntities(wall);
        }


        // Spawn enemies at the borders
        for (int i = 0; i < NUM_ENEMIES; i++) {
            Vector2 position = getRandomBorderPosition(screenWidth, screenHeight, playerPosition, 50);
            Entity enemy = new NonPlayableCharacter("Enemy", position, new Texture("demon.png"));
            entityManager.addEntities(enemy);
        }

		collisionManager = new CollisionManager();

		// Iterate through entity list to set up collision detectors
		for (int i = 0; i < entityManager.getEntitiesSize(); i++) {
			Entity entityA = entityManager.getEntity(i);

			// Handles collision between Character -> (NPC & Wall)
			if (entityA instanceof Character) {
                CollisionDetector playerCollideBoundaryDetector = new PlayerCollideBoundaryDetector((Character) entityA);
    	        collisionManager.addDetectors(playerCollideBoundaryDetector);
				for (int x = 0; x < entityManager.getEntitiesSize(); x++) {
                    Entity entityB = entityManager.getEntity(x);
					if (entityB instanceof NonPlayableCharacter) {
						// Add player-enemy collision detector
						CollisionDetector playerCollideEnemyDetector = new PlayerCollideEnemyDetector((Character) entityA, (NonPlayableCharacter) entityB);
						collisionManager.addDetectors(playerCollideEnemyDetector);
					}

					if (entityB instanceof Wall){
						CollisionDetector playerCollideWallDetector = new PlayerCollideWallDetector((Character) entityA, (Wall) entityB);
						collisionManager.addDetectors(playerCollideWallDetector);
					}
				}
			}

			// Handles collision between NPC -> (NPC & Wall)
			if (entityA instanceof NonPlayableCharacter) {
                CollisionDetector enemyCollideBoundaryDetector = new EnemyCollideBoundaryDetector((NonPlayableCharacter) entityA);
    	        collisionManager.addDetectors(enemyCollideBoundaryDetector);
				for (int y = 0; y < entityManager.getEntitiesSize(); y++) {
                    Entity entityB = entityManager.getEntity(y);
					if (entityB instanceof NonPlayableCharacter && entityA != entityB) {
						// Add enemy-enemy collision detector
						CollisionDetector enemyCollideEnemyDetector = new EnemyCollideEnemyDetector((NonPlayableCharacter) entityA, (NonPlayableCharacter) entityB);
						collisionManager.addDetectors(enemyCollideEnemyDetector);
					}

					if (entityB instanceof Wall){
						CollisionDetector enemyCollideWallDetector = new EnemyCollideWallDetector((NonPlayableCharacter) entityA, (Wall) entityB);
						collisionManager.addDetectors(enemyCollideWallDetector);
					}
				}
			}
		}

		

        // INITIALIZE BUTTONS
        pauseBtn = new TextButton("Pause", skin);
        pauseBtn.getLabel().setFontScale(0.5f);
        pauseBtn.getLabel().setColor(Color.SKY);
        pauseBtn.setColor(Color.ROYAL);
        
        resultsBtn = new TextButton("Results", skin);
        resultsBtn.getLabel().setFontScale(0.5f);
        resultsBtn.getLabel().setColor(Color.SKY);
        resultsBtn.setColor(Color.ROYAL);
		
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
        buttonTable.add(pauseBtn).pad(10).size(pauseBtn.getWidth() * 0.6f, pauseBtn.getHeight() * 0.6f);
        // ADD BUTTONS TO THE BUTTON TABLE
        buttonTable.add(resultsBtn).pad(10).size(resultsBtn.getWidth() * 0.6f, resultsBtn.getHeight() * 0.6f).expandX().right();
        
        // INITIALIZE BUTTON LISTENERS
        pauseBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sceneManager.changeScene(SceneType.PAUSE, SceneTransitionType.PAUSE);
            }
        });
        
        resultsBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sceneManager.changeScene(SceneType.RESULTS, SceneTransitionType.NORMAL);
            }
        });

        // Set the keybinds required for this PlayScene
        ioManager = new IOManager();

        ioManager.addIOBind(ioManager.getKeyboard(), Keys.W, () -> player.moveUserUp(), false);
        ioManager.addIOBind(ioManager.getKeyboard(), Keys.A, () -> player.moveUserLeft(), false);
        ioManager.addIOBind(ioManager.getKeyboard(), Keys.S, () -> player.moveUserDown(), false);
        ioManager.addIOBind(ioManager.getKeyboard(), Keys.D, () -> player.moveUserRight(), false);
        ioManager.addIOBind(ioManager.getKeyboard(), Keys.ESCAPE, () -> sceneManager.changeScene(SceneType.PAUSE, SceneTransitionType.PAUSE), true);
    }

    @Override
    public void render(SpriteBatch batch) {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();

        // Draw Entities on Screen
        entityManager.drawEntites(batch);
        entityManager.moveEntities();
        entityManager.checkForEntitiesToBeRemoved();
        collisionManager.checkAllCollisions();
        collisionManager.checkForDetectorsToBeRemoved();

        ioManager.handleInput();
    }

    private Vector2 getRandomBorderPosition(int screenWidth, int screenHeight, Vector2 playerPosition, float minDistance) {
        Random random = new Random();
        Vector2 position = new Vector2();

        int side = random.nextInt(4); // 0 = top, 1 = bottom, 2 = left, 3 = right

        switch (side) {
            case 0: // Top
                position.set(random.nextFloat() * screenWidth, screenHeight - 10);
                break;
            case 1: // Bottom
                position.set(random.nextFloat() * screenWidth, 10);
                break;
            case 2: // Left
                position.set(10, random.nextFloat() * screenHeight);
                break;
            case 3: // Right
                position.set(screenWidth - 10, random.nextFloat() * screenHeight);
                break;
        }

        // Ensure the enemy is at least `minDistance` away from the player
        while (position.dst(playerPosition) < minDistance) {
            position = getRandomBorderPosition(screenWidth, screenHeight, playerPosition, minDistance);
        }

        return position;
    }
}
       
