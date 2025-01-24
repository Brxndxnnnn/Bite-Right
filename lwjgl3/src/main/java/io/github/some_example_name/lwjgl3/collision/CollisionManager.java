package io.github.some_example_name.lwjgl3.collision;

import java.util.List;

import io.github.some_example_name.lwjgl3.entity.Entity;

public class CollisionManager {

    public void detectCollisions(Entity player, List<Entity> enemies) {
        for (Entity enemy : enemies) {
            if (isColliding(player, enemy)) {
                handleCollision(player, enemy);
            }
        }
    }

    private boolean isColliding(Entity entity1, Entity entity2) {
        return Math.abs(entity1.getX() - entity2.getX()) < entity1.getTexture().getWidth() &&
               Math.abs(entity1.getY() - entity2.getY()) < entity1.getTexture().getHeight();
    }

    private void handleCollision(Entity entity1, Entity entity2) {
        entity1.reduceHealth(entity2.getAttack());
        System.out.println(entity1.getName() + " collided with " + entity2.getName());
    }
}
