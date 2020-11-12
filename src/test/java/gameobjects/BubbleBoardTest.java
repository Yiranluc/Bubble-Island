package gameobjects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sprite.Color;


@SuppressWarnings({"PMD.DataflowAnomalyAnalysis", "PMD.AvoidLiteralsInIfCondition"})
public class BubbleBoardTest {
    private transient World world;
    private static final float dt = 1;


    @BeforeEach
    private void setUp() {
        world = new World(new Vector2(0, 0), true);
    }

    /**
     * Test the storage of the bubbles in the 2d array and in the 1d arraylist
     * with the hexagon of level 0.
     */
    @Test
    public void testLevel0BubbleBoard() {
        BubbleBoard a = new BubbleBoard(world, 0, 300, 400);
        assertEquals(a.getBoard().length, a.getBoardSize());
        int median = a.getMedian();
        assertEquals(a.size(), 7);
        assertEquals(25.0, a.getBoard()[median + 1][median + 0]
                .getB2body().getPosition().x - 300);
        assertEquals((int)(25 * Math.sqrt(3)),
                (int)a.getBoard()[median + 1][median + 0].getB2body()
                        .getPosition().y - 400);
        assertEquals(-50.0, a.getBoard()[median - 1][median + 1]
                .getB2body().getPosition().x - 300);
        assertEquals(0.0, a.getBoard()[median - 1][median + 1]
                .getB2body().getPosition().y - 400);
    }

    /**
     * Test the storage of the bubbles in the 2d array and in the 1d arraylist
     * with the hexagon of level 1.
     */
    @Test
    public void testLevel1BubbleBoard() {
        BubbleBoard a = new BubbleBoard(world, 1, 300, 400);
        assertEquals(a.getBoard().length, a.getBoardSize());
        int median = a.getMedian();
        assertEquals(a.size(), 19);
        assertEquals(50.0, a.getBoard()[median + 2][median + 0]
                .getB2body().getPosition().x - 300);
        assertEquals((int)(50 * Math.sqrt(3)),
                (int)a.getBoard()[median + 2][median + 0]
                        .getB2body().getPosition().y - 400);
        assertEquals(-75.0, a.getBoard()[median - 2][median + 1]
                .getB2body().getPosition().x - 300);
        assertEquals((int)(-25 * Math.sqrt(3) * 10),
                (int)((a.getBoard()[median - 2][median + 1]
                        .getB2body().getPosition().y - 400) * 10));
    }

    /**
     * Test the storage of the bubbles in the 2d array and in the 1d arraylist
     * with the hexagon of level 2.
     */
    @Test
    public void testLevel2BubbleBoard() {
        BubbleBoard a = new BubbleBoard(world, 2, 300, 400);
        int median = a.getMedian();
        assertEquals(a.getBoard().length, a.getBoardSize());
        assertEquals(a.size(), 37);
        assertEquals(75.0, a.getBoard()[median + 3][median + 0]
                .getB2body().getPosition().x - 300);
        assertEquals((int)(75 * Math.sqrt(3)),
                (int)a.getBoard()[median + 3][median + 0]
                        .getB2body().getPosition().y - 400);
        assertEquals(-125.0,
                a.getBoard()[median - 3][median + 2]
                        .getB2body().getPosition().x - 300);
        assertEquals((int) (-25 * Math.sqrt(3) * 10),
                (int) ((a.getBoard()[median - 3][median + 2]
                        .getB2body().getPosition().y - 400) * 10));
    }

    /**
     * Test the storage of the bubbles in the 2d array and in the 1d arraylist
     * with the hexagon of level 3.
     */
    @Test
    public void testLevel3BubbleBoard() {
        BubbleBoard a = new BubbleBoard(world, 3, 300, 400);
        assertEquals(a.getBoard().length, a.getBoardSize());
        int median = a.getMedian();
        assertEquals(a.size(), 61);
        assertEquals(100.0,
                a.getBoard()[median + 4][median + 0]
                        .getB2body().getPosition().x - 300);
        assertEquals((int)(100 * Math.sqrt(3)),
                (int)a.getBoard()[median + 4][median + 0]
                        .getB2body().getPosition().y - 400);
        assertEquals(-125,
                a.getBoard()[median - 4][median + 1]
                        .getB2body().getPosition().x - 300);
        assertEquals((int)(-75 * Math.sqrt(3) * 10),
                (int)((a.getBoard()[median - 4][median + 1]
                        .getB2body().getPosition().y - 400) * 10));
    }

    /**
     * When the level has the number of rows larger than what the current screen size can contain,
     * the current level should go down to what the current screen size can contain.
     * In this case, from level 3 to level 1.
     */
    @Test
    public void testLevelLargerThanScreen() {
        BubbleBoard a = new BubbleBoard(world, 3, 150, 150);
        assertEquals(1, a.getLevel());
        assertEquals(19, a.size());
    }

    /**
     * Test for getClustersOfSameColor function.
     */
    @Test
    public void testGetClustersOfSameColor() {
        BubbleBoard board = new BubbleBoard(world, 0, 300, 400);
        int median = board.getMedian();
        Bubble test = new Bubble(world, 0, 0);
        ArrayList<Bubble> neighbors = new ArrayList<>();
        for (int x = 0; x < board.getBoardSize(); x++) {
            int storageCoordinatex = x - 0 - 1;
            int m = median + storageCoordinatex;
            for (int y = 0; y < board.getBoardSize(); y++) {
                int storageCoordinatey = y - 0 - 1;
                int n = median + storageCoordinatey;
                if (board.getBoard()[m][n] != null) {
                    Bubble current = board.getBoard()[m][n];
                    if (storageCoordinatex == 1 && storageCoordinatey == 0) {
                        test = current;
                    }
                    if (storageCoordinatex == 0 && storageCoordinatey == 1) {
                        neighbors.add(current);
                    }
                    if (storageCoordinatex == 0 && storageCoordinatey == 0) {
                        neighbors.add(current);
                    }
                    if (storageCoordinatex == 1 && storageCoordinatey == -1) {
                        neighbors.add(current);
                    }
                    if (storageCoordinatex == -1) {
                        current.setColor(Color.BLUE);
                    } else if (!(storageCoordinatex == 0
                            && storageCoordinatey == 0)) {
                        current.setColor(Color.RED);
                    }
                }
            }
        }

        board.getClustersOfSameColor();

        // there should be 2 clusters in total according to the setting above.
        assertEquals(3, board.getComponents().size());
        int index2 = 0;
        for (ArrayList<Bubble> cluster : board.getComponents()) {
            if (index2 == 0) {
                for (int i = 0; i < 2; i++) {
                    assertEquals(2, cluster.size());
                    assertEquals(Color.BLUE, cluster.get(i).getColorType());
                }
            }
            if (index2 == 1) {
                for (int i = 0; i < 4; i++) {
                    assertEquals(4, cluster.size());
                    assertEquals(Color.RED, cluster.get(i).getColorType());
                }
            }
            if (index2 == 2) {
                assertEquals(1, cluster.size());
                assertEquals(Color.STAR, cluster.get(0).getColorType());
            }
            index2++;
        }

        // test whether the bubble test finds correct neighbors
        assertEquals(test.getNeighbors().size(), neighbors.size());
        assertTrue(test.getNeighbors().contains(neighbors.get(0)));
        assertTrue(test.getNeighbors().contains(neighbors.get(1)));
        assertTrue(test.getNeighbors().contains(neighbors.get(2)));
    }
}
