package myTestGame.game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class gameWindow extends JFrame {

    private static gameWindow game_Window;
    private static long lastFrameTime;
    private static Image backGround;
    private static Image gameOver;
    private static Image drop;
    private static float drop_left = 200;
    private static float drop_top = -100;
    private static float dropV = 200;
    private static int score;


    public static void main(String[] args) throws IOException {
        backGround = ImageIO.read(gameWindow.class.getResourceAsStream("background.png"));
        drop = ImageIO.read(gameWindow.class.getResourceAsStream("drop.png"));
        gameOver = ImageIO.read(gameWindow.class.getResourceAsStream("game_over.png"));
        game_Window = new gameWindow();
        game_Window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game_Window.setLocation(200, 100);
        game_Window.setSize(906, 478);
        game_Window.setResizable(false);
        lastFrameTime = System.nanoTime();
        gameField game_Field = new gameField();
        game_Field.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                float drop_right = drop_left + drop.getWidth(null);
                float drop_bottom = drop_top + drop.getHeight(null);
                boolean is_drop = x >= drop_left && x <= drop_right && y >= drop_top && y <= drop_bottom;
                if (is_drop) {
                    drop_top = -100;
                    drop_left = (int) (Math.random() * (game_Field.getWidth() - drop.getWidth(null)));
                    dropV = dropV + 20;
                    score++;
                    game_Window.setTitle("Score: " + score);
                }
            }
        });
        game_Window.add(game_Field);
        game_Window.setVisible(true);

    }

    private static void onRepaint(Graphics g) {
        long currentTime = System.nanoTime();
        float deltaTime = (currentTime - lastFrameTime) * 0.000000001f;
        lastFrameTime = currentTime;

        drop_top = drop_top + dropV * deltaTime;

        g.drawImage(backGround, 0, 0, null);
        g.drawImage(drop, (int) drop_left, (int) drop_top, null);
        if (drop_top > game_Window.getHeight()) g.drawImage(gameOver, 280, 120, null);

    }

    private static class gameField extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            onRepaint(g);
            repaint();
        }
    }
}

