package io.github.some_example_name.lwjgl3.application_classes.scenes;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import io.github.some_example_name.lwjgl3.abstract_engine.audio.AudioPlayer;
import io.github.some_example_name.lwjgl3.abstract_engine.collision.CollisionDetector;
import io.github.some_example_name.lwjgl3.abstract_engine.collision.CollisionManager;
import io.github.some_example_name.lwjgl3.abstract_engine.entity.Entity;
import io.github.some_example_name.lwjgl3.abstract_engine.entity.EntityManager;
import io.github.some_example_name.lwjgl3.abstract_engine.io.IOManager;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.Scene;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneManager;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneTransitionType;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneType;
import io.github.some_example_name.lwjgl3.application_classes.collision_detectors.EnemyCollideEnemyDetector;
import io.github.some_example_name.lwjgl3.application_classes.collision_detectors.EnemyCollideWallDetector;
import io.github.some_example_name.lwjgl3.application_classes.collision_detectors.PlayerCollideEnemyDetector;
import io.github.some_example_name.lwjgl3.application_classes.collision_detectors.PlayerCollideFoodDetector;
import io.github.some_example_name.lwjgl3.application_classes.collision_detectors.PlayerCollideWallDetector;
import io.github.some_example_name.lwjgl3.application_classes.entities.Enemy;
import io.github.some_example_name.lwjgl3.application_classes.entities.Food;
import io.github.some_example_name.lwjgl3.application_classes.entities.Player;
import io.github.some_example_name.lwjgl3.application_classes.entities.Wall;
import io.github.some_example_name.lwjgl3.application_classes.utilities.DialogEventListener;
import io.github.some_example_name.lwjgl3.application_classes.utilities.DialogPopUpManager;
import io.github.some_example_name.lwjgl3.application_classes.utilities.FoodGenerator;
import io.github.some_example_name.lwjgl3.application_classes.utilities.MazeGenerator;

public class PlayScene extends Scene implements DialogEventListener {
    private final EntityManager entityManager;
    private final CollisionManager collisionManager;
    private final IOManager ioManager;
    private final DialogPopUpManager dialogManager;
    private final MazeGenerator mazeGenerator;
    private final FoodGenerator foodGenerator;
    private Player player;
    private Label healthLabel;
    private Label foodCountLabel;
    private boolean isPaused;

    public PlayScene(SceneManager sceneManager) {
        super(sceneManager);

        // Get screen width and height
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        float cellSize = Math.min(screenWidth / 15, screenHeight / 15);

        // Loading all required Textures
        Texture wallTexture = new Texture("entities_images/wall.png"); // Load Wall Texture
        Texture enemyTexture = new Texture("entities_images/demon.png"); // Load Enemy Texture
        Texture playerTexture = new Texture("entities_images/player.png"); // Load Player Texture
        Texture healthyFoodTexture = new Texture("entities_images/healthyfood.png"); // Load Healthy Food Texture
        Texture unhealthyFoodTexture = new Texture("entities_images/unhealthyfood.png"); // Load Unhealthy Food Texture

        // INITIALIZE REQUIRED MANAGERS
        ioManager = new IOManager();
        entityManager = new EntityManager();
        collisionManager = new CollisionManager();
        mazeGenerator = new MazeGenerator(cellSize);
        foodGenerator = new FoodGenerator(cellSize);
        dialogManager = new DialogPopUpManager(stage, skin);
        
        dialogManager.setDialogEventListener(this); // Register PlayScene as a listener
        
        // Create and generate maze
        createAndAddMaze(wallTexture);

        // Create and Add Player
        createAndAddPlayer(playerTexture);

        // Create and Add Enemies
        createAndAddEnemies(enemyTexture);

        // Generate and add initial food items
        createAndAddFood(healthyFoodTexture, unhealthyFoodTexture);

        // Initialize collision manager and setup collision detectors
        setupCollisionDetectors();

        // Set the labels such as Health etc
        setTitleLabels();

        // Set the keybinds required for this PlayScene
        ioManager.addIOBind(ioManager.getKeyboard(), Keys.W, () -> player.moveUserUp(), false);
        ioManager.addIOBind(ioManager.getKeyboard(), Keys.A, () -> player.moveUserLeft(), false);
        ioManager.addIOBind(ioManager.getKeyboard(), Keys.S, () -> player.moveUserDown(), false);
        ioManager.addIOBind(ioManager.getKeyboard(), Keys.D, () -> player.moveUserRight(), false);
        ioManager.addIOBind(ioManager.getKeyboard(), Keys.ESCAPE, () -> sceneManager.changeScene(SceneType.PAUSE, SceneTransitionType.PAUSE), true);

        AudioPlayer.getInstance().setSceneBGM("play.mp3");

    }

    private void createAndAddMaze(Texture wallTexture) {
        mazeGenerator.generateMaze(wallTexture);

        for (int i = 0; i < mazeGenerator.getWallListSize(); i++){
            entityManager.addEntities(mazeGenerator.getWall(i));
        }
    }

