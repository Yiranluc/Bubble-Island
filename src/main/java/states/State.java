package states;

import gameobjects.Bubble;

/**
 * Interface for the State of a Bubble.
 */
public interface State {

    /**
     * Method to update the Bubble, according to its State.
     */
    void update(float dt);

    Bubble getBubble();
}
