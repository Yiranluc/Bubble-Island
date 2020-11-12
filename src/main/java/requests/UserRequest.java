package requests;

/**
 * Class that allows user input captured by a GUI class
 * to be saved into an object.
 * The SignUpRequest object is then handled by the Dao.
 */
public abstract class UserRequest {

    private transient String username;
    private transient String password;

    /**
     * Constructor method.
     * @param username entered by player.
     * @param password entered by player.
     */
    public UserRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Getter method for the username attribute.
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter method for the username attribute.
     * @param username new username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter method for the password attribute.
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter method for the password attribute.
     * @param password new password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Checks whether the request is valid.
     * @return true when both attributes are non-empty,
     *         false otherwise.
     */
    public boolean isEmpty() {
        return (username.equals("") || password.equals(""));
    }
}
