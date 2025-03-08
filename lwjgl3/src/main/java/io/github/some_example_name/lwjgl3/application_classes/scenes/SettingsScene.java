package io.github.some_example_name.lwjgl3.application_classes.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import io.github.some_example_name.lwjgl3.abstract_engine.scene.Scene;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneManager;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneTransitionType;

public class SettingsScene extends Scene{
	private final TextButton saveBtn;
	private final TextButton cancelBtn;

	public SettingsScene(SceneManager sceneManager) {
		super(sceneManager);
		
		saveBtn = new TextButton("Save", skin);
		cancelBtn = new TextButton("Cancel", skin);
		
		saveBtn.getLabel().setColor(Color.SKY);
		saveBtn.setColor(Color.ROYAL);
		cancelBtn.getLabel().setColor(Color.SKY);
		cancelBtn.setColor(Color.ROYAL);
		saveBtn.getLabel().setFontScale(0.5f);
		cancelBtn.getLabel().setFontScale(0.5f);
		
		// INITIALIZE MAIN TABLE FOR THE TITLE
		Table titleTable = new Table();
		titleTable.setFillParent(true);
		titleTable.top(); // Align content to the top
		stage.addActor(titleTable);
		
		Label label = new Label("Settings", skin, "subtitle");
		titleTable.add(label).center().padTop(20); // Center the title at the top with padding
		
		Table buttonTable = new Table();
		buttonTable.setFillParent(true);
		buttonTable.bottom(); // Align content to the bottom
		stage.addActor(buttonTable);
		buttonTable.add(saveBtn).padBottom(30).size(saveBtn.getWidth() * 0.6f, saveBtn.getHeight() * 0.6f).expandX().center();
		buttonTable.add(cancelBtn).padBottom(30).size(cancelBtn.getWidth() * 0.6f, cancelBtn.getHeight() * 0.6f).expandX().center();
		buttonTable.row();
		
		saveBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				sceneManager.changeScene(new HomeScene(sceneManager), SceneTransitionType.NORMAL);
			}
		});
		
		cancelBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				System.out.println("Going to settings");
				sceneManager.changeScene(new HomeScene(sceneManager), SceneTransitionType.NORMAL);
			}
		});
	}

	@Override
	public void render(SpriteBatch batch) {
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
	}
}