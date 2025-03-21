package io.github.some_example_name.lwjgl3.application_classes.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import io.github.some_example_name.lwjgl3.application_classes.entities.Food;
import io.github.some_example_name.lwjgl3.application_classes.entities.HealthyFood;
import io.github.some_example_name.lwjgl3.application_classes.entities.UnhealthyFood;

/**
 * Manager for dialog popups
 */
public class DialogPopUpManager {
    private final Stage popupStage;
    private final ShapeRenderer shapeRenderer;
    private final DialogPopUp dialogPopup;
    private boolean popupShown = false;
    
    /**
     * Constructor for popup manager
     * 
     * @param skin The skin to use for UI components
     */
    public DialogPopUpManager(Skin skin) {
        popupStage = new Stage();
        shapeRenderer = new ShapeRenderer();
        dialogPopup = new DialogPopUp(popupStage, skin);
    }
    
    /**
     * Shows a food popup when player collects food
     * 
     * @param food The food that was collected
     * @param closeCallback Callback to run when popup is closed
     */
    public void showFoodPopup(Food food, Runnable closeCallback) {
        // Set input to popup stage
        Gdx.input.setInputProcessor(popupStage);
        
        // Configure popup based on food type
        String title = "Food Collected!";
        String content;
        Color titleColor;
        
        if (food instanceof HealthyFood) {
            HealthyFood healthyFood = (HealthyFood) food;
            content = "Healthy Food\n\n" +
                    "Health: +" + healthyFood.getHealthPoints() + "\n" +
                    "Strength: +" + healthyFood.getStrengthBoost() + "\n" +
                    "Points: +20";
            titleColor = Color.GREEN;
        } else {
            UnhealthyFood unhealthyFood = (UnhealthyFood) food;
            content = "Unhealthy Food\n\n" +
                    "Health: -" + unhealthyFood.getDamage() + "\n" +
                    "Points: -10";
            titleColor = Color.RED;
        }
        
        // Set close callback
        dialogPopup.setCloseCallback(closeCallback);
        
        // Show popup
        dialogPopup.show(title, content, titleColor, "Continue");
        popupShown = true;
    }
    
    /**
     * Renders the popup if it's active
     * 
     * @param batch SpriteBatch to use for rendering
     * @return true if popup was rendered (is active)
     */
    public boolean render(SpriteBatch batch) {
        if (!popupShown || !dialogPopup.isVisible()) {
            return false;
        }
        
        // We need to end the batch before using ShapeRenderer
        batch.end();
        
        // Draw semi-transparent black overlay
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 0.7f); // Black with 70% opacity
        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
        
        // Draw popup stage
        popupStage.draw();
        
        // Start the batch again for any subsequent rendering
        batch.begin();
        
        return true;
    }
    
    /**
     * Checks if popup is currently shown
     * @return true if popup is shown
     */
    public boolean isPopupShown() {
        return popupShown && dialogPopup.isVisible();
    }
    
    /**
     * Hides the popup
     */
    public void hidePopup() {
        popupShown = false;
        dialogPopup.hide();
    }
    
    /**
     * Gets the popup stage
     * @return The stage used for popups
     */
    public Stage getPopupStage() {
        return popupStage;
    }
    
    /**
     * Disposes resources
     */
    public void dispose() {
        popupStage.dispose();
        shapeRenderer.dispose();
    }
}