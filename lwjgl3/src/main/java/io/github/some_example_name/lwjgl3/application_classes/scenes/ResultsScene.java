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

public class ResultsScene extends Scene {
	private final TextButton quitBtn;

	public ResultsScene(SceneManager sceneManager) {
		super(sceneManager);
        quitBtn = new TextButton("Quit", skin);
        quitBtn.getLabel().setFontScale(0.5f);
        quitBtn.getLabel().setColor(Color.SKY);
        quitBtn.setColor(Color.ROYAL);
        
        Table titleTable = new Table();
		titleTable.setFillParent(true);
		titleTable.top(); // Align content to the top
		stage.addActor(titleTable);
		
		Label label = new Label("Results", skin, "subtitle");
		titleTable.add(label).center().padTop(20); // Center the title at the top with padding
		Table buttonTable = new Table();
		buttonTable.setFillParent(true);
		buttonTable.bottom(); // Align content to the bottom
		stage.addActor(buttonTable);
		buttonTable.add(quitBtn).padBottom(30).size(quitBtn.getWidth() * 0.6f, quitBtn.getHeight() * 0.6f).expandX().center();
		
		quitBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
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
