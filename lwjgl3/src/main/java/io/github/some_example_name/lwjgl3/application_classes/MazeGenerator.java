package io.github.some_example_name.lwjgl3.application_classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import io.github.some_example_name.lwjgl3.abstract_engine.entity.EntityManager;
import io.github.some_example_name.lwjgl3.application_classes.entities.Wall;

public class MazeGenerator {
    // Maze layout (1 = wall, 0 = path)
    private static final int[][] MAZE_LAYOUT = {
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1},
        {1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
        {1, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1, 0, 0, 0, 1},
        {1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1},
        {1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1},
        {1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1},
        {1, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1, 0, 0, 0, 1},
        {1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
        {1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    // Grid dimensions
    private static final int GRID_WIDTH = 15;
    private static final int GRID_HEIGHT = 15;

    private final EntityManager entityManager;
    private final float cellSize;
    private final Texture wallTexture;

    /**
     * Creates a new MazeGenerator
     * 
     * @param entityManager The entity manager to add wall entities to
     * @param screenWidth Screen width to calculate cell size
     * @param screenHeight Screen height to calculate cell size
     * @param wallTexture Texture to use for wall entities
     */
    public MazeGenerator(EntityManager entityManager, int screenWidth, int screenHeight, Texture wallTexture) {
        this.entityManager = entityManager;
        this.wallTexture = wallTexture;
        
        // Calculate cell size based on screen dimensions and maze size
        float maxDimension = Math.max(GRID_WIDTH, GRID_HEIGHT);
        this.cellSize = Math.min(screenWidth / GRID_WIDTH, screenHeight / GRID_HEIGHT);
        
        // Ensure textures are properly sized for the grid
        this.wallTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
    }

    /**
     * Generates the maze walls and adds them to the entity manager
     */
    public void generateMaze() {
        // Convert layout to wall entities
        for (int y = 0; y < GRID_HEIGHT; y++) {
            for (int x = 0; x < GRID_WIDTH; x++) {
                if (MAZE_LAYOUT[y][x] == 1) {
                    // Create a wall at this position
                    Wall wall = new Wall(
                        "Wall_" + x + "_" + y, 
                        new Vector2(x * cellSize, y * cellSize),
                        wallTexture
                    );
                    // Ensure wall texture is scaled to match cell size
                    wall.setSize(cellSize, cellSize);
                    entityManager.addEntities(wall);
                }
            }
        }
    }
    
    /**
     * Returns true if the position contains a wall in the maze layout
     * 
     * @param gridX X position in the grid
     * @param gridY Y position in the grid
     * @return True if position contains a wall
     */
    public static boolean isWall(int gridX, int gridY) {
        // Check bounds
        if (gridX < 0 || gridX >= GRID_WIDTH || gridY < 0 || gridY >= GRID_HEIGHT) {
            return true; // Consider out of bounds as walls
        }
        return MAZE_LAYOUT[gridY][gridX] == 1;
    }
    
    /**
     * Converts a screen position to a grid position
     * 
     * @param screenX X position on screen
     * @param screenY Y position on screen
     * @return Vector2 containing grid X and Y coordinates
     */
    public Vector2 screenToGrid(float screenX, float screenY) {
        int gridX = (int)(screenX / cellSize);
        int gridY = (int)(screenY / cellSize);
        return new Vector2(gridX, gridY);
    }
    
    /**
     * Converts a grid position to a screen position (center of the cell)
     * 
     * @param gridX X position in grid
     * @param gridY Y position in grid
     * @return Vector2 containing screen X and Y coordinates
     */
    public Vector2 gridToScreen(int gridX, int gridY) {
        float screenX = gridX * cellSize + cellSize / 2;
        float screenY = gridY * cellSize + cellSize / 2;
        return new Vector2(screenX, screenY);
    }
    
    /**
     * Converts a grid position to a screen position (top-left corner of the cell)
     * 
     * @param gridX X position in grid
     * @param gridY Y position in grid
     * @return Vector2 containing screen X and Y coordinates
     */
    public Vector2 gridToScreenTopLeft(int gridX, int gridY) {
        float screenX = gridX * cellSize;
        float screenY = gridY * cellSize;
        return new Vector2(screenX, screenY);
    }
    
    /**
     * Gets the cell size used for the maze
     * @return Cell size in pixels
     */
    public float getCellSize() {
        return cellSize;
    }
    
    /**
     * Gets the grid width of the maze
     * @return Grid width
     */
    public static int getGridWidth() {
        return GRID_WIDTH;
    }
    
    /**
     * Gets the grid height of the maze
     * @return Grid height
     */
    public static int getGridHeight() {
        return GRID_HEIGHT;
    }
}