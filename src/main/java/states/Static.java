package states;

import gameobjects.Bubble;

/**
 * The Static State applies to fixed Bubbles, that are attached to the BubbleBoard.
 */
public class Static implements State {

    private transient Bubble bubble;

    /**
     * Basic constructor.
     * @param bubble The Bubble on which this state is applied.
     */
    public Static(Bubble bubble) {
        this.bubble = bubble;
        if (!bubble.getB2body().getFixtureList().isEmpty()) {
            bubble.getB2body().getFixtureList().get(0).setUserData("static");
        } else {
            System.out.println("Bubble without a fixture detected");
        }
    }

    /**
     * Updates the position of the Bubble.
     */
    @Override
    public void update(float dt) {
        bubble.updatePosition();
    }

    /**
     * Getter for the Bubble attribute.
     * @return The current Bubble.
     */
    public Bubble getBubble() {
        return this.bubble;
    }
}
