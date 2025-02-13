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
import io.github.some_example_name.lwjgl3.abstract_engine.scene.Scene;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneManager;

public class PlayScene extends Scene{
	private TextButton pauseBtn;
	private TextButton quitBtn;
	private EntityManager em;

	public PlayScene(SceneManager sceneManager) {
		super();
		
		// louis added
	    em = new EntityManager();
	    em.intializeEntities();
		// end
		
		
		pauseBtn = new TextButton("Pause", skin);
		quitBtn = new TextButton("Quit", skin);
				
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		
		Label label = new Label("Play", skin);
		table.add(label);
        table.row();
		
		pauseBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				SceneManager.getInstance().changeScene(new PauseScene());
			}
		});
		
		table.add(pauseBtn).spaceTop(50);
        table.row();
		
		quitBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				SceneManager.getInstance().changeScene(new HomeScene());
			}
		});
		
		table.add(quitBtn).spaceTop(10);
	}
	
	public void render(SpriteBatch batch) {
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
		// louis added
		em.drawEntites(batch);
		em.updateEntities();
	}

}
