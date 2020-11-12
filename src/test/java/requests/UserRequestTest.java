package requests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public abstract class UserRequestTest {

    protected static String USERNAME = "Cheyenne";
    protected static String PASSWORD = "Secret";
    protected static String TEST = "Test";

    protected transient UserRequest request;

    /**
     * Initialize the request to be used in the tests below.
     */
    @BeforeEach
    public void initialize() {
        request = new SignUpRequest(USERNAME, PASSWORD);
    }


    /**
     * Tests the constructor and getter for the
     * username attribute.
     */
    @Test
    public void testGetUsername() {
        assertEquals(USERNAME, request.getUsername());
    }

    /**
     * Tests the constructor and getter for the
     * password attribute.
     */
    @Test
    public void testGetPassword() {
        assertEquals(PASSWORD, request.getPassword());
    }

    /**
     * Tests the setter for the username attribute.
     */
    @Test
    public void testSetUsername() {
        request.setUsername(TEST);
        assertEquals(TEST, request.getUsername());
    }


    /**
     * Tests the setter for the password attribute.
     */
    @Test
    public void testSetPassword() {
        request.setPassword(TEST);
        assertEquals(TEST, request.getPassword());
    }

    /**
     * Tests the isEmpty method.
     * Verifies that is returns true when the username is empty.
     */
    @Test
    public void testEmptyName() {
        SignUpRequest req = new SignUpRequest("", PASSWORD);
        assert (req.isEmpty());
    }

    /**
     * Tests the isEmpty method.
     * Verifies that is returns true when the password is empty.
     */
    @Test
    public void testEmptyPassword() {
        SignUpRequest req = new SignUpRequest(USERNAME, "");
        assert (req.isEmpty());
    }

    /**
     * Tests the isEmpty method.
     * Verifies that is returns false when both fields are filled in.
     */
    @Test
    public void testEmptyNone() {
        assertFalse(request.isEmpty());
    }

    /**
     * Tests the isEmpty method.
     * Verifies that is returns true when both fields are empty.
     */
    @Test
    public void testEmptyBoth() {
        SignUpRequest req = new SignUpRequest("", "");
        assert (req.isEmpty());
    }

}