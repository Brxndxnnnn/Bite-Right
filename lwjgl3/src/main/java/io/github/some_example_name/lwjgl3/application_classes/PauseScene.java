package io.github.some_example_name.lwjgl3.application_classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import io.github.some_example_name.lwjgl3.abstract_engine.io.IOManager;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneManager;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SecondaryScene;

public class PauseScene extends SecondaryScene{
	private TextButton resumeBtn;
	private TextButton quitBtn;
	private IOManager ioManager;

	public PauseScene(SceneManager sceneManager) {
		super(sceneManager);
		setAudioFilePath("music2.mp3"); // WENJUN CHANGE FILEPATH OF THIS!!

		resumeBtn = new TextButton("Resume", skin);
		quitBtn = new TextButton("Quit", skin);

		// INITIALIZE MAIN TABLE FOR THE TITLE
		Table titleTable = new Table();
		titleTable.setFillParent(true);
		titleTable.top(); // Align content to the top
		stage.addActor(titleTable);

		Label label = new Label("Pause Scene", skin);
		titleTable.add(label).center().padTop(20); // Center the title at the top with padding

		Table buttonTable = new Table();
		buttonTable.setFillParent(true);
		stage.addActor(buttonTable);

		buttonTable.add(resumeBtn).spaceTop(30);
		buttonTable.row();
		buttonTable.add(quitBtn).spaceTop(10);
		
		resumeBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				sceneManager.changeScene(new PlayScene(sceneManager, false)); // need go back to the old play scene
			}
		});

		quitBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				sceneManager.changeScene(new HomeScene(sceneManager, true));
			}
		});

		// Set the keybinds required for this PlayScene
		ioManager = new IOManager();

		ioManager.addIOBind(ioManager.getKeyboard(), Keys.ESCAPE, () -> sceneManager.changeScene(new PlayScene(sceneManager, false)), true);
	}

	public void render(SpriteBatch batch) {
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();

		ioManager.handleInput();
	}

}
