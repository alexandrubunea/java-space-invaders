import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Random;


public class Panel extends JPanel implements Runnable {
    // game-config
    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;
    private Thread gameThread;
    private boolean gameOver = false;
    private boolean gameWon = false;

    // spaceship
    Spaceship spaceship;
    private int lives_remaining = 3;

    // enemy
    Enemy enemy;

    // projectiles
    ArrayList<Projectile> projectiles = new ArrayList<>();
    private boolean countdown = false;
    private long lastShootTime;
    private boolean weaponsTooHot = false;
    private int weaponsHeatLevel = 0;
    private int specialProjectiles = 0;

    // random-objects
    ArrayList<RandomObject> randomObjects = new ArrayList<>();


    // constructor
    Panel() {
        // screen-config
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        this.addMouseMotionListener(new MyMouseAdapter());

        spaceship = new Spaceship(SCREEN_WIDTH / 2, 450);
        enemy = new Enemy(200, 40);

        gameThread = new Thread(this);
        gameThread.start();
    }

    // render
    private void renderHeat(Graphics g) {
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
    private void renderHUD(Graphics g) {
        // remaining-lives
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.drawString("REMAINING LIVES: ", 570, 540);
        int location_x = 720;
        g.setColor(Color.RED);
        for(int i = 0; i < lives_remaining; i++) {
            g.fillOval(location_x, 526, 15, 15);
            location_x += 20;
        }
        // special-bullets-remaining
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.drawString("ADDITIONAL BULLETS: " + specialProjectiles, 570, 560);

        // enemy-health
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.drawString("ENEMY HEALTH ", 340, 550);
        g.drawString("" + enemy.getHealth(), 375, 570);
    }
    public void renderGameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("GAME OVER", SCREEN_WIDTH / 2 - 110, SCREEN_HEIGHT / 2 - 110);
        g.setFont(new Font("Arial", Font.BOLD, 16));
    }
    public void renderYouWon(Graphics g) {
        g.setColor(Color.ORANGE);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("YOU WON", SCREEN_WIDTH / 2 - 110, SCREEN_HEIGHT / 2 - 110);
        g.setFont(new Font("Arial", Font.BOLD, 16));
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
    }
    private void render(Graphics g) {
        if(gameWon) {
            renderYouWon(g);
            return;
        }
        if(!gameOver) {
            // random-objects-falling-from-the-sky
            randomObjects.forEach(randomObject -> randomObject.render(g));

            // player
            projectiles.forEach(projectile -> projectile.render(g));
            spaceship.render(g);

            // enemy
            enemy.render(g);

            // horizontal-line
            g.setColor(Color.WHITE);
            g.fillRect(-5, 500, SCREEN_WIDTH + 10, 2);

            renderHeat(g);
            renderHUD(g);
        } else renderGameOver(g);
    }

    // mouse-adapter
    private class MyMouseAdapter extends MouseMotionAdapter {
        @Override
        public void mouseMoved(MouseEvent e) {
            spaceship.move(e.getX(), SCREEN_WIDTH);
        }
    }

    // key-adapter
    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                if(!countdown && !weaponsTooHot) {
                    Projectile newProjectile1 = new Projectile(spaceship.getComponentCenterX(1), spaceship.getComponentCenterY(1));
                    Projectile newProjectile2 = new Projectile(spaceship.getComponentCenterX(2), spaceship.getComponentCenterY(2));

                    projectiles.add(newProjectile1);
                    projectiles.add(newProjectile2);

                    lastShootTime = System.nanoTime();

                    countdown = true;

                    if(specialProjectiles > 0) {
                        newProjectile1 = new Projectile(spaceship.getComponentCenterX(3), spaceship.getComponentCenterY(3));
                        newProjectile2 = new Projectile(spaceship.getComponentCenterX(4), spaceship.getComponentCenterY(4));

                        projectiles.add(newProjectile1);
                        projectiles.add(newProjectile2);

                        weaponsHeatLevel += 2;
                        specialProjectiles -= 2;
                    } else weaponsHeatLevel += 1;

                    if(weaponsHeatLevel >= 100) weaponsTooHot = true;
                }
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
                ArrayList<Projectile> toDelete = new ArrayList<>();
                projectiles.forEach(projectile -> {
                    projectile.move();
                    if(projectile.getCenterY() - projectile.getRadius() / 2 <= 0)
                        toDelete.add(projectile);
                });

