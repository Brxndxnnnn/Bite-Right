package io.github.some_example_name.lwjgl3.application_classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import io.github.some_example_name.lwjgl3.abstract_engine.scene.Scene;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneManager;

public class HomeScene extends Scene{
	private TextButton startBtn;
	
	public HomeScene(SceneManager sceneManager) {
		super(sceneManager);
		// Initialise button
        startBtn = new TextButton("Start", skin);
		
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);

		Label label = new Label("Home", skin);
		table.add(label);
        table.row();
        
		// Initialise button listener
		startBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				sceneManager.changeScene(new PlayScene(sceneManager));
			}
		});
		
		table.add(startBtn).space(30);
	}
	
	public void render(SpriteBatch batch) {
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
	}
}
