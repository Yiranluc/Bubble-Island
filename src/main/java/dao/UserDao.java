package dao;

import controllers.UserDaoController;
import generalize.DatabaseConnection;

import java.sql.SQLException;
import java.util.ArrayList;

import requests.LogInRequest;
import requests.SignUpRequest;

/**
 * Data Access Object class that is used by the GUI.
 * The class itself makes use of a DatabaseConnection
 * to store and retrieve information and a Controller
 * to forward feedback to the user (GUI).
 */
public class UserDao {
    private transient DatabaseConnection databaseConnection;
    private transient UserDaoController controller;

    /**
     * Constructor method with parameters. Allows for injecting mocks.
     * @param conn the DatabaseConnection.
     * @param contr the UserDaoController.
     */
    public UserDao(DatabaseConnection conn, UserDaoController contr) {
        databaseConnection = conn;
        controller = contr;
    }

    /**
     * Constructor method without parameters.
     * Easy to use for the GUI classes, since it automatically
     * instantiates the attributes.
     */
    public UserDao() {
        databaseConnection = new DatabaseConnection();
        controller = new UserDaoController();
    }

    /**
     * Getter method for the DatabaseConnection.
     * @return databaseConnection of the instance.
     */
    public DatabaseConnection getDatabaseConnection() {
        return databaseConnection;
    }

    /**
     * Setter method for the DatabaseConnection.
     * @param databaseConnection to set to the instance.
     */
    public void setDatabaseConnection(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    /**
     * Getter method for the Controller.
     * @return controller of the instance.
     */
    public UserDaoController getController() {
        return this.controller;
    }

    /**
     * Getter method for the Controller.
     * @param controller to set to the instance.
     */
    public void setController(UserDaoController controller) {
        this.controller = controller;
    }

    /**
     * Tries to insert a new user into the database,
     * after checking the validity of the request.
     * @param signUpRequest The request containing the credentials to register.
     */
    public void register(SignUpRequest signUpRequest) {
        // Check if any of the fields is empty
        if (signUpRequest.isEmpty()) {
            controller.emptyFields();
            return;
        }

        // Check if the username is unique.
        try {
            if (!databaseConnection.isUnique(signUpRequest.getUsername())) {
                controller.duplicateUsername();
                return;
            }
        } catch (SQLException e) {
            controller.connectionFailed();
            return;
        }

        // Insert the credentials into the database.
        try {
            databaseConnection.register(signUpRequest);
            controller.registered();
        } catch (SQLException e) {
            controller.connectionFailed();
        } finally {
            databaseConnection.close();
        }
    }

    /**
     * Login with your user credentials in order to play the game.
     * @param logInRequest The credentials provided by the user.
     */
    public boolean login(LogInRequest logInRequest) {
        // Check if any of the fields is empty
        if (logInRequest.isEmpty()) {
            controller.emptyFields();
            return false;
        }
        try {
            if (databaseConnection.login(logInRequest)) {
                controller.loggedIn();
                return true;
            } else {
                controller.logInFailed();
                return false;
            }
        } catch (SQLException e) {
            controller.connectionFailed();
            return false;
        } finally {
            databaseConnection.close();
        }
    }

    /**
     * Retrieve the names of users on the leader board.
     * @return a list of names.
     */
    public ArrayList<String> retrieveName()  {
        try {
            return databaseConnection.retrieveName();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Retrieve the scores of users on the leader board.
     * @return a list of scores.
     */
    public ArrayList<String> retrieveScore() {
        try {
            return databaseConnection.retrieveScore();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void insertName_Score(int score, String playerName) {
        databaseConnection.insertNameAndScore(score, playerName);
    }
}
