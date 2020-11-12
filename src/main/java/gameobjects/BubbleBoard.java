package gameobjects;

import com.badlogic.gdx.physics.box2d.World;
import iterators.BoardIterator;
import java.util.ArrayList;
import java.util.Iterator;
import sprite.Color;

/**
 * The hexagon structure in the center of the game screen.
 */
@SuppressWarnings({"PMD.AvoidLiteralsInIfCondition", "PMD.DataflowAnomalyAnalysis"})
public class BubbleBoard implements Iterable<Bubble> {
    private transient World world;
    private static final float RADIUS =  Bubble.getRadius();
    // the current level of the game.
    private transient int level;
    // the number of rows we can have at maximum according to current screen size.
    private transient int maxNumber;
    // the middle of the x axis of the screen.
    private transient int offsetX;
    // the middle of the y axis of the screen.
    private transient int offsetY;
    // the number of rows according to current level.
    private transient int numberOfRow;
    // the size for the bubbleBoard we have according to the current screen size and level.
    private transient int boardSize;
    // the median of size of the 2d array board.
    private transient int median;

    //Storing board bubbles in a 2d array allows for a easy checking of the neighbors of a bubble.
    private transient Bubble[][] board;
    //A simple one dimensional arraylist allows for the easy adding of a new collided bubble.
    private transient ArrayList<Bubble> bubbles;
    // components stores all the clusters of the same color when a new board gets initialized.
    private transient ArrayList<ArrayList<Bubble>> components = new ArrayList<ArrayList<Bubble>>();

    /**
     * Initialisation of a new bubbleBoard.
     * Setting the hexagon to the center of the screen still needs to be integrated with
     * other classes such as BubbleSpinner class.
     * @param world the world which the bubble board is in.
     * @param level the current level of the game, which can range from 1 to 3
     *              with corresponding sizes of the board.
     * @param offsetX to set the hexagon center at the center of the screen.
     * @param offsetY to set the hexagon center at the center of the screen.
     */
    public BubbleBoard(World world, int level, int offsetX, int offsetY)
            throws IndexOutOfBoundsException {

        this.world = world;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.level = level;
        this.maxNumber = Math.min((int)(offsetX / RADIUS), (int)(offsetY / RADIUS));
        this.numberOfRow = 3 + 2 * this.level;
        if (maxNumber < numberOfRow) {
            this.level = (int) (maxNumber - 3) / 2;
            this.numberOfRow = 3 + 2 * this.level;
        }
        this.boardSize = numberOfRow;
        this.median = (numberOfRow + 1) / 2 - 1;
        board = new Bubble[boardSize][boardSize];
        bubbles = new ArrayList<>();
        defineBoard(world);
    }

    /**
     * Define the bubbleBoard GUI and store each bubble into the 2d bubble array.
     * @param world the world which the bubble board is in.
     */

    private void defineBoard(World world) {
        int median = (boardSize + 1) / 2 - 1;
        for (int m = 0; m < boardSize; m++) {
            int storageCoordinatex = m - level - 1;
            int bubblesOnThatRow = boardSize - Math.abs(storageCoordinatex);

            for (int n = 0; n < bubblesOnThatRow; n++) {
                int storageCoordinatey;
                if (storageCoordinatex < 0) {
                    storageCoordinatey = level + 1 - n;
                } else {
                    storageCoordinatey = n - level - 1;
                }
                float screenCoordinatex = (float)storageCoordinatex / 2
                        - (float)storageCoordinatey / 2;
                float screenCoordinatey = (float) Math.sqrt(3) / 2
                        *  (storageCoordinatex + storageCoordinatey);
                // spare the center piece space for the Star.
                Bubble a;
                if (screenCoordinatex == 0 && screenCoordinatey == 0) {
                    a = new Star(world, 2 * screenCoordinatex * RADIUS + offsetX,
                            screenCoordinatey * RADIUS * 2 + offsetY);
                } else {
                    a = new Bubble(world, 2 * screenCoordinatex * RADIUS + offsetX,
                            screenCoordinatey * RADIUS * 2 + offsetY);
                }


                board[median + storageCoordinatex][median + storageCoordinatey] = a;
                bubbles.add(a);

            }
        }

    }

