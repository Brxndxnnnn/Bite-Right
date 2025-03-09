package io.github.some_example_name.lwjgl3.application_classes.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import io.github.some_example_name.lwjgl3.abstract_engine.collision.CollisionDetector;
import io.github.some_example_name.lwjgl3.abstract_engine.collision.CollisionManager;
import io.github.some_example_name.lwjgl3.abstract_engine.entity.Entity;
import io.github.some_example_name.lwjgl3.abstract_engine.entity.EntityManager;
import io.github.some_example_name.lwjgl3.abstract_engine.io.IOManager;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.Scene;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneManager;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneTransitionType;
import io.github.some_example_name.lwjgl3.application_classes.collision_detectors.EnemyCollideEnemyDetector;
import io.github.some_example_name.lwjgl3.application_classes.collision_detectors.EnemyCollideWallDetector;
import io.github.some_example_name.lwjgl3.application_classes.collision_detectors.PlayerCollideEnemyDetector;
import io.github.some_example_name.lwjgl3.application_classes.collision_detectors.PlayerCollideFoodDetector;
import io.github.some_example_name.lwjgl3.application_classes.collision_detectors.PlayerCollideWallDetector;
import io.github.some_example_name.lwjgl3.application_classes.entities.Enemy;
import io.github.some_example_name.lwjgl3.application_classes.entities.Food;
import io.github.some_example_name.lwjgl3.application_classes.entities.Player;
import io.github.some_example_name.lwjgl3.application_classes.entities.Wall;
import io.github.some_example_name.lwjgl3.application_classes.utilities.FoodGenerator;
import io.github.some_example_name.lwjgl3.application_classes.utilities.MazeGenerator;

public class PlayScene extends Scene {
    private final EntityManager entityManager;
    private final CollisionManager collisionManager;
    private final IOManager ioManager;
    private final MazeGenerator mazeGenerator;
    private Player player;
    private Label healthLabel;

    public PlayScene(SceneManager sceneManager) {
        super(sceneManager);
        setAudioFilePath("play.mp3");

        // Get screen width and height
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        // INITIALIZE ENTITY MANAGER
        entityManager = new EntityManager();
        
        // Load wall texture
        Texture wallTexture = new Texture("entities_images/wall.png");
        
        // Create and generate maze
        mazeGenerator = new MazeGenerator(entityManager, screenWidth, screenHeight, wallTexture);
        mazeGenerator.generateMaze();

        // Get the cell size
        float cellSize = mazeGenerator.getCellSize();
        
        // Calculate player starting position (top-left of cell 1,1)
        Vector2 playerStartPos = mazeGenerator.gridToScreenTopLeft(1, 1);
        
        // Create player with appropriate size for the maze
        Texture playerTexture = new Texture("entities_images/player.png");
        player = new Player("Player", playerStartPos, playerTexture, 2f);
        
        // Set player size to fit within a cell (slightly smaller for movement clearance)
        player.setSize(cellSize * 0.9f, cellSize * 0.9f);
        
        // Center the player within the cell
        float offset = cellSize * 0.05f; // 5% offset for the 90% sized entity
        player.setPosition(new Vector2(
            playerStartPos.x + offset, 
            playerStartPos.y + offset
        ));
        
        entityManager.addEntities(player);

        // Add enemies at specific maze positions
        addEnemies();

        // Generate food in the maze
        Texture healthyFoodTexture = new Texture("entities_images/healthyfood.png");  // Replace with your actual healthy food texture
        Texture unhealthyFoodTexture = new Texture("entities_images/unhealthyfood.png"); // Replace with your actual unhealthy food texture
        
        FoodGenerator foodGenerator = new FoodGenerator(
            entityManager, mazeGenerator, healthyFoodTexture, unhealthyFoodTexture);
        
        // Generate 20 food items
        foodGenerator.generateFood(5);
        
        // Update collision detectors to include food
        collisionManager = new CollisionManager();
        setupCollisionDetectors();

        // INITIALIZE MAIN TABLE FOR THE TITLE
        Table titleTable = new Table();
        titleTable.setFillParent(true);
        titleTable.top(); // Align content to the top
        stage.addActor(titleTable);

        Label label = new Label("Pacman Game", skin);
        titleTable.add(label).center().padTop(20); // Center the title at the top with padding
        
        // Add health label
        healthLabel = new Label("Health: " + player.getHealth() + "/" + player.getMaxHealth(), skin);
        healthLabel.setColor(Color.WHITE);
        titleTable.row();
        titleTable.add(healthLabel).center().padTop(5);

        // Set the keybinds required for this PlayScene
        ioManager = new IOManager();

        ioManager.addIOBind(ioManager.getKeyboard(), Keys.W, () -> player.moveUserUp(), false);
        ioManager.addIOBind(ioManager.getKeyboard(), Keys.A, () -> player.moveUserLeft(), false);
        ioManager.addIOBind(ioManager.getKeyboard(), Keys.S, () -> player.moveUserDown(), false);
        ioManager.addIOBind(ioManager.getKeyboard(), Keys.D, () -> player.moveUserRight(), false);
        ioManager.addIOBind(ioManager.getKeyboard(), Keys.ESCAPE, () -> sceneManager.changeScene(new PauseScene(sceneManager), SceneTransitionType.PAUSE), true);
    }

    private void addEnemies() {
        float cellSize = mazeGenerator.getCellSize();
        Texture ghostTexture = new Texture("entities_images/demon.png");
        
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
        Enemy ghost1 = new Enemy("Ghost1", ghost1Pos, ghostTexture);
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
        Enemy ghost2 = new Enemy("Ghost2", ghost2Pos, ghostTexture);
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
        Enemy ghost3 = new Enemy("Ghost3", ghost3Pos, ghostTexture);
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
        // Iterate through entity list to set up collision detectors
        for (int i = 0; i < entityManager.getEntitiesSize(); i++) {
            Entity entityA = entityManager.getEntity(i);

            // Handles collision between Player -> (NPC & Wall & Food)
            if (entityA instanceof Player) {
                for (int x = 0; x < entityManager.getEntitiesSize(); x++) {
                    Entity entityB = entityManager.getEntity(x);
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
                        CollisionDetector playerCollideFoodDetector = new PlayerCollideFoodDetector((Player) entityA, (Food) entityB);
                        collisionManager.addDetectors(playerCollideFoodDetector);
                    }
                }
            }

            // Handles collision between NPC -> (NPC & Wall)
            if (entityA instanceof Enemy) {
                for (int y = 0; y < entityManager.getEntitiesSize(); y++) {
                    Entity entityB = entityManager.getEntity(y);
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
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // Update health display
        healthLabel.setText("Health: " + player.getHealth() + "/" + player.getMaxHealth());
        
        // Draw UI
        stage.draw();

        // Draw Entities on Screen
        // batch.begin();
        entityManager.drawEntites(batch);
        // batch.end();
        
        // Update entity movement
        entityManager.moveEntities();
        entityManager.checkForEntitiesToBeRemoved();
        
        // Check collisions
        collisionManager.checkAllCollisions();
        collisionManager.checkForDetectorsToBeRemoved();

        // Handle user input
        ioManager.handleInput();
        
        // Check for game over condition
        if (player.getHealth() <= 0) {
            // Game over logic
            System.out.println("Game Over! Player died.");
            // sceneManager.changeScene(new HomeScene(sceneManager), SceneTransitionType.NORMAL);
        }
    }
}