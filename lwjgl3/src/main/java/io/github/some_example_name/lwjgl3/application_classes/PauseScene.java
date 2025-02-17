package io.github.some_example_name.lwjgl3.application_classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneManager;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SecondaryScene;

public class PauseScene extends SecondaryScene{
	private TextButton resumeBtn;
	private TextButton quitBtn;

	public PauseScene(SceneManager sceneManager) {
		super(sceneManager);
		setAudioFilePath("music2.mp3"); // WENJUN CHANGE FILEPATH OF THIS!!

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
				sceneManager.changeScene(new PlayScene(sceneManager, false)); // need go back to the old play scene
			}
		});

		table.add(resumeBtn).spaceTop(30);
        table.row();

		quitBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				sceneManager.changeScene(new HomeScene(sceneManager, true));
			}
		});

		table.add(quitBtn).spaceTop(10);
	}

	public void render(SpriteBatch batch) {
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
	}

}
