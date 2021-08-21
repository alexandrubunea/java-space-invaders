import java.awt.*;

public class Projectile {
    // config
    private final int x;
    private int y;
    private int centerY;
    private final int centerX;

    private final static int Y_VELOCITY = 4;
    private final static int PROJECTILE_RADIUS = 10;

    // constructor
    Projectile(int x, int y) {
        this.x = x - PROJECTILE_RADIUS / 2;
        this.y = y - PROJECTILE_RADIUS / 2;

        this.centerX = this.x + PROJECTILE_RADIUS / 2;
        this.centerY = this.y + PROJECTILE_RADIUS / 2;
    }

    public void move() {
        this.y -= Y_VELOCITY;
        this.centerY = this.y + PROJECTILE_RADIUS / 2;
    }

    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(this.x, this.y, PROJECTILE_RADIUS, PROJECTILE_RADIUS);
    }

    // fetch-values
    public int getCenterY() { return this.centerY; }
    public int getCenterX() { return this.centerX; }
    public int getRadius() { return PROJECTILE_RADIUS; }
}
