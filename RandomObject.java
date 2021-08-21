import java.awt.*;

public class RandomObject {
    // config
    private final int x;
    private int y;
    private final int centerX;
    private int centerY;
    private final int type;
    private final static int RADIUS = 20;
    private final static int Y_VELOCITY = 2;

    // constructor
    RandomObject(int x, int y, int type) {
        this.x = x;
        this.y = y;

        this.type = type;

        this.centerX = this.x + RADIUS / 2;
        this.centerY = this.y + RADIUS / 2;
    }

    public void render(Graphics g) {
        if(this.type == 1) g.setColor(Color.RED);
        else if(this.type == 2) g.setColor(Color.CYAN);
        else if(this.type == 3) g.setColor(Color.GREEN);

        g.fillOval(this.x, this.y, RADIUS, RADIUS);
    }

    public void move() {
        this.y += Y_VELOCITY;
        this.centerY = this.y + RADIUS / 2;
    }

    // fetch-values
    public int getCenterX() { return this.centerX; }
    public int getCenterY() { return this.centerY; }
    public int getRadius() { return RADIUS; }
    public int getType() { return this.type; }

}
