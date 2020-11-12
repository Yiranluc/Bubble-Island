package sprite;


/**
 * This enum class provides file paths to different graphical screen elements.
 */
public enum ScreenElement {

    BACK("assets/buttons/prev.png"),
    BACKGROUND("assets/background/island.jpg"),
    GENERAL_SCREEN("assets/displays/general.png"),
    INFO("assets/buttons/about.png"),
    NAME("assets/background/name.png"),
    PAUSED("assets/buttons/pause.png"),
    PAUSED_SCREEN("assets/displays/pause-screen.png"),
    RANKING("assets/buttons/ranking.png"),
    RESTART("assets/buttons/restart.png"),
    PLAY("assets/buttons/play.png");

    private transient String path;

    /**
     * Basic constructor method which instantiates
     * the path field.
     *
     * @param path path to the corresponding image.
     */
    ScreenElement(String path) {
        this.path = path;
    }

    /**
     * Getter method to retrieve the path.
     *
     * @return path attribute of the class.
     */
    public String getPath() {
        return this.path;
    }
}
