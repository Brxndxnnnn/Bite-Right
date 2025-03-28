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
import io.github.some_example_name.lwjgl3.abstract_engine.movement.Direction;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.Scene;
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
import io.github.some_example_name.lwjgl3.application_classes.utilities.FoodDictionary;
import io.github.some_example_name.lwjgl3.application_classes.utilities.FoodGenerator;
import io.github.some_example_name.lwjgl3.application_classes.utilities.GameDifficulty;
import io.github.some_example_name.lwjgl3.application_classes.utilities.GameDifficulty.Difficulty;
import io.github.some_example_name.lwjgl3.application_classes.utilities.MazeGenerator;
import io.github.some_example_name.lwjgl3.application_classes.utilities.NutritionistGenerator;

/**
 * Main gameplay scene managing game entities, interactions, and state.
 */
public class PlayScene implements Scene, DialogEventListener {
    // Core game managers
    private EntityManager entityManager;
    private CollisionManager collisionManager;
    private IOManager ioManager;
    private MazeGenerator mazeGenerator;
    private FoodGenerator foodGenerator;
    private NutritionistGenerator nutritionistGenerator;
    private GameSceneManager sceneManager;
    private DialogPopUpManager dialogPopUpManager;
    private FoodDictionary foodDictionary;

    // Game state variables
    private Player player;
    private float speed;
    private int targetPoint;
    private boolean showPopup;

    // UI components
    private Label healthLabel;
    private Label foodCountLabel;
    private Label pointsLabel;
    private Stage stage;
    private Stage overlayStage;
    private Skin skin;

    // Resources
    private Texture playBackground;
    private Texture nutritionistTexture;
    private Texture floorTexture;
    private SpriteBatch batch;

    /**
     * Constructs the play scene with all necessary game components.
     * 
     * @param sceneManager Manager for scene transitions
     */
    public PlayScene(GameSceneManager sceneManager) {
        // Initialize core components
        initializeSceneComponents(sceneManager);
        
        // Adjust game elements based on selected difficulty
        adjustGameElementsByDifficulty();
        
        // Setup game world
        setupGameWorld();
        
        // Configure input and audio
        configureInputAndAudio();
    }

    /**
     * Initialize basic scene components.
     */
    private void initializeSceneComponents(GameSceneManager sceneManager) {
        this.sceneManager = sceneManager;
        stage = new Stage();
        overlayStage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("pixthulhu/skin/pixthulhu-ui.json"));
        batch = new SpriteBatch();

