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
import io.github.some_example_name.lwjgl3.abstract_engine.scene.Scene;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneManager;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneTransitionType;

public class HomeScene extends Scene{
	private TextButton startBtn;
	private TextButton quitBtn;
	private IOManager ioManager;

	public HomeScene(SceneManager sceneManager, Boolean isHome) {
		super(sceneManager);

		setAudioFilePath("music3.mp3");
        startBtn = new TextButton("Start", skin);
		quitBtn = new TextButton("Quit", skin);

		// INITIALIZE MAIN TABLE FOR THE TITLE
		Table titleTable = new Table();
		titleTable.setFillParent(true);
		titleTable.top(); // Align content to the top
		stage.addActor(titleTable);

		Label label = new Label("Home Scene", skin);
		titleTable.add(label).center().padTop(20); // Center the title at the top with padding

		Table buttonTable = new Table();
		buttonTable.setFillParent(true);
		stage.addActor(buttonTable);

		buttonTable.add(startBtn).space(30);
		buttonTable.row();
		buttonTable.add(quitBtn).space(5);

		startBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				sceneManager.changeScene(new PlayScene(sceneManager, false), SceneTransitionType.NORMAL);
			}
		});

		quitBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		
		// Set the keybinds required for this HomeScene
		ioManager = new IOManager();

		ioManager.addIOBind(ioManager.getKeyboard(), Keys.DOWN, () -> sceneManager.getAudioPlayer().setVolume(sceneManager.getAudioPlayer().getVolume() - 5), true);
		ioManager.addIOBind(ioManager.getKeyboard(), Keys.UP, () -> sceneManager.getAudioPlayer().setVolume(sceneManager.getAudioPlayer().getVolume() + 5), true);
	}

	@Override
	public void render(SpriteBatch batch) {
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();

		ioManager.handleInput();
	}
}
