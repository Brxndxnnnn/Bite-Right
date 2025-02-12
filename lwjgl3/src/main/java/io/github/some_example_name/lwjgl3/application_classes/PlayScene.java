package io.github.some_example_name.lwjgl3.application_classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import io.github.some_example_name.lwjgl3.abstract_engine.entity.EntityManager;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.Scene;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneManager;

public class PlayScene extends Scene{
	private BitmapFont font;
	private Stage stage;
	private Skin skin;
	private TextButton pauseBtn;
	private TextButton quitBtn;
	private EntityManager em;

	public PlayScene(SceneManager sceneManager) {
		font = new BitmapFont();
		stage = new Stage();
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		Gdx.input.setInputProcessor(stage);
		
		// louis added
	    em = new EntityManager();
	    em.intializeEntities();
		// end
		
		
		pauseBtn = new TextButton("Pause", skin);
		quitBtn = new TextButton("Quit", skin);
		
		pauseBtn.setPosition(Gdx.graphics.getWidth() / 2 - pauseBtn.getWidth() / 2,
                200);
		quitBtn.setPosition(Gdx.graphics.getWidth() / 2 - pauseBtn.getWidth() / 2,
                150);
		
		pauseBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				sceneManager.changeScene(new PauseScene(sceneManager));
			}
		});
		
		quitBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				sceneManager.changeScene(new HomeScene(sceneManager));
			}
		});
		
		stage.addActor(pauseBtn);
		stage.addActor(quitBtn);

	}
	
	public void render(SpriteBatch batch) {
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		font.draw(batch, "Play", 300, 300);
		stage.draw();
		// louis added
		em.drawEntites(batch);
		em.updateEntities();
	}
	
	@Override
	public void dispose() {
		stage.clear();
		stage.dispose();
		skin.dispose();
	}
}