        // Initialize utility components
        foodDictionary = new FoodDictionary();
        ioManager = new IOManager();
        entityManager = new EntityManager();
        collisionManager = new CollisionManager();
        dialogPopUpManager = new DialogPopUpManager(overlayStage, skin);
        dialogPopUpManager.setDialogEventListener(this);
    }

    /**
     * Adjust game elements based on selected difficulty.
     */
    private void adjustGameElementsByDifficulty() {
        Difficulty difficulty = GameDifficulty.getInstance().getDifficulty();
        
        switch (difficulty) {
            case EASY:
                speed = 100f;
                targetPoint = 200;
                playBackground = new Texture(Gdx.files.internal("background_images/easyplay.jpg"));
                break;
            case NORMAL:
                speed = 150f;
                targetPoint = 300;
                playBackground = new Texture(Gdx.files.internal("background_images/easyplay.jpg"));
                break;
            case HARD:
                speed = 300f;
                targetPoint = 400;
                playBackground = new Texture(Gdx.files.internal("background_images/hardPlay.jpg"));
                break;
        }

        // Set background image
        Image backgroundImage = new Image(new TextureRegionDrawable(new TextureRegion(playBackground)));
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        backgroundImage.setPosition(0, 0);
        stage.addActor(backgroundImage);
    }

    /**
     * Setup the game world with maze, entities, and initial configurations.
     */
    private void setupGameWorld() {
        // Calculate cell size and maze dimensions
        float cellSize = calculateCellSize();
        
        // Initialize generators
        mazeGenerator = new MazeGenerator(cellSize);
        foodGenerator = new FoodGenerator(cellSize, mazeGenerator, foodDictionary);
        nutritionistGenerator = new NutritionistGenerator(cellSize, mazeGenerator, foodDictionary);

        // Load textures
        Texture wallTexture = new Texture("entities_images/wall.png");
        floorTexture = new Texture("entities_images/floor.png");
        Texture enemyTexture = new Texture("entities_images/demon.png");
        Texture playerTexture = new Texture("entities_images/player.png");
        nutritionistTexture = new Texture("entities_images/nutritionist.png");

        // Generate game world components
        generateFloorAndMaze(wallTexture);
        createPlayer(playerTexture, cellSize);
        createEnemies(enemyTexture, cellSize);
        createInitialFood();

        // Setup collision detectors and labels
        setupCollisionDetectors();
        setupTitleLabels();
    }

    /**
     * Calculate cell size based on screen dimensions.
     */
    private float calculateCellSize() {
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        float cellSize = Math.min(screenWidth / 15f, screenHeight / 15f);
        return cellSize * 0.8f;
    }

    /**
     * Generate floor and maze components.
     */
    private void generateFloorAndMaze(Texture wallTexture) {
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        // Generate floor
        mazeGenerator.generateFloor(floorTexture, screenWidth, screenHeight);
        for (int i = 0; i < mazeGenerator.getFloorListSize(); i++) {
            entityManager.addEntity(mazeGenerator.getFloor(i));
        }

        // Generate maze
        mazeGenerator.generateMaze(wallTexture, screenWidth, screenHeight);
        for (int i = 0; i < mazeGenerator.getWallListSize(); i++) {
            entityManager.addEntity(mazeGenerator.getWall(i));
        }
    }

    /**
     * Create player entity.
     */
    private void createPlayer(Texture playerTexture, float cellSize) {
        Vector2 playerStartPos = mazeGenerator.gridToScreenTopLeft(1, 1);
        player = new Player("Player", playerStartPos, playerTexture, 2f);

        player.setSize(cellSize * 0.95f, cellSize * 0.95f);
        float offset = cellSize * 0.05f;
        player.setPosition(new Vector2(
            playerStartPos.x + offset,
            playerStartPos.y + offset
        ));

        entityManager.addEntity(player);
    }

    /**
     * Create enemy entities.
     */
    private void createEnemies(Texture enemyTexture, float cellSize) {
        Player playerEntity = findPlayerEntity();
        float offset = cellSize * 0.05f;

        // Ghost 1 - top right corner
        Vector2 ghost1Pos = mazeGenerator.gridToScreenTopLeft(MazeGenerator.getGridWidth() - 2, MazeGenerator.getGridHeight() - 2);
        Enemy ghost1 = createEnemy("Ghost1", ghost1Pos, enemyTexture, cellSize, offset, playerEntity);
        entityManager.addEntity(ghost1);

        // Ghost 2 - top left corner
        Vector2 ghost2Pos = mazeGenerator.gridToScreenTopLeft(1, MazeGenerator.getGridHeight() - 2);
        Enemy ghost2 = createEnemy("Ghost2", ghost2Pos, enemyTexture, cellSize, offset, playerEntity);
        entityManager.addEntity(ghost2);

        // Ghost 3 - bottom right corner
        Vector2 ghost3Pos = mazeGenerator.gridToScreenTopLeft(MazeGenerator.getGridWidth() - 2, 1);
        Enemy ghost3 = createEnemy("Ghost3", ghost3Pos, enemyTexture, cellSize, offset, playerEntity);
        entityManager.addEntity(ghost3);
    }

    /**
     * Create a single enemy entity.
     */
    private Enemy createEnemy(String name, Vector2 position, Texture texture, float cellSize, float offset, Player playerEntity) {
        Enemy enemy = new Enemy(name, position, texture, speed);
        enemy.setSize(cellSize * 0.8f, cellSize * 0.8f);
        enemy.setPosition(new Vector2(position.x + offset, position.y + offset));
        
        if (playerEntity != null) {
            enemy.setTargetEntity(playerEntity);
        }
        
        return enemy;
    }

    /**
     * Find the player entity in the entity manager.
     */
    private Player findPlayerEntity() {
        for (int i = 0; i < entityManager.getEntitiesSize(); i++) {
            Entity entity = entityManager.getEntity(i);
            if (entity instanceof Player) {
                return (Player) entity;
            }
        }
        return null;
    }

    /**
     * Create initial food items.
     */
    private void createInitialFood() {
        List<Food> initialFood = foodGenerator.generateFood(5);
        for (Food food : initialFood) {
            entityManager.addEntity(food);
        }
    }

    /**
     * Configure input bindings and audio.
     */
    private void configureInputAndAudio() {
        // Player movement bindings
        ioManager.addIOBind(ioManager.getKeyboard(), Keys.W, () -> player.move(Direction.UP), false);
        ioManager.addIOBind(ioManager.getKeyboard(), Keys.A, () -> player.move(Direction.LEFT), false);
        ioManager.addIOBind(ioManager.getKeyboard(), Keys.S, () -> player.move(Direction.DOWN), false);
        ioManager.addIOBind(ioManager.getKeyboard(), Keys.D, () -> player.move(Direction.RIGHT), false);
        
        // Pause menu binding
        ioManager.addIOBind(
            ioManager.getKeyboard(), 
            Keys.ESCAPE, 
            () -> sceneManager.changeScene(SceneType.PAUSE, SceneTransitionType.PAUSE), 
            true
        );

        // Set background music
        AudioPlayer.getInstance().setSceneBGM("background_music/play.mp3");
    }

    /**
     * Setup collision detectors for game entities.
     */
    private void setupCollisionDetectors() {
        collisionManager.clearDetectors();

        for (int i = 0; i < entityManager.getEntitiesSize(); i++) {
            Entity entityA = entityManager.getEntity(i);

            if (entityA instanceof Player) {
                setupPlayerCollisionDetectors((Player) entityA);
            } else if (entityA instanceof Enemy) {
                setupEnemyCollisionDetectors((Enemy) entityA);
            } else if (entityA instanceof Nutritionist) {
                setupNutritionistCollisionDetectors((Nutritionist) entityA);
            }
        }
    }

    /**
     * Setup collision detectors for player.
     */
    private void setupPlayerCollisionDetectors(Player player) {
        for (int x = 0; x < entityManager.getEntitiesSize(); x++) {
            Entity entityB = entityManager.getEntity(x);

            if (entityB instanceof Enemy) {
                CollisionDetector detector = new PlayerCollideEnemyDetector(player, (Enemy) entityB);
                collisionManager.addDetector(detector);
            }
            if (entityB instanceof Wall) {
                CollisionDetector detector = new PlayerCollideWallDetector(player, (Wall) entityB);
                collisionManager.addDetector(detector);
            }
            if (entityB instanceof Food) {
                CollisionDetector detector = new PlayerCollideFoodDetector(player, (Food) entityB, dialogPopUpManager);
                collisionManager.addDetector(detector);
            }
            if (entityB instanceof Nutritionist) {
                CollisionDetector detector = new PlayerCollideNutritionistDetector(player, (Nutritionist) entityB, dialogPopUpManager);
                collisionManager.addDetector(detector);
            }
        }
    }

    /**
     * Setup collision detectors for enemies.
     */
    private void setupEnemyCollisionDetectors(Enemy enemy) {
        for (int y = 0; y < entityManager.getEntitiesSize(); y++) {
            Entity entityB = entityManager.getEntity(y);

            if (entityB instanceof Enemy && entityB != enemy) {
                CollisionDetector detector = new EnemyCollideEnemyDetector(enemy, (Enemy) entityB);
                collisionManager.addDetector(detector);
            }
            if (entityB instanceof Wall) {
                CollisionDetector detector = new EnemyCollideWallDetector(enemy, (Wall) entityB);
                collisionManager.addDetector(detector);
            }
        }
    }

    /**
     * Setup collision detectors for nutritionist.
     */
    private void setupNutritionistCollisionDetectors(Nutritionist nutritionist) {
        for (int y = 0; y < entityManager.getEntitiesSize(); y++) {
            Entity entityB = entityManager.getEntity(y);
            
            if (entityB instanceof Wall) {
                CollisionDetector detector = new NutritionistCollideWallDetector(nutritionist, (Wall) entityB);
                collisionManager.addDetector(detector);
            }
        }
    }

    /**
     * Setup title labels for game UI.
     */
    private void setupTitleLabels() {
        Table titleTable = new Table();
        titleTable.setFillParent(true);
        titleTable.bottom();
        titleTable.padBottom(20);
        stage.addActor(titleTable);

        // Health label
        healthLabel = new Label("Health: " + player.getHealth() + "/" + player.getMaxHealth(), skin);
        healthLabel.setColor(Color.WHITE);
        titleTable.add(healthLabel).left().pad(10);

        // Points label
        pointsLabel = new Label("Points: 100/" + targetPoint, skin);
        pointsLabel.setColor(Color.GREEN);
        titleTable.add(pointsLabel).expandX().center().pad(10);

        // Food count label
        foodCountLabel = new Label("Food: 0/7", skin);
        foodCountLabel.setColor(Color.YELLOW);
        titleTable.add(foodCountLabel).right().pad(10);
    }

    /**
     * Update nutritionist spawning logic.
     */
    private void updateNutritionist() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        
        // Check if a nutritionist exists
        boolean nutritionistExists = checkNutritionistExists();
        
        // Spawn new nutritionist if needed
        if (!nutritionistExists && nutritionistGenerator.update(deltaTime, nutritionistExists)) {
            spawnNutritionist();
        }
    }

    /**
     * Check if a nutritionist currently exists in the game.
     */
    private boolean checkNutritionistExists() {
        for (int i = 0; i < entityManager.getEntitiesSize(); i++) {
            Entity entity = entityManager.getEntity(i);
            if (entity instanceof Nutritionist) {
                return true;
            }
        }
        return false;
    }

    /**
     * Spawn a new nutritionist.
     */
    private void spawnNutritionist() {
        Nutritionist newNutritionist = nutritionistGenerator.generateNutritionist(
            nutritionistTexture, 
            speed * 0.8f
        );
        
        if (newNutritionist != null) {
            entityManager.addEntity(newNutritionist);
            System.out.println("Nutritionist spawned");
        }
    }

    /**
     * Spawn new food items.
     */
    private void spawnFood() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        // Update food generator and add new food items
        List<Food> newFood = foodGenerator.update(deltaTime);
        for (Food food : newFood) {
            entityManager.addEntity(food);
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
        
        // Render dialog popups
        dialogPopUpManager.render(batch);
    }

    /**
     * Update the overall game state.
     */
    private void updateGameState() {
        // Spawn new entities
        spawnFood();
        updateNutritionist();
        
        // Update collision detectors
        setupCollisionDetectors();
        
        // Update entity movement and removal
        entityManager.moveEntities();
        entityManager.checkForEntitiesToBeRemoved();
        
        // Check collisions
        collisionManager.checkAllCollisions();
        collisionManager.checkForDetectorsToBeRemoved();
        
        // Handle user input
        ioManager.handleInput();
    }

    /**
     * Draw game elements.
     */
    private void drawGame(SpriteBatch batch) {
        // Clear the screen
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // Update UI displays
        updateUILabels();
        
        // Draw UI
        stage.draw();
        
        // Draw entities
        entityManager.drawEntites(batch);
    }

    /**
     * Update UI label texts.
     */
    private void updateUILabels() {
        healthLabel.setText("Health: " + player.getHealth() + "/" + player.getMaxHealth());
        
        int currentFoodCount = foodGenerator.getFoodListSize();
        foodCountLabel.setText("Food: " + currentFoodCount + "/7");
        
        pointsLabel.setText("Points: " + player.getPoints() + "/" + targetPoint);
    }

    /**
     * Check if the game has ended.
     */
    private void checkGameEndCondition() {
        if (showPopup) return;
        
        if (player.getHealth() <= 0) {
            // Store results in the scene manager instead of static fields
            sceneManager.setGameResults(player.getPoints(), false);
            sceneManager.changeScene(SceneType.RESULTS, SceneTransitionType.NORMAL);
        } else if (player.getPoints() >= targetPoint) {
            sceneManager.setGameResults(player.getPoints(), true);
            sceneManager.changeScene(SceneType.RESULTS, SceneTransitionType.NORMAL);
        }
    }

    @Override
    public void onDialogOpened() {
        showPopup = true;
    }

    @Override
    public void onDialogClosed() {
        showPopup = false;
    }

    @Override
    public void dispose() {
        // Dispose of resources
        stage.dispose();
        skin.dispose();
        playBackground.dispose();
        nutritionistTexture.dispose();
        floorTexture.dispose();
        foodGenerator.dispose();
        batch.dispose();
    }
}