    /**
     * Find the clusters of bubbles in the board which are of the same color.
     */
    public void getClustersOfSameColor() {

        // traverse each bubble in the bubble board to update its chains.
        for (int x = 0; x < boardSize; x++) {
            int storageCoordinatex = x - level - 1;
            int m = median + storageCoordinatex;
            for (int y = 0; y < boardSize; y++) {
                int storageCoordinatey = y - level - 1;
                int n = median + storageCoordinatey;
                if (board[m][n] != null) {
                    Bubble current = board[m][n];
                    ArrayList<Bubble> component = current.getChain();
                    component = checkNeighbors(current, component, m, n);
                    // update the inComponent of each bubble in the cluster
                    for (Bubble member : component) {
                        member.setChain(component);
                    }
                }
            }
        }

        // traverse each bubble in the board to find different same-color clusters
        // in the bubble board.
        for (int x = 0; x < boardSize; x++) {
            int storageCoordinatex = x - level - 1;
            int m = median + storageCoordinatex;
            for (int y = 0; y < boardSize; y++) {
                int storageCoordinatey = y - level - 1;
                int n = median + storageCoordinatey;
                if (board[m][n] != null) {
                    Bubble current = board[m][n];
                    if (!components.contains(current.getChain())) {
                        components.add(current.getChain());
                    }
                }
            }
        }
    }

    public void add(Bubble bubble) {
        bubbles.add(bubble);
    }

    public boolean isEmpty() {
        return bubbles.isEmpty();
    }

    public Iterator<Bubble> iterator() {
        return new BoardIterator(getBubbles());
    }

    /**
     * Check the six neighbors of a bubble whether they are with the same color of the bubble.
     * And add the neighbors to the 'neighbors' instance of the current bubble.
     * @param current the current bubble to check for its neighbors
     * @param component current cluster of bubbles of the same color.
     * @param m the index of the storage of a bubble in the 2d array board.
     * @param n the index of the storage of a bubble in the 2d array board.
     * @return the updated cluster of bubbles of the same color after checking current
     *         bubble's neighbors.
     */
    public ArrayList<Bubble> checkNeighbors(Bubble current, ArrayList<Bubble> component,
                                            int m, int n) {
        if ((m + 1) < boardSize && board[m + 1][n] != null) {
            Bubble a = board[m + 1][n];
            current.getNeighbors().add(a);
            addBubbleIntoComponent(current.getColorType(), a, component);
        }

        if ((m - 1) >= 0 && board[m - 1][n] != null) {
            Bubble a = board[m - 1][n];
            current.getNeighbors().add(a);
            addBubbleIntoComponent(current.getColorType(), a, component);
        }

        if ((n + 1) < boardSize && board[m][n + 1] != null) {
            Bubble a = board[m][n + 1];
            current.getNeighbors().add(a);
            addBubbleIntoComponent(current.getColorType(), a, component);
        }

        if ((n - 1) >= 0 && board[m][n - 1] != null) {
            Bubble a = board[m][n - 1];
            current.getNeighbors().add(a);
            addBubbleIntoComponent(current.getColorType(), a, component);
        }

        if ((n - 1) >= 0 && (m + 1) < boardSize && board[m + 1][n - 1] != null) {
            Bubble a = board[m + 1][n - 1];
            current.getNeighbors().add(a);
            addBubbleIntoComponent(current.getColorType(), a, component);
        }

        if ((m - 1) >= 0 && (n + 1) < boardSize && board[m - 1][n + 1] != null) {
            Bubble a = board[m - 1][n + 1];
            current.getNeighbors().add(a);
            addBubbleIntoComponent(current.getColorType(), a, component);
        }
        return component;
    }

    private void addBubbleIntoComponent(Color color, Bubble a, ArrayList<Bubble> component) {
        if (color.equals(a.getColorType())) {
            if (a.getChain().size() == 0) {
                component.add(a);
            } else {
                for (Bubble bubble: a.getChain()) {
                    if (!component.contains(bubble)) {
                        component.add(bubble);
                    }
                }
            }
        }
    }

    /**
     * rotates the board slowly every frame.
     *
     * @param angle the amount of rotation every frame.
     */
    public void rotateSlow(float angle) {
        Iterator<Bubble> boardIterator = bubbles.iterator();

        while (boardIterator.hasNext()) {

            if (angle > 0) {

                boardIterator.next().rotateWithBoard(0.5f,
                        offsetX,
                        offsetY);

            } else if (angle < 0) {
                boardIterator.next().rotateWithBoard(-0.5f,
                        offsetX,
                        offsetY);
            }

        }
    }

    private ArrayList<Bubble> getBubbles() {
        return bubbles;
    }

    public Bubble[][] getBoard() {
        return board;
    }

    public int size() {
        return getBubbles().size();
    }

    public ArrayList<ArrayList<Bubble>> getComponents() {
        return components;
    }

    public int getLevel() {
        return level;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public int getMedian() {
        return median;
    }
}
