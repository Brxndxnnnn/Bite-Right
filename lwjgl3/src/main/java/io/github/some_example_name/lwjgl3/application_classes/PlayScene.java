package io.github.some_example_name.lwjgl3.application_classes;

import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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
import io.github.some_example_name.lwjgl3.abstract_engine.entity.MovableEntity;
import io.github.some_example_name.lwjgl3.abstract_engine.entity.NonMovableEntity;
import io.github.some_example_name.lwjgl3.abstract_engine.io.IOManager;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.PrimaryScene;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneManager;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneTransitionType;

public class PlayScene extends PrimaryScene {
    private TextButton pauseBtn;
    private EntityManager entityManager;
    private CollisionManager collisionManager;
    private IOManager ioManager;
    private final int NUM_ENEMIES = 10;

    public PlayScene(SceneManager sceneManager, Boolean isHome) {
        super(sceneManager, isHome);
        setAudioFilePath("music1.mp3");

        // Get screen width and height
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        // INITIALIZE ENTITY MANAGER
        entityManager = new EntityManager();

        // Spawn the player in the center of the screen
        Vector2 playerPosition = new Vector2(screenWidth / 2f, screenHeight / 2f);
        MovableEntity player = new Character("Player", playerPosition, new Texture("player.png"), 2f);
        entityManager.addEntities(player);

		// Spawn the walls
        Vector2 wallPosition = new Vector2(screenWidth / 4f, screenHeight / 4f);
        NonMovableEntity wall = new Wall("Wall", wallPosition, new Texture("player.png"));
        entityManager.addEntities(wall);

        // Spawn enemies at the borders
        for (int i = 0; i < NUM_ENEMIES; i++) {
            Vector2 position = getRandomBorderPosition(screenWidth, screenHeight, playerPosition, 50);
            Entity enemy = new NonPlayableCharacter("Enemy", position, new Texture("demon.png"));
            entityManager.addEntities(enemy);
        }

		collisionManager = new CollisionManager();

        // Get entity list from EntityManager
		List<Entity> entities = entityManager.getEntities(); // Assuming there's a getter for entityList

		// Iterate through entity list to set up collision detectors
		for (int i = 0; i < entities.size(); i++) {
			Entity entityA = entities.get(i);

			// Handles collision between Character -> (NPC & Wall)
			if (entityA instanceof Character) {
				for (Entity entityB : entities) {
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
				for (Entity entityB : entities) {
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

        // INITIALIZE BUTTON LISTENERS
        pauseBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sceneManager.changeScene(new PauseScene(sceneManager), SceneTransitionType.PAUSE);
            }
        });

        // Set the keybinds required for this PlayScene
        ioManager = new IOManager();

        ioManager.addIOBind(ioManager.getKeyboard(), Keys.W, () -> player.moveUp(), false);
        ioManager.addIOBind(ioManager.getKeyboard(), Keys.A, () -> player.moveLeft(), false);
        ioManager.addIOBind(ioManager.getKeyboard(), Keys.S, () -> player.moveDown(), false);
        ioManager.addIOBind(ioManager.getKeyboard(), Keys.D, () -> player.moveRight(), false);
        ioManager.addIOBind(ioManager.getKeyboard(), Keys.ESCAPE, () -> sceneManager.changeScene(new PauseScene(sceneManager), SceneTransitionType.PAUSE), true);
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
       
