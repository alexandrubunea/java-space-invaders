import java.awt.*;

public class Enemy {
    // config
    private final int x;
    private final int y;
    private final int centerX;
    private final int centerY;
    private int health = 10000;

    private static final int WIDTH = 400;
    private static final int HEIGHT = 150;

    // constructor
    Enemy(int x, int y) {
        this.x = x;
        this.y = y;

        this.centerX = this.x + WIDTH / 2;
        this.centerY = this.y + HEIGHT / 2;
    }

    public void render(Graphics g) {
        if(health <= 0) return;
        g.setColor(Color.GRAY);
        g.fillOval(this.x, this.y, WIDTH, HEIGHT);
        g.setColor(Color.GREEN);
        g.drawOval(this.x + 10, this.y + 10, WIDTH - 20, HEIGHT - 20);
        g.setColor(Color.CYAN);
        g.fillOval(this.x + 100, this.y, WIDTH - 200, HEIGHT - 50);
    }

    public void damage(int damage_amount) {
        this.health -= damage_amount;
    }

    // fetch-values
    public int getCenterX() { return this.centerX; }
    public int getCenterY() { return this.centerY; }
    public int getWidth() { return WIDTH; }
    public int getHealth() { return this.health; }
}
