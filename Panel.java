import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel implements Runnable {
    // game-config
    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;

    // spaceship
    Spaceship spaceship;


    // constructor
    Panel() {
        // screen-config
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);

        spaceship = new Spaceship(SCREEN_WIDTH / 2, 500);
    }

    // render
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
    }
    private void render(Graphics g) {
        spaceship.render(g);
    }

    // game-loop
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 144.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1) {

                repaint();
            }
        }
    }
}
