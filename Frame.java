import javax.swing.*;

public class Frame extends JFrame {

    // constructor
    Frame() {
        this.add(new Panel());
        this.setTitle("SPACE INVADERS");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(null);
    }
}
