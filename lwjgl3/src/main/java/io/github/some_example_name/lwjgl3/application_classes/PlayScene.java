package io.github.some_example_name.lwjgl3.application_classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import io.github.some_example_name.lwjgl3.abstract_engine.entity.EntityManager;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.PrimaryScene;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneManager;

public class PlayScene extends PrimaryScene{
	private TextButton pauseBtn;
	private TextButton quitBtn;
	private EntityManager entityManager;

	public PlayScene(SceneManager sceneManager, Boolean isHome) {
		super(sceneManager, isHome);
		setAudioFilePath("music1.mp3");

		// INITIALIZE ENTITIES
	    entityManager = new EntityManager();
	    entityManager.intializeEntities();

		// INITIALIZE BUTTONS
		pauseBtn = new TextButton("Pause", skin);
		quitBtn = new TextButton("Quit", skin);

		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);

		Label label = new Label("Play", skin);
		table.add(label);
        table.row();

		// INITIALIZE BUTTON LISTENERS
		pauseBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				sceneManager.changeScene(new PauseScene(sceneManager));
			}
		});

		table.add(pauseBtn).spaceTop(30);
        table.row();

		quitBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				sceneManager.changeScene(new HomeScene(sceneManager, true));
			}
		});

		table.add(quitBtn).spaceTop(10);
	}

	public void create(){}

	public void render(SpriteBatch batch) {
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();

		// Draw Entities on Screen
		entityManager.drawEntites(batch);
		entityManager.updateEntities();
	}

}
