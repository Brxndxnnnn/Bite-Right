package io.github.some_example_name.lwjgl3.application_classes.scenes;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

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
import io.github.some_example_name.lwjgl3.application_classes.collision_detectors.NutritionistCollideWallDetector;
import io.github.some_example_name.lwjgl3.application_classes.collision_detectors.PlayerCollideEnemyDetector;
import io.github.some_example_name.lwjgl3.application_classes.collision_detectors.PlayerCollideFoodDetector;
import io.github.some_example_name.lwjgl3.application_classes.collision_detectors.PlayerCollideNutritionistDetector;
import io.github.some_example_name.lwjgl3.application_classes.collision_detectors.PlayerCollideWallDetector;
import io.github.some_example_name.lwjgl3.application_classes.entities.Enemy;
import io.github.some_example_name.lwjgl3.application_classes.entities.Food;
import io.github.some_example_name.lwjgl3.application_classes.entities.Nutritionist;
import io.github.some_example_name.lwjgl3.application_classes.entities.Player;
import io.github.some_example_name.lwjgl3.application_classes.entities.Wall;
import io.github.some_example_name.lwjgl3.application_classes.utilities.DialogEventListener;
import io.github.some_example_name.lwjgl3.application_classes.utilities.DialogPopUpManager;
import io.github.some_example_name.lwjgl3.application_classes.utilities.FoodGenerator;
import io.github.some_example_name.lwjgl3.application_classes.utilities.GameDifficulty;
import io.github.some_example_name.lwjgl3.application_classes.utilities.GameDifficulty.Difficulty;
import io.github.some_example_name.lwjgl3.application_classes.utilities.MazeGenerator;
import io.github.some_example_name.lwjgl3.application_classes.utilities.NutritionistGenerator;

public class PlayScene implements Scene, DialogEventListener {
    private final EntityManager entityManager;
    private final CollisionManager collisionManager;
    private final IOManager ioManager;
    private final MazeGenerator mazeGenerator;
    private final FoodGenerator foodGenerator;
    private final NutritionistGenerator nutritionistGenerator;
    private final SceneManager sceneManager;
    private final DialogPopUpManager dialogPopUpManager;
    private Player player;
    private Label healthLabel;
    private Label foodCountLabel;
    private Stage stage;
    private Stage overlayStage;
    private Skin skin;
    private Label pointsLabel;
    private static int finalPoints;
    private static boolean gameWon;
    private float speed;
    private int targetPoint;
    private Texture playBackground;
    private SpriteBatch batch;
    private Nutritionist activeNutritionist;
    private Texture nutritionistTexture;
    private Texture floorTexture;
    private boolean showPopup;