                if(lastTime - lastShootTime > 100000000 && countdown) countdown = false;
                if(lastTime - lastShootTime > 1000000000 && weaponsHeatLevel > 0) {
                    weaponsHeatLevel -= 5;
                    lastShootTime = lastTime;
                }
                if(weaponsHeatLevel < 85 && weaponsTooHot) weaponsTooHot = false;

                // check-if-the-enemy-takes-damage
                projectiles.forEach(projectile -> {
                    if(projectile.getCenterX() + projectile.getRadius() / 2 >= enemy.getCenterX() - enemy.getWidth() / 2 &&
                    projectile.getCenterX() - projectile.getRadius() / 2 <= enemy.getCenterX() + enemy.getWidth() / 2 &&
                    projectile.getCenterY() - projectile.getRadius() / 2 <= enemy.getCenterY()) {
                        toDelete.add(projectile);
                        enemy.damage(10);
                        if(enemy.getHealth() <= 0) {
                            gameWon = true;
                            repaint();
                            gameThread.stop();
                        }

                        Random random = new Random();
                        int high = enemy.getCenterX() + enemy.getWidth() / 2 - 15;
                        int low = enemy.getCenterX() - enemy.getWidth() / 2 + 15;
                        int random_x = random.nextInt(high - low) + low;
                        int random_y = enemy.getCenterY(); // not so random
                        int random_type = 1;
                        int chance = random.nextInt(100 - 1) + 1;
                        if(chance < 10) {
                            chance = random.nextInt(100 - 1) + 1;
                            if (chance < 15) {
                                chance = random.nextInt(100 - 1) + 1;
                                if (chance < 50) random_type = 2;
                                else random_type = 3;
                            }
                            RandomObject newObject = new RandomObject(random_x, random_y, random_type);
                            randomObjects.add(newObject);
                        }
                    }
                });

                // delete-useless-projectiles
                toDelete.forEach(projectile -> projectiles.remove(projectile));

                // random-objects-falling-down
                randomObjects.forEach(RandomObject::move);
                ArrayList<RandomObject> toRemove = new ArrayList<>();

                // check-if-player-gets-random-object
                randomObjects.forEach(o -> {
                    int lower_x = spaceship.getLowerX();
                    int higher_x = spaceship.getHigherX();
                    if(o.getCenterX() + o.getRadius() / 2 >= lower_x &&
                    o.getCenterX() - o.getRadius() / 2 <= higher_x &&
                    o.getCenterY() + o.getRadius() / 2 >= spaceship.getCenterY()) {
                        toRemove.add(o);
                        if(o.getType() == 1) {
                            lives_remaining -= 1;
                            if(lives_remaining < 0) {
                                gameOver = true;
                                repaint();
                                gameThread.stop();
                            }
                        }
                        else if(o.getType() == 2) {
                            if(weaponsHeatLevel - 40 < 0) weaponsHeatLevel = 0;
                            else weaponsHeatLevel -= 40;
                        }
                        else if(o.getType() == 3) specialProjectiles += 100;
                    }
                });

                // random-objects-falling-off-the-screen 500
                randomObjects.forEach(o -> {
                    if(o.getCenterY() + o.getRadius() / 2 >= 500) toRemove.add(o);
                });

                // delete-useless-falling-objects
                toRemove.forEach(o -> randomObjects.remove(o));

                repaint();
                delta--;
            }
        }
    }
}
