package gameobjects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import states.Dynamic;
import states.State;
import states.Static;



class BubbleTest {
    private transient Bubble bubble;
    private transient World world;
    private transient String xflaw = "The x location is invalid";
    private transient String yflaw = "The y location is invalid";
    private static final float X = 50;
    private static final float Y = 300;
    private static final float dt = 1;


    @BeforeEach
    private void setUp() {
        world = new World(new Vector2(0, 0), true);
        bubble = new Bubble(world, X, Y);
    }

    @Test
    void constructorTest1() {
        float x = 0;
        float y = 0;
        Bubble bubble = new Bubble(world, x, y);
        bubble.update(dt);
        assertEquals(x - (bubble.getWidth() / 2), bubble.getX(), xflaw);
        assertEquals(y - (bubble.getHeight() / 2), bubble.getY(), yflaw);
        assertNotNull(bubble.getColorType());
    }

    @Test
    void constructorTest2() {
        float x = 30;
        float y = 90;
        Bubble bubble = new Bubble(world, x, y);
        bubble.update(dt);
        assertEquals(x - (bubble.getWidth() / 2), bubble.getX(), xflaw);
        assertEquals(y - (bubble.getHeight() / 2), bubble.getY(), yflaw);
    }

    @Test
    void constructorTest3() {
        float x = 45;
        float y = 80;
        Bubble bubble = new Bubble(world, x, y);
        bubble.update(dt);
        assertNotEquals(y - (bubble.getHeight() / 2), bubble.getX(), xflaw);
        assertNotEquals(x - (bubble.getWidth() / 2), bubble.getY(), yflaw);
    }

    /**
     * Test the rotationWithBoard method with a positive angle
     * input larger than 360 degrees.
     */
    @Test
    void rotationWithBoardTestWithLargePositiveInput() {
        bubble.rotateWithBoard(450, 200, 300);
        assertEquals(199, (int)bubble.getB2body().getPosition().x);
        assertEquals(150, (int)bubble.getB2body().getPosition().y);
        assertEquals(450,bubble.getRotation());
    }

    /**
     * Test the rotationWithBoard method with a positive angle
     * input smaller than 360 degrees.
     */
    @Test
    void rotationWithBoardTestWithSmallPositiveInput() {
        bubble.rotateWithBoard(60, 200, 300);
        assertEquals(125, (int)bubble.getB2body().getPosition().x);
        assertEquals(170, (int)bubble.getB2body().getPosition().y);
        assertEquals(60,bubble.getRotation());
    }

    /**
     * Test the rotationWithBoard method with a negative angle
     * input which is smaller than -360 degrees.
     */
    @Test
    void rotationWithBoardTestWithLargeNegativeInput() {
        bubble.rotateWithBoard(-450, 200, 300);
        assertEquals(199, (int)bubble.getB2body().getPosition().x);
        assertEquals(450, (int)bubble.getB2body().getPosition().y);
        assertEquals(-450,bubble.getRotation());
    }

    /**
     * Test the rotationWithBoard method with a negative angle
     * input which is larger than -360 degrees.
     */
    @Test
    void rotationWithBoardTestWithSmallNegativeInput() {
        bubble.rotateWithBoard(-420, 200, 300);
        assertEquals(124, (int)bubble.getB2body().getPosition().x);
        assertEquals(429, (int)bubble.getB2body().getPosition().y);
        assertEquals(-420,bubble.getRotation());
    }

    /**
     * Test the rotationWithBoard method with 0 degree input.
     */
    @Test
    void rotationWithBoardTestWith0Degree() {
        bubble.rotateWithBoard(0, 200,300);
        assertEquals(50, (int)bubble.getB2body().getPosition().x);
        assertEquals(300, (int)bubble.getB2body().getPosition().y);
        assertEquals(0,bubble.getRotation());
    }

