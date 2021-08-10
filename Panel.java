import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class Panel extends JPanel implements Runnable {
    // game-config
    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;
    private Thread gameThread;

    // spaceship
    Spaceship spaceship;


    // constructor
    Panel() {
        // screen-config
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());

        spaceship = new Spaceship(SCREEN_WIDTH / 2, 500);

        gameThread = new Thread(this);
        gameThread.start();
    }

    // render
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
    }
    private void render(Graphics g) {
        spaceship.render(g);
    }

    // key-adapter
    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A -> spaceship.move('l', SCREEN_WIDTH);
                case KeyEvent.VK_D -> spaceship.move('r', SCREEN_WIDTH);
            }
        }
        @Override
        public void keyReleased(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_A, KeyEvent.VK_D -> spaceship.move('s', SCREEN_WIDTH);
            }
        }
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
