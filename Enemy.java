package com.example.game1;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.Random;

public class Enemy {
    public double enemyX;
    public double enemyY;
    public boolean drawEnemy = true;

    Random rand = new Random();

    public Enemy(double enemyX, double enemyY) {
        this.enemyX = enemyX;
        this.enemyY = enemyY;
    }
    public double calculateDistance(double xOne, double yOne, double xTwo, double yTwo) {
        double g1 = Math.pow((xTwo - xOne), 2);
        double g2 = Math.pow((yTwo - yOne), 2);
        double g = g1 + g2;
        return Math.sqrt(g);
    }

    public void draw(GraphicsContext gc) {
        if (drawEnemy) {
            gc.setFill(Color.RED);
            gc.fillRect(enemyX, enemyY, 25, 25);
        }

    }
    public void act() {
        if (drawEnemy) {
            double distanceX = HelloApplication.playerX - enemyX;
            double distanceY = HelloApplication.playerY - enemyY;
            double degreeToPlayer = java.lang.Math.atan2(distanceY, distanceX);
            double enemyMoveX = java.lang.Math.cos(degreeToPlayer);
            double enemyMoveY = java.lang.Math.sin(degreeToPlayer);
            enemyX += enemyMoveX * rand.nextDouble(5) + 1;
            enemyY += enemyMoveY * rand.nextDouble(5) + 1;
            double epDistance = calculateDistance(enemyX, enemyY, HelloApplication.playerX, HelloApplication.playerY);
            if (epDistance <= 25) {
                HelloApplication.lives--;
                drawEnemy = false;
            }
        }
    }
}