    /**
     * Tests state transitions.
     */
    @Test
    void staticDynamicTest() {
        assert (bubble.getState() instanceof Static);
        assertEquals(bubble, bubble.getState().getBubble());
        assertFalse(bubble.getState() instanceof Dynamic);

        State state = new Dynamic(bubble);
        bubble.setState(state);

        assertEquals(bubble, bubble.getState().getBubble());
        assertEquals(state, bubble.getState());
        assertFalse(bubble.getState() instanceof Static);
    }

    /**
     * A static Bubble should not move.
     */
    @Test
    void updateStaticTest() {
        bubble.update(dt);
        bubble.setDirection(0f,0f);
        float x = bubble.getX();
        float y = bubble.getY();
        bubble.update(dt);

        assertEquals(x, bubble.getX());
        assertEquals(y, bubble.getY());
    }

    /**
     * A dynamic Bubble should move.
     */
    @Test
    void updateDynamicTest() {
        bubble.setState(new Dynamic(bubble));
        float x = bubble.getX();
        float y = bubble.getY();
        bubble.update(dt);

        assertNotEquals(x, bubble.getX());
        assertNotEquals(y, bubble.getY());
    }

    /**
     * we manually set 6 neighbors of a bubble, and test whether the method works in a good order.
     */
    @Test
    void testeliminateBubbleInNeighbors() {
        Bubble neighbor1 = new Bubble(world, 0, 0);
        neighbor1.getNeighbors().add(bubble);
        Bubble neighbor2 = new Bubble(world, 0, 0);
        neighbor2.getNeighbors().add(bubble);
        Bubble neighbor3 = new Bubble(world, 0, 0);
        neighbor3.getNeighbors().add(bubble);
        Bubble neighbor4 = new Bubble(world, 0, 0);
        neighbor4.getNeighbors().add(bubble);
        Bubble neighbor5 = new Bubble(world, 0, 0);
        neighbor5.getNeighbors().add(bubble);
        Star neighbor6 = new Star(world, 0, 0);
        neighbor6.getNeighbors().add(bubble);
        ArrayList<Bubble>  neighbors = new ArrayList<>();
        neighbors.add(neighbor1);
        neighbors.add(neighbor2);
        neighbors.add(neighbor3);
        neighbors.add(neighbor4);
        neighbors.add(neighbor5);
        neighbors.add(neighbor6);
        bubble.setNeighbors(neighbors);
        bubble.eliminateBubbleInNeighbors();
        assertEquals(0, neighbor1.getNeighbors().size());
        assertEquals(0, neighbor2.getNeighbors().size());
        assertEquals(0, neighbor3.getNeighbors().size());
        assertEquals(0, neighbor4.getNeighbors().size());
        assertEquals(0, neighbor5.getNeighbors().size());
        assertEquals(0, neighbor6.getNeighbors().size());
    }

    /**
     * When there a bubble has no neighbor, it is not connected to the center of the board.
     */
    @Test
    void checkConnectedToCenter0Neighbor() {
        assertEquals(false, bubble.checkConnectedToCenter());
    }

    /**
     * If the neighbor or the bubble itself can not reach the starfish,
     * the bubble is not connected to the center of the board.
     */
    @Test
    void checkConnectedToCenter1Neighbor() {
        Bubble neighbor1 = new Bubble(world, 0, 0);
        neighbor1.getNeighbors().add(bubble);
        bubble.getNeighbors().add(neighbor1);
        assertEquals(false, bubble.checkConnectedToCenter());
    }

    /**
     * Manually set up 2 neighbors for a bubble, to
     * check whether the bubble is connected to the star.
     */
    @Test
    void checkConnectedToCenter2Neighbors() {
        Star star = new Star(world, 0, 0);
        Bubble neighbor1 = new Bubble(world, 0, 0);
        Bubble neighbor2 = new Bubble(world, 0, 0);

        star.getNeighbors().add(neighbor1);
        neighbor1.getNeighbors().add(star);
        neighbor1.getNeighbors().add(bubble);
        neighbor2.getNeighbors().add(bubble);
        bubble.getNeighbors().add(neighbor1);
        bubble.getNeighbors().add(neighbor2);
        assertEquals(true, bubble.checkConnectedToCenter());
    }

}