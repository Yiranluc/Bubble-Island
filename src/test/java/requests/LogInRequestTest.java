package requests;

import org.junit.jupiter.api.BeforeEach;

class LogInRequestTest extends UserRequestTest {

    /**
     * Initialize the request to be used in the tests below.
     */
    @Override
    @BeforeEach
    public void initialize() {
        request = new LogInRequest(USERNAME, PASSWORD);
    }
}