package io.github.some_example_name.lwjgl3.application_classes;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.some_example_name.lwjgl3.abstract_engine.io.IOManager;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneManager;
import io.github.some_example_name.lwjgl3.abstract_engine.scene.SceneTransitionType;
import io.github.some_example_name.lwjgl3.application_classes.scenes.HomeScene;
public class GameMaster extends ApplicationAdapter {
	
    private SceneManager sceneManager;
    private SpriteBatch batch;
	private IOManager iomanager;

    @Override
    public void create(){
        sceneManager = new SceneManager(); // Create a new instance of SceneManager
        sceneManager.changeScene(new HomeScene(sceneManager), SceneTransitionType.NORMAL); // Render the first scene accordingly
        batch = new SpriteBatch();
		iomanager = new IOManager();
		
		// to use bindings, example as such
		// iomanager.addIOBind(iomanager.getKeyboard(), Keys.NUM_1, () -> audio.loopAudio(true), true);
		// iomanager.addIOBind(iomanager.getKeyboard(), Keys.NUM_3, () -> audio.changeAudio("audio2.mp3"), true);
		// iomanager.addIOBind(iomanager.getKeyboard(), Keys.S, () -> audio.changeAudio("audio1.mp3"), true);
		// iomanager.addIOBind(iomanager.getKeyboard(), Keys.A, () -> audio.setVolume(audio.getVolume() - 5), false);
		// iomanager.addIOBind(iomanager.getKeyboard(), Keys.D, () -> audio.setVolume(audio.getVolume() + 5), false);
		// iomanager.addIOBind(iomanager.getMouse(), Input.Buttons.RIGHT, () -> audio.changeAudio("audio3.mp3"), true);
		// iomanager.addIOBind(iomanager.getMouse(), Input.Buttons.MIDDLE, () -> iomanager.removeIOBind(iomanager.getKeyboard(), Keys.S), true);
		
    }

    @Override
    public void render(){		
		batch.begin();
        sceneManager.render(batch);
		batch.end();

        iomanager.handleInput();
        //iomanager.getMouse().printX();
    }

    @Override
    public void dispose(){
        // dispose scenemanager here if needed
    }

}
