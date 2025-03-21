package io.github.some_example_name.lwjgl3.application_classes.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Dialog popup UI component for displaying information
 */
public class DialogPopUp {
    private final Stage stage;
    private final Skin skin;
    private Table contentTable;
    private boolean visible = false;
    private Runnable closeCallback;
    
    /**
     * Constructor for dialog popup
     * 
     * @param stage The stage to display the popup on
     * @param skin The skin to use for UI components
     */
    public DialogPopUp(Stage stage, Skin skin) {
        this.stage = stage;
        this.skin = skin;
        createUI();
    }
    
    /**
     * Creates the UI components for the popup
     */
    private void createUI() {
        // Create main table that will hold content
        contentTable = new Table();
        contentTable.setFillParent(true);
        contentTable.center();
        stage.addActor(contentTable);
        
        // Initially hide the popup
        hide();
    }
    
    /**
     * Shows the popup with title, content and a button
     * 
     * @param title The title of the popup
     * @param content The content text
     * @param titleColor Color for the title
     * @param buttonText Text for the button
     */
    public void show(String title, String content, Color titleColor, String buttonText) {
        // Clear previous content
        contentTable.clear();
        
        // Create popup content
        Table dialogTable = new Table();
        dialogTable.setBackground(skin.getDrawable("window"));
        
        Label titleLabel = new Label(title, skin, "subtitle");
        if (titleColor != null) {
            titleLabel.setColor(titleColor);
        }
        
        Label contentLabel = new Label(content, skin);
        contentLabel.setWrap(true);
        
        TextButton closeButton = new TextButton(buttonText, skin);
        closeButton.getLabel().setFontScale(0.5f);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
                if (closeCallback != null) {
                    closeCallback.run();
                }
            }
        });
        
        // Add components to dialog table
        dialogTable.pad(20);
        dialogTable.add(titleLabel).padBottom(10).row();
        dialogTable.add(contentLabel).width(260).padBottom(20).row();
        dialogTable.add(closeButton).padTop(10);
        
        // Add dialog table to main content table
        contentTable.add(dialogTable).width(300).height(200);
        
        // Show the popup
        visible = true;
    }
    
    /**
     * Hides the popup
     */
    public void hide() {
        visible = false;
        contentTable.clear();
    }
    
    /**
     * Checks if the popup is currently visible
     * @return true if visible
     */
    public boolean isVisible() {
        return visible;
    }
    
    /**
     * Sets a callback to be called when the popup is closed
     * @param callback The callback to run
     */
    public void setCloseCallback(Runnable callback) {
        this.closeCallback = callback;
    }
}