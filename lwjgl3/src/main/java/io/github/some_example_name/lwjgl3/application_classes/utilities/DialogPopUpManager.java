package io.github.some_example_name.lwjgl3.application_classes.utilities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

// Manages the current dialog pop up shown - ensures only one is shown at a time
public class DialogPopUpManager {
	private final DialogPopUp dialogPopup;
	private DialogEventListener listener; // Observer
	 
	public DialogPopUpManager(Stage stage, Skin skin) {
		dialogPopup = new DialogPopUp(stage, skin);
	}

    public void showPopUp(String title, String fact) {
        if (!dialogPopup.isVisible() && listener != null) {
        	dialogPopup.show(title, fact);
        	listener.onDialogOpened();
        	
        	// Set callback function in dialog pop up to notify observer when the popup closes
            dialogPopup.setCloseCallback(()->{
	        	// uses listener's (Play Scene) function to resume game
	        	listener.onDialogClosed();
            });
        }
        
    }
    
    public void setDialogEventListener(DialogEventListener listener) {
    	this.listener = listener;
    }
    
    public void render(SpriteBatch batch) {
    	dialogPopup.render(batch);
    }
}
