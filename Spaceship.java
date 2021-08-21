import java.awt.*;

public class Spaceship {
    // config
    private int x;
    private final int y;

    // components
    private static final int SCALE_MULTIPLY = 3;

    private static final int COMPONENTS = 5;

    private static final int MAIN_COMPONENT_WIDTH = 6 * SCALE_MULTIPLY;
    private static final int MAIN_COMPONENT_HEIGHT = 10 * SCALE_MULTIPLY;

    private static final int PRIMARY_WEAPON_COMPONENT_WIDTH = 10 * SCALE_MULTIPLY;
    private static final int PRIMARY_WEAPON_COMPONENT_HEIGHT = 20 * SCALE_MULTIPLY;

    private static final int SECONDARY_WEAPON_COMPONENT_WIDTH = 5 * SCALE_MULTIPLY;
    private static final int SECONDARY_WEAPON_COMPONENT_HEIGHT = 8 * SCALE_MULTIPLY;


    SpaceshipComponent[] components = new SpaceshipComponent[COMPONENTS];

    // constructor
    Spaceship(int x, int y) {
        this.x = x;
        this.y = y;

        // center-of-the-space-ship
        components[0] = new SpaceshipComponent(this.x - MAIN_COMPONENT_WIDTH / 2, this.y - MAIN_COMPONENT_HEIGHT / 2, MAIN_COMPONENT_WIDTH, MAIN_COMPONENT_HEIGHT);

        // primary-weapons-of-the-ship
        components[1] = new SpaceshipComponent(this.x - MAIN_COMPONENT_WIDTH / 2 - PRIMARY_WEAPON_COMPONENT_WIDTH, this.y - PRIMARY_WEAPON_COMPONENT_HEIGHT / 2, PRIMARY_WEAPON_COMPONENT_WIDTH, PRIMARY_WEAPON_COMPONENT_HEIGHT);
        components[2] = new SpaceshipComponent(this.x + MAIN_COMPONENT_WIDTH / 2, this.y - PRIMARY_WEAPON_COMPONENT_HEIGHT / 2, PRIMARY_WEAPON_COMPONENT_WIDTH, PRIMARY_WEAPON_COMPONENT_HEIGHT);

        // secondary-weapons-of-the-ship
        components[3] = new SpaceshipComponent(this.x - MAIN_COMPONENT_WIDTH / 2 - PRIMARY_WEAPON_COMPONENT_WIDTH - SECONDARY_WEAPON_COMPONENT_WIDTH, this.y - SECONDARY_WEAPON_COMPONENT_HEIGHT / 2, SECONDARY_WEAPON_COMPONENT_WIDTH, SECONDARY_WEAPON_COMPONENT_HEIGHT);
        components[4] = new SpaceshipComponent(this.x + MAIN_COMPONENT_WIDTH / 2 + PRIMARY_WEAPON_COMPONENT_WIDTH, this.y - SECONDARY_WEAPON_COMPONENT_HEIGHT / 2, SECONDARY_WEAPON_COMPONENT_WIDTH, SECONDARY_WEAPON_COMPONENT_HEIGHT);


    }

    public void render(Graphics g) {
        for(SpaceshipComponent component : components)
            component.render(g);
    }

    public void move(int to_x, int RIGHT_LIMIT) {
        if(to_x - PRIMARY_WEAPON_COMPONENT_WIDTH - SECONDARY_WEAPON_COMPONENT_WIDTH > 0 && to_x + MAIN_COMPONENT_WIDTH + PRIMARY_WEAPON_COMPONENT_WIDTH + components[4].getWidth() / 2 < RIGHT_LIMIT) {
            components[0].move(to_x);
            components[1].move(to_x - PRIMARY_WEAPON_COMPONENT_WIDTH);
            components[2].move(to_x + MAIN_COMPONENT_WIDTH);
            components[3].move(to_x - PRIMARY_WEAPON_COMPONENT_WIDTH - SECONDARY_WEAPON_COMPONENT_WIDTH);
            components[4].move(to_x + MAIN_COMPONENT_WIDTH + PRIMARY_WEAPON_COMPONENT_WIDTH);
        }
    }

    // fetch-values
    public int getComponentCenterX(int component_id) { return components[component_id].getCenterX(); }
    public int getComponentCenterY(int component_id) { return components[component_id].getCenterY(); }
    public int getLowerX() { return components[3].getCenterX() - components[3].getWidth() / 2; }
    public int getHigherX() { return components[4].getCenterX() + components[4].getWidth() / 2; }
    public int getCenterY() { return components[0].getCenterY(); }
}
