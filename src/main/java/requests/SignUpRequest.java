package requests;

public class SignUpRequest extends UserRequest {

    /**
     * Constructor method.
     * @param username entered by player.
     * @param password entered by player.
     */
    public SignUpRequest(String username, String password) {
        super(username, password);
    }

}
