package sprite;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class ScreenElementTest {

    /**
     * Different elements should not be the same.
     */
    @Test
    void testUnique() {
        ScreenElement back = ScreenElement.BACK;
        ScreenElement info = ScreenElement.INFO;
        assertNotEquals(back, info);
    }

    /**
     * Equal elements should be the same.
     */
    @Test
    void testSame() {
        ScreenElement background1 = ScreenElement.BACKGROUND;
        ScreenElement background2 = ScreenElement.BACKGROUND;
        assertEquals(background1, background2);
    }

    /**
     * Assert that an element is not null.
     */
    @Test
    void testNotNull() {
        ScreenElement play = ScreenElement.PLAY;
        assertNotNull(play);
    }

    /**
     * The returned path should not be null.
     */
    @Test
    void testGetPath1() {
        assertNotNull(ScreenElement.RANKING.getPath());
    }

    /**
     * The returned paths should be different for
     * different elements.
     */
    @Test
    void testGetPath2() {
        assertEquals(ScreenElement.PAUSED.getPath(), ScreenElement.PAUSED.getPath());
        assertNotEquals(ScreenElement.RESTART.getPath(), ScreenElement.PLAY.getPath());
    }
}