import java.awt.*;

public class SpaceshipComponent {
    // config
    private int x;
    private int centerX;

    private final int y;
    private final int centerY;

    private final int width;
    private final int height;

    // constructor
    SpaceshipComponent(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.centerX = this.x + this.width / 2;
        this.centerY = this.y + this.height / 2;
    }

    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(this.x, this.y, this.width, this.height);

        g.setColor(Color.RED);
        g.drawRect(this.x, this.y, this.width, this.height);

        g.setColor(Color.BLUE);
        g.fillOval(this.centerX - 5/2, this.centerY - 5/2, 5, 5);
    }

    public void move(int velocity) {
        this.x = this.x + velocity;
        this.centerX = this.x + this.width / 2;
    }

    // fetch-values
    public int getCenterX() { return this.centerX; }
    public int getWidth() { return this.width; }
}
