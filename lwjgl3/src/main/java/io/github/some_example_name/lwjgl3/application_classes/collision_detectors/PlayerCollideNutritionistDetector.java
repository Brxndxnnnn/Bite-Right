package io.github.some_example_name.lwjgl3.application_classes.collision_detectors;

import io.github.some_example_name.lwjgl3.abstract_engine.audio.AudioPlayer;
import io.github.some_example_name.lwjgl3.abstract_engine.collision.EntityCollisionDetector;
import io.github.some_example_name.lwjgl3.abstract_engine.collision.Removable;
import io.github.some_example_name.lwjgl3.application_classes.entities.Nutritionist;
import io.github.some_example_name.lwjgl3.application_classes.entities.Player;
import io.github.some_example_name.lwjgl3.application_classes.utilities.DialogPopUpManager;

public class PlayerCollideNutritionistDetector extends EntityCollisionDetector<Player, Nutritionist> implements Removable{
    private final Player player;
    private final Nutritionist nutritionist;
    private DialogPopUpManager dialogPopUpManager;
    private boolean toBeRemoved = false;

    public PlayerCollideNutritionistDetector(Player player, Nutritionist nutritionist, DialogPopUpManager dialogPopUpManager) {
        super(player, nutritionist);
        this.player = player;
        this.nutritionist = nutritionist;
        this.dialogPopUpManager = dialogPopUpManager;
    }

    @Override
    public boolean checkCollision() {
        // Check if we're still in cooldown
        if(nutritionist.shouldBeRemoved()){
            return false;
        }
        return super.checkCollision();
        
    }

    @Override
    public void resolveCollision() {
        if (nutritionist.shouldBeRemoved()) return;
        // Check collision
        System.out.println("Player collided with Nutritionist!");
        
        // Play a sound effect for the collision
        AudioPlayer.getInstance().playSFX("SFX/nutritionist.mp3");
        
        // TODO: Add popup logic or other nutritionist-specific behavior here
        // For now, just apply a small health bonus
        player.adjustHealth(5);
        
        // Force the nutritionist to change direction
        nutritionist.forceDirectionChange();
        
        // Ensure the nutritionist enters collision state for smooth movement
        nutritionist.setCollisionState(true);


        nutritionist.setToBeRemoved();

        setToBeRemoved();

        dialogPopUpManager.showPopUp("Did you know?", nutritionist.getFunFact());
    }

    @Override
    public void setToBeRemoved() {
        toBeRemoved = true;
    }

    @Override
    public boolean shouldBeRemoved() {
        return toBeRemoved;
    }
}