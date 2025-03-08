package io.github.some_example_name.lwjgl3.application_classes.scenes;

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

import io.github.some_example_name.lwjgl3.abstract_engine.collision.CollisionDetector;
import io.github.some_example_name.lwjgl3.abstract_engine.collision.CollisionManager;
import io.github.some_example_name.lwjgl3.abstract_engine.entity.Entity;
import io.github.some_example_name.lwjgl3.abstract_engine.entity.EntityManager;
import io.github.some_example_name.lwjgl3.abstract_engine.io.IOManager;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.Scene;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneManager;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneTransitionType;
import io.github.some_example_name.lwjgl3.application_classes.MazeGenerator;
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
    private final EntityManager entityManager;
    private final CollisionManager collisionManager;
    private final IOManager ioManager;
    private final MazeGenerator mazeGenerator;
    private final int NUM_ENEMIES = 3; // Reduced for Pacman-style gameplay

    public PlayScene(SceneManager sceneManager) {
        super(sceneManager);
        setAudioFilePath("play.mp3");

        // Get screen width and height
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        // INITIALIZE ENTITY MANAGER
        entityManager = new EntityManager();
        
        // Load wall texture
        Texture wallTexture = new Texture("wall.png");
        
        // Create and generate maze
        mazeGenerator = new MazeGenerator(entityManager, screenWidth, screenHeight, wallTexture);
        mazeGenerator.generateMaze();

        // Get the cell size
        float cellSize = mazeGenerator.getCellSize();
        
        // Calculate player starting position (top-left of cell 1,1)
        Vector2 playerStartPos = mazeGenerator.gridToScreenTopLeft(1, 1);
        
        // Create player with appropriate size for the maze
        Texture playerTexture = new Texture("player.png");
        Character player = new Character("Player", playerStartPos, playerTexture, 2f);
        
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

        collisionManager = new CollisionManager();
        setupCollisionDetectors();

        // INITIALIZE BUTTONS
        pauseBtn = new TextButton("Pause", skin);
        pauseBtn.getLabel().setFontScale(0.6f);
        pauseBtn.getLabel().setColor(Color.SKY);
        pauseBtn.setColor(Color.ROYAL);
        
        // INITIALIZE MAIN TABLE FOR THE TITLE
        Table titleTable = new Table();
        titleTable.setFillParent(true);
        titleTable.top(); // Align content to the top
        stage.addActor(titleTable);

        Label label = new Label("Pacman Game", skin);
        titleTable.add(label).center().padTop(20); // Center the title at the top with padding

        // INITIALIZE BUTTON TABLE
        Table buttonTable = new Table();
        buttonTable.setFillParent(true);
        buttonTable.top().left(); // Align to top-left
        stage.addActor(buttonTable);

        // ADD BUTTONS TO THE BUTTON TABLE
        buttonTable.add(pauseBtn).pad(10).size(pauseBtn.getWidth() * 0.75f, pauseBtn.getHeight() * 0.75f);

        // INITIALIZE BUTTON LISTENERS
        pauseBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sceneManager.changeScene(new PauseScene(sceneManager), SceneTransitionType.PAUSE);
            }
        });

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
        Texture ghostTexture = new Texture("demon.png");
        
        // Add ghosts at specific positions in the maze
        // Ghost 1 - top right corner (red ghost)
        Vector2 ghost1Pos = mazeGenerator.gridToScreenTopLeft(MazeGenerator.getGridWidth() - 2, MazeGenerator.getGridHeight() - 2);
        NonPlayableCharacter ghost1 = new NonPlayableCharacter("Ghost1", ghost1Pos, ghostTexture);
        // Set ghost size to fit within a cell (slightly smaller for movement clearance)
        ghost1.setSize(cellSize * 0.9f, cellSize * 0.9f);
        // Center the ghost within the cell
        float offset = cellSize * 0.05f; // 5% offset for the 90% sized entity
        ghost1.setPosition(new Vector2(ghost1Pos.x + offset, ghost1Pos.y + offset));
        entityManager.addEntities(ghost1);

        // Ghost 2 - top left corner (pink ghost)
        Vector2 ghost2Pos = mazeGenerator.gridToScreenTopLeft(1, MazeGenerator.getGridHeight() - 2);
        NonPlayableCharacter ghost2 = new NonPlayableCharacter("Ghost2", ghost2Pos, ghostTexture);
        // Set ghost size to fit within a cell
        ghost2.setSize(cellSize * 0.9f, cellSize * 0.9f);
        // Center the ghost within the cell
        ghost2.setPosition(new Vector2(ghost2Pos.x + offset, ghost2Pos.y + offset));
        entityManager.addEntities(ghost2);

        // Ghost 3 - bottom right corner (cyan ghost)
        Vector2 ghost3Pos = mazeGenerator.gridToScreenTopLeft(MazeGenerator.getGridWidth() - 2, 1);
        NonPlayableCharacter ghost3 = new NonPlayableCharacter("Ghost3", ghost3Pos, ghostTexture);
        // Set ghost size to fit within a cell
        ghost3.setSize(cellSize * 0.9f, cellSize * 0.9f);
        // Center the ghost within the cell
        ghost3.setPosition(new Vector2(ghost3Pos.x + offset, ghost3Pos.y + offset));
        entityManager.addEntities(ghost3);
    }

    private void setupCollisionDetectors() {
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
}