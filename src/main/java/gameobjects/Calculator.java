package gameobjects;

/**
 * The Calculator was created to move the logic of calculating
 * a time bonus out of PlayScreen, since that class consists
 * of mainly GUI and is therefore not tested with unit tests.
 * It can be used by the GUI screens to do calculations.
 */
public class Calculator {


    /**
     * Basic constructor.
     */
    public Calculator() {
    }

    /**
     * Method to calculate the time bonus according to the level.
     * @param level the current level of the game
     * @param time the time in seconds it took to win the level.
     * @return the time bonus earned by the player.
     */
    public int calculateTimeBonus(int level, int time) {
        int bonus = ((300 * level) - time) / 2;
        return Math.max(0, bonus);
    }
}
