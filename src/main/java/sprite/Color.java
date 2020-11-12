package sprite;

import java.security.SecureRandom;

/**
 * This enum provides different colors
 * to the Bubble class.
 */
public enum Color {

    BLUE("assets/bubbles/blue.png"),
    GREEN("assets/bubbles/green.png"),
    PINK("assets/bubbles/pink.png"),
    PURPLE("assets/bubbles/purple.png"),
    RED("assets/bubbles/red.png"),
    STAR("assets/gameObjects/star.png");

    private transient String path;

    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * Constructor method.
     *
     * @param path path to the corresponding sprite.
     */
    Color(String path) {
        this.path = path;
    }

    /**
     * Picks a random color.
     *
     * @return color that is randomly chosen.
     */
    public static Color random() {
        return Color.values()[RANDOM.nextInt(Color.values().length - 1)];
    }

    /**
     * Getter method.
     *
     * @return the path of the instance.
     */
    public String getPath() {
        return this.path;
    }

}
