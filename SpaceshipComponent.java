import java.awt.*;

public class SpaceshipComponent {
    // config
    private int x;
    private final int y;
    private final int width;
    private final int height;

    // constructor
    SpaceshipComponent(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(this.x, this.y, this.width, this.height);

        g.setColor(Color.RED);
        g.drawRect(this.x, this.y, this.width, this.height);
    }
}
