package io.github.some_example_name.lwjgl3.application_classes.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import io.github.some_example_name.lwjgl3.abstract_engine.scene.Scene;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneManager;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneTransitionType;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneType;

public class DifficultyScene implements Scene {
	private final TextButton easyBtn;
	private final TextButton normalBtn;
	private final TextButton hardBtn;
	private final TextButton backBtn;
	private Stage stage;
	private Skin skin;
	
	public DifficultyScene(SceneManager sceneManager) {
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		skin = new Skin(Gdx.files.internal("pixthulhu/skin/pixthulhu-ui.json"));
		
		easyBtn = new TextButton("Easy", skin);
		easyBtn.getLabel().setFontScale(0.5f);
		easyBtn.getLabel().setColor(Color.SKY);
		easyBtn.setColor(Color.ROYAL);
        
		normalBtn = new TextButton("Normal", skin);
		normalBtn.getLabel().setFontScale(0.5f);
		normalBtn.getLabel().setColor(Color.SKY);
		normalBtn.setColor(Color.ROYAL);
		
		hardBtn = new TextButton("Hard", skin);
		hardBtn.getLabel().setFontScale(0.5f);
		hardBtn.getLabel().setColor(Color.SKY);
		hardBtn.setColor(Color.ROYAL);
		
		backBtn = new TextButton("Back", skin);
		backBtn.getLabel().setFontScale(0.5f);
		backBtn.getLabel().setColor(Color.SKY);
		backBtn.setColor(Color.ROYAL);
        
        Table titleTable = new Table();
		titleTable.setFillParent(true);
		titleTable.top(); // Align content to the top
		stage.addActor(titleTable);
		
		Label label = new Label("Select a difficulty", skin, "subtitle");
		titleTable.add(label).center().padTop(20); // Center the title at the top with padding
		titleTable.row();
		
		Table buttonTable = new Table();
		buttonTable.setFillParent(true);
		buttonTable.center(); // Align content to the bottom
		stage.addActor(buttonTable);
		
		Table backTable = new Table();
		backTable.setFillParent(true);
		backTable.bottom(); // Align content to the bottom
		stage.addActor(backTable);
		
		buttonTable.add(easyBtn).padBottom(10).size(easyBtn.getWidth() * 0.6f, easyBtn.getHeight() * 0.6f).expandX().center();
		buttonTable.row();
		buttonTable.add(normalBtn).padBottom(10).size(normalBtn.getWidth() * 0.6f, normalBtn.getHeight() * 0.6f).expandX().center();
		buttonTable.row();
		buttonTable.add(hardBtn).padBottom(10).size(hardBtn.getWidth() * 0.6f, hardBtn.getHeight() * 0.6f).expandX().center();
		
		backTable.add(backBtn).padBottom(30).size(backBtn.getWidth() * 0.6f, backBtn.getHeight() * 0.6f).expandX().center();
		
		easyBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				sceneManager.changeScene(SceneType.PLAY, SceneTransitionType.NORMAL);
			}
		});
				
		normalBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				sceneManager.changeScene(SceneType.PLAY, SceneTransitionType.NORMAL);
			}
		});
	
		hardBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				sceneManager.changeScene(SceneType.PLAY, SceneTransitionType.NORMAL);
			}
		});
		
		
		backBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				sceneManager.changeScene(SceneType.HOME, SceneTransitionType.NORMAL);
			}
		});
		
	}

	@Override
	public void render(SpriteBatch batch) {
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();		
	}

	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();		
	}
}