    private void createAndAddFood(Texture healthyFoodTexture, Texture unhealthyFoodTexture) {
        foodGenerator.setTextures(healthyFoodTexture, unhealthyFoodTexture);
        // Generate initial 5 food items
        List<Food> initialFood = foodGenerator.generateFood(5);

        // Add food items to entity manager
        for (Food food : initialFood) {
            entityManager.addEntities(food);
        }
    }

    private void createAndAddPlayer(Texture playerTexture) {
        // Get the cell size
        float cellSize = mazeGenerator.getCellSize();

        // Create player with appropriate size for the maze
        Vector2 playerStartPos = mazeGenerator.gridToScreenTopLeft(1, 1); // Calculate player starting position (top-left of cell 1,1)
        player = new Player("Player", playerStartPos, playerTexture, 2f);
        player.setSize(cellSize * 0.9f, cellSize * 0.9f); // // Set player size to fit within a cell (slightly smaller for movement clearance)

        // Center the player within the cell
        float offset = cellSize * 0.05f; // 5% offset for the 90% sized entity
        player.setPosition(new Vector2(
            playerStartPos.x + offset,
            playerStartPos.y + offset
        ));

        entityManager.addEntities(player);
    }

    private void setTitleLabels() {
        // INITIALIZE MAIN TABLE FOR THE TITLE
        Table titleTable = new Table();
        titleTable.setFillParent(true);
        titleTable.top().right(); // Align content to the top right
        titleTable.padRight(10).padTop(10); // Add padding for better visibility
        stage.addActor(titleTable);

        // Add health label
        healthLabel = new Label("Health: " + player.getHealth() + "/" + player.getMaxHealth(), skin);
        healthLabel.setColor(Color.WHITE);
        titleTable.add(healthLabel).right().padTop(5);
        titleTable.row();

        // Add food count label
        foodCountLabel = new Label("Food: 0/7", skin);
        foodCountLabel.setColor(Color.YELLOW);
        titleTable.add(foodCountLabel).right().padTop(5);
    }

    private void createAndAddEnemies(Texture enemyTexture) {
        float cellSize = mazeGenerator.getCellSize();

        // Find player entity for the ghosts to target
        Player playerEntity = null;
        for (int i = 0; i < entityManager.getEntitiesSize(); i++) {
            Entity entity = entityManager.getEntity(i);
            if (entity instanceof Player) {
                playerEntity = (Player) entity;
                break;
            }
        }

        // Add ghosts at specific positions in the maze
        // Ghost 1 - top right corner (red ghost)
        Vector2 ghost1Pos = mazeGenerator.gridToScreenTopLeft(MazeGenerator.getGridWidth() - 2, MazeGenerator.getGridHeight() - 2);
        Enemy ghost1 = new Enemy("Ghost1", ghost1Pos, enemyTexture);
        // Set ghost size to fit within a cell (slightly smaller for movement clearance)
        ghost1.setSize(cellSize * 0.9f, cellSize * 0.9f);
        // Center the ghost within the cell
        float offset = cellSize * 0.05f; // 5% offset for the 90% sized entity
        ghost1.setPosition(new Vector2(ghost1Pos.x + offset, ghost1Pos.y + offset));
        // Set player as target
        if (playerEntity != null) {
            ghost1.setTargetEntity(playerEntity);
        }
        entityManager.addEntities(ghost1);

        // Ghost 2 - top left corner (pink ghost)
        Vector2 ghost2Pos = mazeGenerator.gridToScreenTopLeft(1, MazeGenerator.getGridHeight() - 2);
        Enemy ghost2 = new Enemy("Ghost2", ghost2Pos, enemyTexture);
        // Set ghost size to fit within a cell
        ghost2.setSize(cellSize * 0.9f, cellSize * 0.9f);
        // Center the ghost within the cell
        ghost2.setPosition(new Vector2(ghost2Pos.x + offset, ghost2Pos.y + offset));
        // Set player as target
        if (playerEntity != null) {
            ghost2.setTargetEntity(playerEntity);
        }
        entityManager.addEntities(ghost2);

        // Ghost 3 - bottom right corner (cyan ghost)
        Vector2 ghost3Pos = mazeGenerator.gridToScreenTopLeft(MazeGenerator.getGridWidth() - 2, 1);
        Enemy ghost3 = new Enemy("Ghost3", ghost3Pos, enemyTexture);
        // Set ghost size to fit within a cell
        ghost3.setSize(cellSize * 0.9f, cellSize * 0.9f);
        // Center the ghost within the cell
        ghost3.setPosition(new Vector2(ghost3Pos.x + offset, ghost3Pos.y + offset));
        // Set player as target
        if (playerEntity != null) {
            ghost3.setTargetEntity(playerEntity);
        }
        entityManager.addEntities(ghost3);
    }

