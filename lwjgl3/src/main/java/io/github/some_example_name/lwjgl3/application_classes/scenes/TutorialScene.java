package io.github.some_example_name.lwjgl3.application_classes.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import io.github.some_example_name.lwjgl3.abstract_engine.io.IOManager;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.Scene;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneTransitionType;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneType;

public class TutorialScene implements Scene {
    private final TextButton backBtn;
    private final IOManager ioManager;
    private final Stage stage;
    private final Skin skin;
    
    public TutorialScene(GameSceneManager sceneManager) {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("pixthulhu/skin/pixthulhu-ui.json"));
        
        // Create main root table for all content
        Table rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);
        
        // Create the title
        Label titleLabel = new Label("Tutorial", skin, "subtitle");
        titleLabel.setAlignment(Align.center);
        
        // Create back button
        backBtn = new TextButton("Back", skin);
        backBtn.getLabel().setFontScale(0.5f);
        backBtn.getLabel().setColor(Color.SKY);
        backBtn.setColor(Color.ROYAL);
        
        // Set up the click listener for back button
        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sceneManager.changeScene(SceneType.HOME, SceneTransitionType.NORMAL);
            }
        });
        
        // Load all textures once
        Texture healthyFoodTexture = new Texture(Gdx.files.internal("entities_images/banana.png"));
        Texture unhealthyFoodTexture = new Texture(Gdx.files.internal("entities_images/fries.png"));
        Texture demonTexture = new Texture(Gdx.files.internal("entities_images/demon.png"));
        
        // Create tutorial content table
        Table tutorialContentTable = new Table();
        tutorialContentTable.defaults().pad(15).align(Align.left);
        //tutorialContentTable.setDebug(true); // Uncomment to see table borders for debugging
        
        // Row 1: Healthy Food
        Image healthyFoodImage = new Image(healthyFoodTexture);
        healthyFoodImage.setScale(1f);
        
        Label healthyFoodLabel = new Label("Collect healthy food items to gain\nhealth, speed and points", skin);
        healthyFoodLabel.setWrap(true);
        healthyFoodLabel.setAlignment(Align.left);
        
        tutorialContentTable.add(healthyFoodImage).size(80).padRight(30);
        tutorialContentTable.add(healthyFoodLabel).width(300).row();
        
        // Row 2: Unhealthy Food
        Image unhealthyFoodImage = new Image(unhealthyFoodTexture);
        unhealthyFoodImage.setScale(1f);
        
        Label unhealthyFoodLabel = new Label("Collecting unhealthy food items\ndecreases health, speed and points", skin);
        unhealthyFoodLabel.setWrap(true);
        unhealthyFoodLabel.setAlignment(Align.left);
        
        tutorialContentTable.add(unhealthyFoodImage).size(80).padRight(30);
        tutorialContentTable.add(unhealthyFoodLabel).width(300).row();
        
        // Row 3: Demon
        Image demonImage = new Image(demonTexture);
        demonImage.setScale(1f);
        
        Label demonLabel = new Label("Do not run into angry chef!\nInstant GAME OVER!", skin);
        demonLabel.setWrap(true);
        demonLabel.setAlignment(Align.left);
        
        tutorialContentTable.add(demonImage).size(80).padRight(30);
        tutorialContentTable.add(demonLabel).width(300).row();
        
        // Add title to the root table
        rootTable.add(titleLabel).padTop(30).padBottom(40).row();
        
        // Add tutorial content to the root table
        rootTable.add(tutorialContentTable).expand().row();
        
        // Add back button to the root table
        rootTable.add(backBtn).size(backBtn.getWidth() * 0.7f, backBtn.getHeight() * 0.7f)
                .padBottom(30).align(Align.center);
        
        // Set the keybinds required for Tutorial
        ioManager = new IOManager();
        ioManager.addIOBind(ioManager.getKeyboard(), Keys.ESCAPE, 
                () -> sceneManager.changeScene(SceneType.HOME, SceneTransitionType.NORMAL), true);
    }

    @Override
    public void render(SpriteBatch batch) {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        ioManager.handleInput();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();        
    }
}