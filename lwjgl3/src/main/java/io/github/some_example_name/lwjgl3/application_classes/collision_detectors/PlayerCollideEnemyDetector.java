package io.github.some_example_name.lwjgl3.application_classes.collision_detectors;

import io.github.some_example_name.lwjgl3.abstract_engine.audio.AudioPlayer;
import io.github.some_example_name.lwjgl3.abstract_engine.collision.EntityCollisionDetector;
import io.github.some_example_name.lwjgl3.application_classes.entities.Enemy;
import io.github.some_example_name.lwjgl3.application_classes.entities.Player;

/**
 * Detects and resolves collisions between the player and enemy entities.
 * When a collision occurs, the player immediately loses all health (instant death).
 */
public class PlayerCollideEnemyDetector extends EntityCollisionDetector<Player, Enemy> {
    private final Player player;
    private boolean collisionDetected;

    /**
     * Creates a new detector for player-enemy collisions.
     *
     * @param player The player entity
     * @param enemy The enemy entity
     */
    public PlayerCollideEnemyDetector(Player player, Enemy enemy) {
        super(player, enemy);
        this.player = player;
    }

    /**
     * Resolves player-enemy collisions by killing the player instantly.
     * Only triggers once to prevent multiple health reductions.
     */
    @Override
    public void resolveCollision() {
        if (collisionDetected) return;
        collisionDetected = true;

        // Play death sound
        AudioPlayer.getInstance().playSFX("SFX/bad.mp3");
        
        // Set player health to 0 (instant death)
        player.setHealth(0);
    }
}