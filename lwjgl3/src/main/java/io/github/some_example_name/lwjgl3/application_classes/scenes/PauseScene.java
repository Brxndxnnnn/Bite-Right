package io.github.some_example_name.lwjgl3.application_classes.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import io.github.some_example_name.lwjgl3.abstract_engine.audio.AudioPlayer;
import io.github.some_example_name.lwjgl3.abstract_engine.io.IOManager;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.Scene;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneManager;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneTransitionType;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneType;

public class PauseScene extends Scene{
	private final TextButton resumeBtn;
	private final TextButton restartBtn;
	private final TextButton quitBtn;
	private final IOManager ioManager;

	public PauseScene(SceneManager sceneManager) {
		super(sceneManager);
		AudioPlayer.getInstance().setSceneBGM("pause.mp3");

		resumeBtn = new TextButton("Resume", skin);
		restartBtn = new TextButton("Restart", skin);
		quitBtn = new TextButton("Quit", skin);
		
		resumeBtn.getLabel().setFontScale(0.5f);
		restartBtn.getLabel().setFontScale(0.5f);
		quitBtn.getLabel().setFontScale(0.5f);

		resumeBtn.getLabel().setColor(Color.SKY);
		resumeBtn.setColor(Color.ROYAL);
		restartBtn.getLabel().setColor(Color.SKY);
		restartBtn.setColor(Color.ROYAL);
		quitBtn.getLabel().setColor(Color.SKY);
		quitBtn.setColor(Color.ROYAL);
		
		// INITIALIZE MAIN TABLE FOR THE TITLE
		Table titleTable = new Table();
		titleTable.setFillParent(true);
		titleTable.top(); // Align content to the top
		stage.addActor(titleTable);

		Label label = new Label("Pause", skin, "subtitle");
		titleTable.add(label).center().padTop(20); // Center the title at the top with padding

		Table buttonTable = new Table();
		buttonTable.setFillParent(true);
		stage.addActor(buttonTable);


		
		buttonTable.add(resumeBtn).spaceTop(30).size(resumeBtn.getWidth() * 0.6f, resumeBtn.getHeight() * 0.6f);
		buttonTable.row();
		buttonTable.add(restartBtn).spaceTop(10).size(restartBtn.getWidth() * 0.6f, restartBtn.getHeight() * 0.6f);
		buttonTable.row();
		buttonTable.add(quitBtn).spaceTop(10).size(quitBtn.getWidth() * 0.6f, quitBtn.getHeight() * 0.6f);
		
		resumeBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				sceneManager.changeScene(SceneType.PLAY, SceneTransitionType.RESUME); // need go back to the old play scene
			}
		});

		restartBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				sceneManager.changeScene(SceneType.PLAY, SceneTransitionType.RESTART); // need go back to the old play scene
			}
		});

		quitBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				sceneManager.changeScene(SceneType.HOME, SceneTransitionType.NORMAL);
			}
		});

		// Set the keybinds required for this PlayScene
		ioManager = new IOManager();

		ioManager.addIOBind(ioManager.getKeyboard(), Keys.ESCAPE, () -> sceneManager.changeScene(SceneType.PLAY, SceneTransitionType.RESUME), true);
	}

    @Override
	public void render(SpriteBatch batch) {
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();

		ioManager.handleInput();
	}

}
