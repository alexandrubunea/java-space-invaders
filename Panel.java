import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel implements Runnable {
    // game-config
    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;


    // constructor
    Panel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
    }


    // game-loop
    @Override
    public void run() {

    }
}
