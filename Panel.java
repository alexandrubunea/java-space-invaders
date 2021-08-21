import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


public class Panel extends JPanel implements Runnable {
    // game-config
    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;
    private Thread gameThread;

    // spaceship
    Spaceship spaceship;

    // projectiles
    ArrayList<Projectile> projectiles = new ArrayList<>();
    private boolean countdown = false;
    private long lastShootTime;
    private boolean weaponsTooHot = false;
    private int weaponsHeatLevel = 0;


    // constructor
    Panel() {
        // screen-config
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());

        spaceship = new Spaceship(SCREEN_WIDTH / 2, 450);

        gameThread = new Thread(this);
        gameThread.start();
    }

    // render
    public void renderHeat(Graphics g) {
        if(!weaponsTooHot) g.setColor(Color.WHITE);
        else {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 10));
            g.drawString("WEAPONS ARE TOO HOT", 30, 580);
        }
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("HEAT LEVEL", 32, 540);

        if(weaponsHeatLevel >= 0) g.setColor(Color.WHITE);
        if(weaponsHeatLevel >= 25) g.setColor(Color.YELLOW);
        if(weaponsHeatLevel >= 50) g.setColor(Color.ORANGE);
        if(weaponsHeatLevel >= 85) g.setColor(Color.RED);

        int last_x = 20;
        for(int i = 0; i < weaponsHeatLevel / 10; i++) {
            g.fillRect(last_x, 550, 10, 20);
            last_x += 15;
        }

        g.setColor(Color.WHITE);
        for(int i = weaponsHeatLevel / 10; i < 10; i++) {
            g.fillRect(last_x, 550, 10, 20);
            last_x += 15;
        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
    }
    private void render(Graphics g) {
        projectiles.forEach(projectile -> projectile.render(g));
        spaceship.render(g);

        // horizontal-line
        g.setColor(Color.WHITE);
        g.fillRect(-5, 500, SCREEN_WIDTH + 10, 2);

        renderHeat(g);
    }

    // key-adapter
    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A -> spaceship.move('l', SCREEN_WIDTH);
                case KeyEvent.VK_D -> spaceship.move('r', SCREEN_WIDTH);
                case KeyEvent.VK_SPACE -> {
                    if(!countdown && !weaponsTooHot) {
                        Projectile newProjectile1 = new Projectile(spaceship.getComponentCenterX(1), spaceship.getComponentCenterY(1));
                        Projectile newProjectile2 = new Projectile(spaceship.getComponentCenterX(2), spaceship.getComponentCenterY(2));

                        projectiles.add(newProjectile1);
                        projectiles.add(newProjectile2);

                        lastShootTime = System.nanoTime();

                        countdown = true;

                        weaponsHeatLevel += 2;
                        if(weaponsHeatLevel >= 100) weaponsTooHot = true;
                    }
                }
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

                // weapons
                projectiles.forEach(Projectile::move);
                if(lastTime - lastShootTime > 100000000 && countdown) countdown = false;
                if(lastTime - lastShootTime > 1000000000 && weaponsHeatLevel > 0) {
                    weaponsHeatLevel -= 5;
                    lastShootTime = lastTime;
                }
                if(weaponsHeatLevel < 85 && weaponsTooHot) weaponsTooHot = false;

                repaint();
                delta--;
            }
        }
    }
}
