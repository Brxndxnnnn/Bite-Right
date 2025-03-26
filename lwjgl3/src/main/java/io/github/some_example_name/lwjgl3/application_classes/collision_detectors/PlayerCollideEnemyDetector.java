package io.github.some_example_name.lwjgl3.application_classes.collision_detectors;

import io.github.some_example_name.lwjgl3.abstract_engine.audio.AudioPlayer;
import io.github.some_example_name.lwjgl3.abstract_engine.collision.EntityCollisionDetector;
import io.github.some_example_name.lwjgl3.application_classes.entities.Enemy;
import io.github.some_example_name.lwjgl3.application_classes.entities.Player;

public class PlayerCollideEnemyDetector extends EntityCollisionDetector<Player, Enemy>{
    private final Player player;
    private final Enemy enemy;
    private boolean collisionDetected;

    public PlayerCollideEnemyDetector(Player player, Enemy enemy){
        super(player, enemy);
        this.player = player;
        this.enemy = enemy;
    }

    // This collision event causes instant death to the player
    @Override
    public void resolveCollision() {
        if (collisionDetected) return;
        collisionDetected = true;
        // Get bounding boxes

        // Check collision
            System.out.println("Player collided with Enemy - INSTANT DEATH!");
                // This loop only runs once
                AudioPlayer.getInstance().playSFX("SFX/crunch.mp3");
//            Set player health to 0 (instant death)
            player.setHealth(0);
    }
}