    public PlayScene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        stage = new Stage();
        overlayStage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("pixthulhu/skin/pixthulhu-ui.json"));


        // Get screen width and height
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        float cellSize = Math.min(screenWidth / 15f, screenHeight / 15f);
        float scalefactor = 0.8f;
        cellSize *= scalefactor;

        // Calculate the total width and height of the maze (based on the number of grid cells)
        float mazeWidth = 15 * cellSize;
        float mazeHeight = 15 * cellSize;

        // Calculate the position to center the maze on the screen
        float startX = (screenWidth - mazeWidth) / 2f; // Offset to center the maze horizontally
        float startY = (screenHeight - mazeHeight) / 2f; // Offset to center the maze vertically

        // Loading all required Textures
        Texture wallTexture = new Texture("entities_images/wall.png"); // Load Wall Texture
        floorTexture = new Texture("entities_images/floor.png"); // Load Floor Texture
        Texture enemyTexture = new Texture("entities_images/demon.png"); // Load Enemy Texture
        Texture playerTexture = new Texture("entities_images/player.png"); // Load Player Texture
        Texture healthyFoodTexture = new Texture("entities_images/healthyfood.png"); // Load Healthy Food Texture
        Texture unhealthyFoodTexture = new Texture("entities_images/unhealthyfood.png"); // Load Unhealthy Food Texture
        nutritionistTexture = new Texture("entities_images/nutritionist.png"); // Load Nutritionist Texture

        // INITIALIZE REQUIRED MANAGERS
        ioManager = new IOManager();
        entityManager = new EntityManager();
        collisionManager = new CollisionManager();
        mazeGenerator = new MazeGenerator(cellSize);
        foodGenerator = new FoodGenerator(cellSize, mazeGenerator);
        nutritionistGenerator = new NutritionistGenerator(cellSize, mazeGenerator);
        dialogPopUpManager = new DialogPopUpManager(overlayStage, skin);
        dialogPopUpManager.setDialogEventListener(this); // Register PlayScene as a listener

        batch = new SpriteBatch();


        adjustElementsByDifficulty();

        // Create and generate floor first (so it appears behind other entities)
        createAndAddFloor(floorTexture);
        
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


    private void adjustElementsByDifficulty() {
        if (GameDifficulty.getInstance().getDifficulty() == Difficulty.EASY) {
            speed = 100f;
            targetPoint = 200;
            playBackground = new Texture(Gdx.files.internal("assets/easyplay.jpg")); // Load easy background
        }
        else if (GameDifficulty.getInstance().getDifficulty() == Difficulty.NORMAL) {
            speed = 150f;
            targetPoint = 300;
            playBackground = new Texture(Gdx.files.internal("assets/easyplay.jpg")); // Load easy background
        }
        else if (GameDifficulty.getInstance().getDifficulty() == Difficulty.HARD) {
            speed = 300f;
            targetPoint = 400;
            playBackground = new Texture(Gdx.files.internal("assets/hardPlay.jpg")); // Load hard background
        }

        Image backgroundImage = new Image(new TextureRegionDrawable(new TextureRegion(playBackground)));
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        backgroundImage.setPosition(0, 0);
        stage.addActor(backgroundImage);
    }

    private void createAndAddFloor(Texture floorTexture) {
        // Get the screen width and height
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        // Generate the floor with the calculated cell size and screen dimensions
        mazeGenerator.generateFloor(floorTexture, screenWidth, screenHeight);

        // Add generated floor tiles to the entity manager
        for (int i = 0; i < mazeGenerator.getFloorListSize(); i++) {
            // Add floor tiles first so they're rendered behind other entities
            entityManager.addEntities(mazeGenerator.getFloor(i));
        }
    }

    private void createAndAddMaze(Texture wallTexture) {
        // Get the screen width and height
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        // Generate the maze with the calculated cell size and screen dimensions
        mazeGenerator.generateMaze(wallTexture, screenWidth, screenHeight);

        // Add generated walls to the entity manager
        for (int i = 0; i < mazeGenerator.getWallListSize(); i++) {
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

        player.setSize(cellSize * 0.9f, cellSize * 0.9f); // Set player size to fit within a cell (slightly smaller for movement clearance)

        // Center the player within the cell
        float offset = cellSize * 0.05f; // 5% offset for the 90% sized entity

        player.setPosition(new Vector2(
            playerStartPos.x + offset,
            playerStartPos.y + offset
        ));

        entityManager.addEntities(player);
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
        Enemy ghost1 = new Enemy("Ghost1", ghost1Pos, enemyTexture, speed);
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
        Enemy ghost2 = new Enemy("Ghost2", ghost2Pos, enemyTexture, speed);
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
        Enemy ghost3 = new Enemy("Ghost3", ghost3Pos, enemyTexture, speed);
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

            // Handles collision between Player -> (NPC & Wall & Food & Nutritionist)
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
                        CollisionDetector playerCollideFoodDetector = new PlayerCollideFoodDetector((Player) entityA, (Food) entityB, dialogPopUpManager);
                        collisionManager.addDetectors(playerCollideFoodDetector);
                    }
                    
                    if (entityB instanceof Nutritionist) {
                        CollisionDetector playerCollideNutritionistDetector = new PlayerCollideNutritionistDetector((Player) entityA, (Nutritionist) entityB);
                        collisionManager.addDetectors(playerCollideNutritionistDetector);
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

                    if (entityB instanceof Wall) {
                        CollisionDetector enemyCollideWallDetector = new EnemyCollideWallDetector((Enemy) entityA, (Wall) entityB);
                        collisionManager.addDetectors(enemyCollideWallDetector);
                    }
                }
            }
            
            // Handles collision between Nutritionist -> Wall
            if (entityA instanceof Nutritionist) {
                for (int y = 0; y < entityManager.getEntitiesSize(); y++) {
                    Entity entityB = entityManager.getEntity(y);
                    
                    // Skip entities that should be removed
                    if (entityB.shouldBeRemoved()) continue;
                    
                    if (entityB instanceof Wall) {
                        CollisionDetector nutritionistCollideWallDetector = new NutritionistCollideWallDetector((Nutritionist) entityA, (Wall) entityB);
                        collisionManager.addDetectors(nutritionistCollideWallDetector);
                    }
                }
            }
        }
    }

    private void setTitleLabels() {
        // INITIALIZE MAIN TABLE FOR THE TITLE
        Table titleTable = new Table();
        titleTable.setFillParent(true);
        titleTable.bottom(); // Align content to the top right
        titleTable.padBottom(20);// Add padding for better visibility
        stage.addActor(titleTable);

        // Add health label
        healthLabel = new Label("Health: " + player.getHealth() + "/" + player.getMaxHealth(), skin);
        healthLabel.setColor(Color.WHITE);
        titleTable.add(healthLabel).left().pad(10);

        // Add points label
        pointsLabel = new Label("Points: 100/" + targetPoint, skin);
        pointsLabel.setColor(Color.GREEN);
        titleTable.add(pointsLabel).expandX().center().pad(10);

        // Add food count label
        foodCountLabel = new Label("Food: 0/7", skin);
        foodCountLabel.setColor(Color.YELLOW);
        titleTable.add(foodCountLabel).right().pad(10);
    }
    
    private void updateNutritionist() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        
        // Check if we have an active nutritionist
        boolean nutritionistExists = false;
        for (int i = 0; i < entityManager.getEntitiesSize(); i++) {
            Entity entity = entityManager.getEntity(i);
            if (entity instanceof Nutritionist && !entity.shouldBeRemoved()) {
                nutritionistExists = true;
                activeNutritionist = (Nutritionist) entity;
                break;
            }
        }
        
        // If no nutritionist exists, check if we should spawn one
        if (!nutritionistExists && nutritionistGenerator.update(deltaTime, nutritionistExists)) {
            // Generate a new nutritionist with 80% of enemy speed
            Nutritionist newNutritionist = nutritionistGenerator.generateNutritionist(nutritionistTexture, speed * 0.8f);
            if (newNutritionist != null) {
                entityManager.addEntities(newNutritionist);
                activeNutritionist = newNutritionist;
                System.out.println("Nutritionist spawned");
            }
        }
        
        // If nutritionist was removed, update the generator
        if (activeNutritionist != null && activeNutritionist.shouldBeRemoved()) {
            nutritionistGenerator.onNutritionistRemoved();
            activeNutritionist = null;
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
    public void render(SpriteBatch batch) {
        // Update game state if not paused
        if (!showPopup) {
            updateGameState();
        }
        
        // Draw everything
        drawGame(batch);
        
        // Check for game over condition
        checkGameEndCondition();
        
        // Render dialog popups at the end, on top of everything else
        dialogPopUpManager.render(batch);
    }

    public static int getfinalPoints() {
        return finalPoints;
    }

    public static boolean isGameWon() {
        return gameWon;
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        playBackground.dispose();
        nutritionistTexture.dispose(); // Make sure to dispose the nutritionist texture
        floorTexture.dispose(); // Make sure to dispose the floor texture
    }


    @Override
    public void onDialogOpened() {
        showPopup = true;
    }


    @Override
    public void onDialogClosed() {
        showPopup = false;
    }

    // Update the game state
private void updateGameState() {
    // Spawn new entities
    spawnFood();
    updateNutritionist();
    
    // Update collision detectors for new entities
    setupCollisionDetectors();
    
    // Update entity movement
    entityManager.moveEntities();
    entityManager.checkForEntitiesToBeRemoved();
    
    // Check for collisions
    collisionManager.checkAllCollisions();
    collisionManager.checkForDetectorsToBeRemoved();
    
    // Handle user input
    ioManager.handleInput();
}

// Draw the game elements
private void drawGame(SpriteBatch batch) {
    // Clear the screen
    Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
    
    // Update UI displays
    healthLabel.setText("Health: " + player.getHealth() + "/" + player.getMaxHealth());
    int currentFoodCount = foodGenerator.getFoodListSize();
    foodCountLabel.setText("Food: " + currentFoodCount + "/7");
    pointsLabel.setText("Points: " + player.getPoints() + "/" + targetPoint);
    
    // Draw UI
    stage.draw();
    
    // Draw entities
    entityManager.drawEntites(batch);
}

// Check if the game has ended (win or lose)
    private void checkGameEndCondition() {
        if (showPopup) return;
        
        if (player.getHealth() <= 0) {
            // Game over logic
            finalPoints = player.getPoints();
            gameWon = false;
            System.out.println("Game Over! Player died.");
            sceneManager.changeScene(SceneType.RESULTS, SceneTransitionType.NORMAL);
        } else if (player.getPoints() >= targetPoint) {
            // Game win logic
            finalPoints = player.getPoints();
            gameWon = true;
            System.out.println("Game Win! Player reached " + targetPoint + " points.");
            sceneManager.changeScene(SceneType.RESULTS, SceneTransitionType.NORMAL);
        }
    }
}