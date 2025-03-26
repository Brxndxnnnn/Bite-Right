package io.github.some_example_name.lwjgl3.application_classes.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class DialogPopUp {
    private final Stage uiStage;
    private final Skin skin;
    private final Table contentTable;
    private boolean visible = false;
    private Runnable closeCallback;
    private final ShapeRenderer shapeRenderer;
    
    public DialogPopUp(Stage stage, Skin skin) {
        this.uiStage = stage;
        this.skin = skin;
        this.shapeRenderer = new ShapeRenderer();
        this.contentTable = new Table();
        contentTable.setFillParent(true); // contentTable covers the whole screen
        uiStage.addActor(contentTable);
    }
    
    public void show(String title, String content) {
        show(title, content, null);
    }
    
    public void show(String title, String content, Texture imageTexture) {
        contentTable.clear();
        
        Table dialogTable = new Table();
        dialogTable.setBackground(skin.getDrawable("window"));
        
        // Creating title
        Label titleLabel = new Label(title, skin);
        
        // Add image if provided
        if (imageTexture != null) {
            Image foodImage = new Image(imageTexture);
            
            // Scale the image to a reasonable size (80x80 pixels)
            float imageSize = 80f;
            float aspectRatio = (float)imageTexture.getWidth() / (float)imageTexture.getHeight();
            float width = aspectRatio >= 1 ? imageSize : imageSize * aspectRatio;
            float height = aspectRatio >= 1 ? imageSize / aspectRatio : imageSize;
            
            foodImage.setSize(width, height);
            dialogTable.add(foodImage).size(width, height).padBottom(10).row();
        }
        
        // Creating content
        Label contentLabel = new Label(content, skin);
        contentLabel.setWrap(true);
        
        // Close button - to continue the game
        TextButton closeButton = new TextButton("Continue", skin);
        
        // Close button - Add listener to input to clear dialog when clicked
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
                
                // runs callback function defined in Manager, to resume game when dialog closes
                if(closeCallback != null) {
                    closeCallback.run();
                }
            }
        });
        
        dialogTable.pad(20);
        dialogTable.add(titleLabel).padBottom(10).row();
        dialogTable.add(contentLabel).width(260).padBottom(20).row();
        dialogTable.add(closeButton).padTop(10);
        
        // Add completed dialog table to the content table
        contentTable.add(dialogTable).width(300); // dialogTable width set to 300 - contained in contentTable
        
        visible = true;
        
        uiStage.addActor(contentTable);
    }
    
    public void hide() {
        visible = false;
        contentTable.clear(); // Clears the dialog table
    }
    
    public boolean isVisible() {
        return visible;
    }
    
    public void setCloseCallback(Runnable callback) {
        this.closeCallback = callback;
    }
    
    public void render(SpriteBatch batch) {
        if (!visible) return;

        batch.end(); // Stop batch before using ShapeRenderer

        Gdx.input.setInputProcessor(uiStage);
        // Draw semi-transparent black overlay
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 0.7f);
        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        uiStage.draw(); // Draw UI elements of the popup
        batch.begin(); // Restart batch
    }
}