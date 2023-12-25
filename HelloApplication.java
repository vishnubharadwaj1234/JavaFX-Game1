package com.example.game1;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;




public class HelloApplication extends Application {

    Random rand = new Random();

    public final int screenWidth = 600;
    public final int screenHeight = 600;

    public static double playerX;
    public static double playerY = 50;

    public static double playerXVel = 0;
    public static double playerYVel = 0;

    public static ArrayList<Enemy> enemies = new ArrayList<>();
    public static boolean a = false;
    public static boolean d = false;
    public static boolean w = false;
    public static boolean s = false;

    public final static double gravity = 10;
    public final static double fbAcceleration = 200;
    public final static double udAcceleration = 200;

    public final static double maximumVelocity = 600;

    public static long deltaTime = System.currentTimeMillis();
    public final static double friction = 0.99;

    public static int lives = 7;

    public static long frames = 0;

    Random randX = new Random();
    Random randY = new Random();

    public double arcDeg = 360;

    public Boolean restart = false;

    @Override
    public void start(Stage stage)  {

        Canvas canvas = new Canvas(screenWidth, screenHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        // Create a timeline, with a gap between newFrame() calls of 16.66_ seconds, meaning around 60 fps.
        // Pass the newFrame() function as the function to be called each frame.
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(16.0 + (2.0 / 3.0)), e -> newFrame(gc)));

        tl.setCycleCount(Animation.INDEFINITE);
        canvas.setFocusTraversable(true);

        // handle mouse and key events
        canvas.setOnKeyPressed(e -> {
            // KeyCode holds what key is being referred to
            KeyCode code = e.getCode();
//            System.out.println("Key was pressed: " + code);


//            if (canvas.setOnKeyPressed() == W) {
//                w = true;
//            } else if (canvas.setOnKeyPressed() == S) {
//                s = true;
//            } else if (canvas.setOnKeyPressed() == A) {
//                a = true;
//            } else if (canvas.setOnKeyPressed() == D) {
//                d = true;
//            }

//             canvas.setOnKeyReleased(e -> {
//
//                w = false;
//            });
//            canvas.setOnKeyReleased( -> S {
//                s = false;
//            });
//            canvas.setOnKeyReleased( -> A {
//                a = false;
//            });
//            canvas.setOnKeyReleased( -> D {
//                d = false;
//            });

            switch (e.getCode()) {
                case W -> w = true;
                case S -> s = true;
                case A -> a = true;
                case D -> d = true;
            }

            if (e.getCode() == KeyCode.R) {
                if (lives == 0) {
                    restart = true;
                }

            }


        });
        canvas.setOnKeyReleased(e -> {
            KeyCode code = e.getCode();
            switch (e.getCode()) {
                case W -> w = !true;
                case S -> s = !true;
                case A -> a = !true;
                case D -> d = !true;
            }
//            System.out.println("Key was released: " + code);
        });
        canvas.setOnMouseDragged(e -> {
            double x = e.getX();
            double y = e.getY();
            // these coordinates represent the constant location of the mouse.
        });
        canvas.setOnMouseClicked(e -> {
            double x = e.getX();
            double y = e.getY();



            // these coordinates represent the locations the mouse has been clicked at.
            System.out.println("Mouse clicked at: " + x + "," + y);
        });



        stage.setTitle("JavaFxGame");
        stage.setScene(new Scene(new StackPane(canvas)));
        stage.show();
        tl.play();
    }

    private void newFrame(GraphicsContext gc) {

        double dt = ((double)System.currentTimeMillis() - (double)deltaTime) / (double)1000;

        if (frames >= 100) {
            enemies.add(new Enemy(randX.nextDouble(600), randY.nextDouble(600)));
            frames = 0;
        }

        // draw the background as a grey rectangle
        // shapes are drawn from their top left corner.
        gc.setFill(Color.GREY);
        gc.fillRect(0,0,screenWidth,screenHeight); // starting from 0,0, which is the top left corner of the screen


        gc.setFill(Color.RED);
        gc.fillRect(0,0,50*lives,25);

        gc.setFill(Color.BLUE);
        gc.fillArc(playerX, playerY, 25, 25, 180, arcDeg, ArcType.ROUND);
//        System.out.println(playerX);
//        System.out.println(playerY);
        if (lives > 0) {
            if (w) {
                playerYVel -= udAcceleration * dt;
            }
            if (s) {
                playerYVel += udAcceleration * dt;
            }
            if (a) {
                playerXVel -= fbAcceleration * dt;
            }
            if (d) {
                playerXVel += fbAcceleration * dt;
            }
            if (playerXVel > maximumVelocity) {
                playerXVel = maximumVelocity;
            }
            if (playerYVel > maximumVelocity) {
                playerYVel = maximumVelocity;
            }
            playerXVel *= friction;
            playerYVel *= friction;
            playerX += playerXVel * dt;
            playerY += playerYVel * dt;
        }

        if (lives > 0) {
            for (Enemy enemy : enemies) {

                enemy.draw(gc);
                enemy.act();

            }
        }

        if (playerX < 0) {
            playerX = 0;
        }
        if (playerX > 575) {
            playerX = 575;
        }
        if (playerY < 0) {
            playerY = 0;
        }
        if (playerY > 575) {
            playerY = 575;
        }

        deltaTime = System.currentTimeMillis();
        frames ++;
        if (lives == 0 && arcDeg > 0) {
            arcDeg--;
        }
        if (lives == 0 && arcDeg == 0) {
            gc.setFill(Color.BLACK);
            gc.setFont(new Font(75));
            gc.fillText("You Died", 150, 250);
            gc.setFont(new Font(25));
            gc.fillText("Press r to restart", 215,350);
            if (restart == true) {
                lives = 7;
                enemies.clear();
                arcDeg = 360;
                restart = false;
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}