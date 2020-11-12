package sprite;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class ColorTest {

    /**
     * Different colors should not be the same.
     */
    @Test
    void testUnique() {
        Color blue = Color.BLUE;
        Color green = Color.GREEN;
        assertNotEquals(blue, green);
    }

    /**
     * Equal colors should be the same.
     */
    @Test
    void testSame() {
        Color pink = Color.PINK;
        Color pink2 = Color.PINK;
        assertEquals(pink, pink2);
    }

    /**
     * Assert that an enum type is not null.
     */
    @Test
    void testNotNull() {
        Color purple = Color.PURPLE;
        assertNotNull(purple);
    }

    /**
     * The random method should return a color.
     */
    @Test
    void testRandom() {
        Color color = Color.random();
        assertNotNull(color);
    }

    /**
     * The returned path should not be null.
     */
    @Test
    void testGetPath1() {
        assertNotNull(Color.RED.getPath());
    }

    /**
     * The returned paths should be different for
     * different colors.
     */
    @Test
    void testGetPath2() {
        assertEquals(Color.RED.getPath(), Color.RED.getPath());
        assertNotEquals(Color.RED.getPath(), Color.BLUE.getPath());
    }
}