package states;

import com.badlogic.gdx.math.Vector2;
import gameobjects.Bubble;

/**
 * The Dynamic State allows a floating Bubble to move.
 */
public class Dynamic implements State {
    // Time after which a floating Bubble expires
    private static final float maxAge = 8;
    private transient float activeBubbleAge = 0;

    private transient Bubble bubble;

    /**
     * Basic constructor.
     * @param bubble The Bubble on which this state is applied.
     */
    public Dynamic(Bubble bubble) {
        this.bubble = bubble;
        if (!bubble.getB2body().getFixtureList().isEmpty()) {
            bubble.getB2body().getFixtureList().get(0).setUserData("dynamic");
        } else {
            System.out.println("Bubble without a fixture detected");
        }
    }

    /**
     * Updates the Bubble by moving it and setting its position.
     */
    @Override
    public void update(float dt) {
        activeBubbleAge += dt;

        //if the bubble gets too old its destroyed and the timer is reset.
        if (activeBubbleAge >= maxAge) {
            bubble.noReward();
            bubble.remove();
        }

        move(dt);
        bubble.updatePosition();
    }

    /**
     * Moves the Bubble according to its direction with dt determining how far to move.
     * @param dt time since previous call to move.
     */
    private void move(float dt) {
        Vector2 vec = new Vector2(bubble.getB2body().getPosition().x + bubble.getxdir() * dt,
                bubble.getB2body().getPosition().y + bubble.getydir() * dt);
        bubble.getB2body().setTransform(vec.x, vec.y, 0);
    }

    /**
     * Getter for the Bubble attribute.
     * @return The current Bubble.
     */
    public Bubble getBubble()  {
        return this.bubble;
    }
}
