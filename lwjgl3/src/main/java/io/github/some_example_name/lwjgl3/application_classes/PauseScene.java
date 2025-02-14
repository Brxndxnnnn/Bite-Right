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

public class PauseScene extends Scene{
	private TextButton resumeBtn;
	private TextButton quitBtn;

	public PauseScene(SceneManager sceneManager) {
		super(sceneManager);
		resumeBtn = new TextButton("Resume", skin);
		quitBtn = new TextButton("Quit", skin);
		
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		
		Label label = new Label("Pause", skin);
		table.add(label);
        table.row();

		resumeBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				sceneManager.changeScene(new PlayScene(sceneManager)); // need go back to the old play scene
			}
		});
		
		table.add(resumeBtn).spaceTop(50);
        table.row();

		quitBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				sceneManager.changeScene(new HomeScene(sceneManager));
			}
		});
		
		table.add(quitBtn).spaceTop(10);
//		table.debug();
	}
	
	public void render(SpriteBatch batch) {
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
	}

}