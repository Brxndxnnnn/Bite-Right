package io.github.some_example_name.lwjgl3.application_classes.utilities;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import io.github.some_example_name.lwjgl3.application_classes.entities.Floor;
import io.github.some_example_name.lwjgl3.application_classes.entities.Wall;

public class MazeGenerator {
    private final List<Wall> wallList;
    private final List<Floor> floorList;

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

    private final float cellSize;

    private float startX;
    private float startY;

    // Constructor
    public MazeGenerator(float cellSize) {
        wallList = new ArrayList<>();
        floorList = new ArrayList<>();

        // Calculate cell size based on screen dimensions and maze size
        this.cellSize = cellSize;
    }

    /**
     * Generates the maze walls and adds them to the entity manager
     *
     * @param wallTexture Texture of the wall entity
     * @param screenWidth Width of the screen
     * @param screenHeight Height of the screen
     */
    public void generateMaze(Texture wallTexture, int screenWidth, int screenHeight) {
        // Ensure textures are properly sized for the grid
        wallTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        // Calculate the total width and height of the maze
        float mazeWidth = GRID_WIDTH * cellSize;
        float mazeHeight = GRID_HEIGHT * cellSize;

        // Calculate the position to center the maze on the screen
        startX = (screenWidth - mazeWidth) / 2f; // Offset to center the maze horizontally
        startY = (screenHeight - mazeHeight) / 2f; // Offset to center the maze vertically
        
        // Convert layout to wall entities
        for (int y = 0; y < GRID_HEIGHT; y++) {
            for (int x = 0; x < GRID_WIDTH; x++) {
                if (MAZE_LAYOUT[y][x] == 1) {
                    // Create a wall at this position
                    Wall wall = new Wall(
                        "Wall_" + x + "_" + y,
                        new Vector2(startX + x * cellSize, startY + y * cellSize),
                        wallTexture
                    );
                    // Ensure wall texture is scaled to match cell size
                    wall.setSize(cellSize, cellSize);
                    wallList.add(wall);
                }
            }
        }
    }
    
    /**
     * Generates the maze floor tiles and adds them to the floor list
     *
     * @param floorTexture Texture of the floor entity
     * @param screenWidth Width of the screen
     * @param screenHeight Height of the screen
     */
    public void generateFloor(Texture floorTexture, int screenWidth, int screenHeight) {
        // Ensure textures are properly sized for the grid
        floorTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        // Calculate the total width and height of the maze
        float mazeWidth = GRID_WIDTH * cellSize;
        float mazeHeight = GRID_HEIGHT * cellSize;

        // Calculate the position to center the maze on the screen
        startX = (screenWidth - mazeWidth) / 2f; // Offset to center the maze horizontally
        startY = (screenHeight - mazeHeight) / 2f; // Offset to center the maze vertically
        
        // Convert layout to floor entities
        for (int y = 0; y < GRID_HEIGHT; y++) {
            for (int x = 0; x < GRID_WIDTH; x++) {
                if (MAZE_LAYOUT[y][x] == 0) {
                    // Create a floor at this position
                    Floor floor = new Floor(
                        "Floor_" + x + "_" + y,
                        new Vector2(startX + x * cellSize, startY + y * cellSize),
                        floorTexture
                    );
                    // Ensure floor texture is scaled to match cell size
                    floor.setSize(cellSize, cellSize);
                    floorList.add(floor);
                }
            }
        }
    }

    /**
     * Returns a wall entity by index
     *
     * @param index Index position in list
     * @return Wall entity at index
     */
    public Wall getWall(int index){
        return wallList.get(index);
    }

    /**
     * Returns the size of the wall list
     * 
     * @return Int size of current wall list
     */
    public int getWallListSize(){
        return wallList.size();
    }
    
    /**
     * Gets a floor entity at the specified index
     *
     * @param index Index position in list
     * @return Floor entity at index
     */
    public Floor getFloor(int index) {
        return floorList.get(index);
    }

    /**
     * Gets the size of the floor list
     * 
     * @return Int size of current floor list
     */
    public int getFloorListSize() {
        return floorList.size();
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
        float screenX = startX + gridX * cellSize;
        float screenY = startY + gridY * cellSize;
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
        float screenX = startX + gridX * cellSize;
        float screenY = startY + gridY * cellSize;
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