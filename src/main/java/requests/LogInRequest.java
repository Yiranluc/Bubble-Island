package requests;

public class LogInRequest extends UserRequest {

    /**
     * Constructor method.
     * @param username entered by player.
     * @param password entered by player.
     */
    public LogInRequest(String username, String password) {
        super(username, password);
    }
}

