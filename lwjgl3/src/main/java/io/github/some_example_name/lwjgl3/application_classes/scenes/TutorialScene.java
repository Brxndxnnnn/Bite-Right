package io.github.some_example_name.lwjgl3.application_classes.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import io.github.some_example_name.lwjgl3.abstract_engine.audio.AudioPlayer;
import io.github.some_example_name.lwjgl3.abstract_engine.io.IOManager;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.Scene;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneManager;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneTransitionType;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneType;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.Scene;

public class TutorialScene implements Scene{
	private final TextButton backBtn;
	private final IOManager ioManager;
	private Stage stage;
	private Skin skin;
	
	public TutorialScene(SceneManager sceneManager) {
//		super(sceneManager);
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		skin = new Skin(Gdx.files.internal("pixthulhu/skin/pixthulhu-ui.json"));
		
		//AudioPlayer.getInstance().setSceneBGM("home.mp3");

		backBtn = new TextButton("Back", skin);

		backBtn.getLabel().setFontScale(0.5f);


		// INITIALIZE MAIN TABLE FOR THE TITLE
		Table titleTable = new Table();
		titleTable.setFillParent(true);
		titleTable.top(); // Align content to the top
		stage.addActor(titleTable);

		Label label = new Label("Tutorial", skin, "subtitle");
		//label.setColor(Color.YELLOW);
		titleTable.add(label).center().padTop(20); // Center the title at the top with padding
		
		// entity tables
		Table healthyTable = new Table();
		healthyTable.setFillParent(true);
		stage.addActor(healthyTable);
		//entityTable.center();
		
		Image entity = new Image(new Texture(Gdx.files.internal("entities_images/healthyfood.png")));
		entity.setScale(0.5f);
		//entity.pack();
		healthyTable.setPosition(-150, 240);
		healthyTable.add(entity);

		Table unhealthyTable = new Table();
		unhealthyTable.setFillParent(true);
		stage.addActor(unhealthyTable);
		
		entity = new Image(new Texture(Gdx.files.internal("entities_images/unhealthyfood.png")));
		entity.setScale(0.3f);
		unhealthyTable.setPosition(-30, 180);
		unhealthyTable.add(entity);
		
		Table enemyTable = new Table();
		enemyTable.setFillParent(true);
		stage.addActor(enemyTable);
		
		entity = new Image(new Texture(Gdx.files.internal("entities_images/demon.png")));
		entity.setScale(4f);
		enemyTable.setPosition(-240, -200);
		enemyTable.add(entity);

		// content for entity description
		Table contentTable = new Table();
		contentTable.setFillParent(true);
		stage.addActor(contentTable);
		contentTable.top().right();
		
		Label content = new Label("Collect these food items to gain", skin);
		contentTable.add(content).padRight(80).padTop(150);
		contentTable.row();
		
		content = new Label("speed, strength and points", skin);
		contentTable.add(content).padRight(80);
		contentTable.row();
		
		content = new Label("Do not collect these food items", skin);
		contentTable.add(content).padRight(80).padTop(100);
		contentTable.row();
		
		content = new Label("Health will be decreased when picked up", skin);
		contentTable.add(content).padRight(40);
		contentTable.row();
		
		content = new Label("Do not run into monsters", skin);
		contentTable.add(content).padRight(80).padTop(150);
		contentTable.row();

		Table buttonTable = new Table();
		buttonTable.setFillParent(true);
		stage.addActor(buttonTable);

		backBtn.getLabel().setColor(Color.SKY);
		backBtn.setColor(Color.ROYAL);


		buttonTable.bottom();
		buttonTable.padTop(170);
		buttonTable.row();
		buttonTable.add(backBtn).padBottom(10).size(backBtn.getWidth() * 0.6f, backBtn.getHeight() * 0.6f);
	


		backBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				sceneManager.changeScene(SceneType.HOME, SceneTransitionType.NORMAL);
			}
		});

		// Set the keybinds required for Tutorial
		ioManager = new IOManager();

        ioManager.addIOBind(ioManager.getKeyboard(), Keys.ESCAPE, () -> sceneManager.changeScene(SceneType.HOME, SceneTransitionType.NORMAL), true);
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
