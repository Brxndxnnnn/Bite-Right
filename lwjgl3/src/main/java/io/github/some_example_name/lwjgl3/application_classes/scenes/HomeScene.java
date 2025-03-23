package io.github.some_example_name.lwjgl3.application_classes.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import io.github.some_example_name.lwjgl3.abstract_engine.audio.AudioPlayer;
import io.github.some_example_name.lwjgl3.abstract_engine.io.IOManager;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.Scene;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneManager;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneTransitionType;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneType;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.Scene;

public class HomeScene implements Scene{
	private final TextButton startBtn;
	private final TextButton tutBtn;
	private final TextButton quitBtn;
	private final IOManager ioManager;
	private Stage stage;
	private Skin skin;
    private Texture homeBackground;
    private SpriteBatch batch;
    private float alpha = 0f;
    private float fadeSpeed = 0.01f;
    private boolean fadingIn = true;
    private Image backgroundImage;


	public HomeScene(SceneManager sceneManager) {
//		super(sceneManager);

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
        homeBackground = new Texture(Gdx.files.internal("assets/homebackground.jpg"));
		skin = new Skin(Gdx.files.internal("pixthulhu/skin/pixthulhu-ui.json"));

        Image backgroundImage = new Image(homeBackground);
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Add the background image to the stage (this will make it part of the UI)
        stage.addActor(backgroundImage);

		AudioPlayer.getInstance().setSceneBGM("home.mp3");

        startBtn = new TextButton("Start", skin);
		tutBtn = new TextButton("Tutorial", skin);
		quitBtn = new TextButton("Quit", skin);

		startBtn.getLabel().setFontScale(0.5f);
		tutBtn.getLabel().setFontScale(0.5f);
		quitBtn.getLabel().setFontScale(0.5f);

		// INITIALIZE MAIN TABLE FOR THE TITLE
		Table titleTable = new Table();
		titleTable.setFillParent(true);
		titleTable.top(); // Align content to the top
		stage.addActor(titleTable);

		Label label = new Label("Hungry Dash", skin, "title");
		label.setColor(Color.YELLOW);
		titleTable.add(label).center().padTop(20); // Center the title at the top with padding

		Table buttonTable = new Table();
		buttonTable.setFillParent(true);
		stage.addActor(buttonTable);

		startBtn.getLabel().setColor(Color.SKY);
		startBtn.setColor(Color.ROYAL);

		quitBtn.getLabel().setColor(Color.SKY);
		quitBtn.setColor(Color.ROYAL);

		tutBtn.getLabel().setColor(Color.SKY);
		tutBtn.setColor(Color.ROYAL);

		buttonTable.center();
		buttonTable.padTop(170);
		buttonTable.add(startBtn).size(startBtn.getWidth() * 0.6f, startBtn.getHeight() * 0.6f);
		buttonTable.row();		
		buttonTable.add(tutBtn).padTop(10).size(tutBtn.getWidth() * 0.6f, tutBtn.getHeight() * 0.6f);
		buttonTable.row();
		buttonTable.add(quitBtn).padTop(10).size(quitBtn.getWidth() * 0.6f, quitBtn.getHeight() * 0.6f);


		startBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				sceneManager.changeScene(SceneType.DIFFICULTY, SceneTransitionType.NORMAL);
			}
		});
		
		tutBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				sceneManager.changeScene(SceneType.TUTORIAL, SceneTransitionType.NORMAL);
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

		// ioManager.addIOBind(ioManager.getKeyboard(), Keys.DOWN, () -> sceneManager.getAudioPlayer().setVolume(sceneManager.getAudioPlayer().getVolume() - 5), true);
		// ioManager.addIOBind(ioManager.getKeyboard(), Keys.UP, () -> sceneManager.getAudioPlayer().setVolume(sceneManager.getAudioPlayer().getVolume() + 5), true);
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
