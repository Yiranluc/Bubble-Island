package factories;

import com.badlogic.gdx.math.Vector3;
import gameobjects.Bubble;
import gameobjects.BubbleBoard;
import screens.PlayScreen;
import states.Dynamic;

@SuppressWarnings("PMD.NullAssignment")
public class BubbleFactory {

    private transient PlayScreen screen;

    private transient Bubble bubble;
    private transient boolean empty = true;

    private transient float width;
    private transient float height;

    /**
     * Constructor for the BubbleFactory.
     *
     * @param screen the screen for which the factory needs to create the bubbles.
     */
    public BubbleFactory(PlayScreen screen, float width, float height) {
        this.screen = screen;
        this.width = width;
        this.height = height;
    }


    /**
     * Updates the BubbleFactory.
     * Adds the time passed to the timer.
     * Creates a bubble if the bubble factory is empty
     * and the last bubble has been shot at the centerpiece.
     */
    public void update() {
        if (screen.getAngle() == 0 && screen.getShotBubble() == null && empty) {
            bubble = new Bubble(screen.getWorld(), width, height);
            ((BubbleBoard) screen.getBoard()).add(bubble);
            empty = false;
        }
    }

    /**
     * If the bubble factory has a bubble, it shoots that into the direction of given vector.
     *
     * @param cursor the coordinates of where the bubble needs to shoot to.
     */
    public void shootBubble(Vector3 cursor) {
        getBubble().setDirection(cursor.x, cursor.y);
        getBubble().setState(new Dynamic(getBubble()));
        empty = true;
    }

    public boolean isEmpty() {
        return empty;
    }

    public Bubble getBubble() {
        return bubble;
    }

    public void setEmpty() {
        bubble = null;
        empty = true;
    }
}