    private void setupCollisionDetectors() {
        // Clear existing collision detectors
        collisionManager.clearDetectors();

        // Iterate through entity list to set up collision detectors
        for (int i = 0; i < entityManager.getEntitiesSize(); i++) {
            Entity entityA = entityManager.getEntity(i);

            // Skip entities that should be removed
            if (entityA.shouldBeRemoved()) continue;

            // Handles collision between Player -> (NPC & Wall & Food)
            if (entityA instanceof Player) {
                for (int x = 0; x < entityManager.getEntitiesSize(); x++) {
                    Entity entityB = entityManager.getEntity(x);

                    // Skip entities that should be removed
                    if (entityB.shouldBeRemoved()) continue;

                    if (entityB instanceof Enemy) {
                        // Add player-enemy collision detector
                        CollisionDetector playerCollideEnemyDetector = new PlayerCollideEnemyDetector((Player) entityA, (Enemy) entityB);
                        collisionManager.addDetectors(playerCollideEnemyDetector);
                    }

                    if (entityB instanceof Wall){
                        CollisionDetector playerCollideWallDetector = new PlayerCollideWallDetector((Player) entityA, (Wall) entityB);
                        collisionManager.addDetectors(playerCollideWallDetector);
                    }

                    if (entityB instanceof Food) {
                        CollisionDetector playerCollideFoodDetector = new PlayerCollideFoodDetector((Player) entityA, (Food) entityB, dialogManager);
                        collisionManager.addDetectors(playerCollideFoodDetector);
                    }
                }
            }

            // Handles collision between NPC -> (NPC & Wall)
            if (entityA instanceof Enemy) {
                for (int y = 0; y < entityManager.getEntitiesSize(); y++) {
                    Entity entityB = entityManager.getEntity(y);

                    // Skip entities that should be removed
                    if (entityB.shouldBeRemoved()) continue;

                    if (entityB instanceof Enemy && entityA != entityB) {
                        // Add enemy-enemy collision detector
                        CollisionDetector enemyCollideEnemyDetector = new EnemyCollideEnemyDetector((Enemy) entityA, (Enemy) entityB);
                        collisionManager.addDetectors(enemyCollideEnemyDetector);
                    }

					if (entityB instanceof Wall){
						                        CollisionDetector enemyCollideWallDetector = new EnemyCollideWallDetector((Enemy) entityA, (Wall) entityB);
						collisionManager.addDetectors(enemyCollideWallDetector);
					}
				}
			}
		}
    }

    @Override
    public void render(SpriteBatch batch) {
    	// show true when collided
    	if(isPaused == true) {
//    		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
    		dialogManager.render(batch);
//    		return;
    	}
    	
    	if (!isPaused) {
    		spawnFood();
        	setupCollisionDetectors();
			//Update entity movement
			entityManager.moveEntities();
			entityManager.checkForEntitiesToBeRemoved();
			
			// Check collisions
			collisionManager.checkAllCollisions();
			collisionManager.checkForDetectorsToBeRemoved();
			
			// Handle user input5
			ioManager.handleInput();
    		
    		
    		
    		
    		
    		
    	}
    	
//		spawnFood();
//
//        // Update collision detectors
//        // (needed since new food items might have been spawned)
//        setupCollisionDetectors();

        // Clear the screen
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update health and food count displays
        healthLabel.setText("Health: " + player.getHealth() + "/" + player.getMaxHealth());

        // Update food count label
        int currentFoodCount = foodGenerator.getFoodListSize();
        foodCountLabel.setText("Food: " + currentFoodCount + "/7");

  

            // Draw Entities on Screen
        entityManager.drawEntites(batch);
        
	    // Draw UI
	    stage.draw();
	    
	    

//        // Update entity movement
//        entityManager.moveEntities();
//        entityManager.checkForEntitiesToBeRemoved();
//
//        // Check collisions
//        collisionManager.checkAllCollisions();
//        collisionManager.checkForDetectorsToBeRemoved();
//
//        // Handle user input
//        ioManager.handleInput();

        // Check for game over condition
        if (player.getHealth() <= 0) {
            // Game over logic
            System.out.println("Game Over! Player died.");

            // Uncomment the following line when you have a GameOverScene ready
            // sceneManager.changeScene(new GameOverScene(sceneManager), SceneTransitionType.NORMAL);

            // Or transition back to the home scene
            // sceneManager.changeScene(new HomeScene(sceneManager), SceneTransitionType.NORMAL);www
        }
    	
        
    }

    private void spawnFood() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        // Update food generator and add any new food items
        List<Food> newFood = foodGenerator.update(deltaTime);
        for (Food food : newFood) {
            entityManager.addEntities(food);
        }
    }

	@Override
	public void onDialogOpened() {
		isPaused = true;
		
	}

	@Override
	public void onDialogClosed() {
		isPaused = false;
		
	}
}
