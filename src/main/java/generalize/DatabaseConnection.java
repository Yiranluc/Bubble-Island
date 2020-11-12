package generalize;

import helpers.DatabaseProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.codec.digest.DigestUtils;
import requests.LogInRequest;
import requests.SignUpRequest;
import requests.UserRequest;

/**
 * This class connects to the database and is used by the system to store and retrieve data.
 */
@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public class DatabaseConnection {

    private transient Connection connection;
    private transient PreparedStatement preparedStatement;

    /**
     * Constructor method, creates a new connection to the database.
     */
    public DatabaseConnection() {
        String url = DatabaseProperties.getUrl();
        String username = DatabaseProperties.getUser();
        String password = DatabaseProperties.getPass();

        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Checks whether a new username is unique.
     * @param username entered by the user trying to sign up.
     * @return true if the username already exists in the database, false otherwise.
     * @throws SQLException if the connection to the database fails.
     */
    public boolean isUnique(String username) throws SQLException {
        String query = "SELECT COUNT(*) FROM `projects_SEM-20-20`.Users "
                + "WHERE username = ?";
        ResultSet rs = null;
        try {
            PreparedStatement stat = connection.prepareStatement(query);
            stat.setString(1, username);
            stat.execute();
            rs = stat.getResultSet();
            rs.next();
            int count = rs.getInt(1);
            return count == 0;
        } catch (SQLException e) {
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
    }

    /**
     * Inserts new credentials into the database.
     * @param signUpRequest username and password the user wants to sign up with.
     * @throws SQLException if the connection to the database fails.
     */
    public void register(SignUpRequest signUpRequest) throws SQLException {
        String query = "INSERT INTO `projects_SEM-20-20`.Users(userID, username, password) "
                + "VALUES(`projects_SEM-20-20`.RandomValue(), ?, ?)";

        authenticationStatement(signUpRequest, query);
    }

    /**
     * Checks whether there is a combination of the username and password.
     * @param logInRequest username and password provided by the user.
     * @return true if there exist an combination of
     *         the username and password exists, false otherwise.
     * @throws SQLException if the connection to the database fails.
     */
    public boolean login(LogInRequest logInRequest) throws SQLException {
        String query = "SELECT (EXISTS(SELECT 1 FROM `projects_SEM-20-20`.Users "
                + "WHERE username = ? AND password = ?));";
        ResultSet resultSet = null;

        try {
            authenticationStatement(logInRequest, query);
            resultSet = preparedStatement.getResultSet();
            resultSet.next();
            int response = resultSet.getInt(1);
            return response == 1;
        } catch (SQLException e) {
            throw e;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }

    /**
     * The method retrieves the names of the top 10 players to be
     * displayed on the leader board.
     * @return an Array list of all the player names.
     */
    public ArrayList<String> retrieveName() throws SQLException {
        ArrayList<String> nameArrayList = new ArrayList<>();
        nameArrayList.add(null);
        ResultSet resultSetName = null;
        String query = "SELECT name FROM `projects_SEM-20-20`."
                + "Scores ORDER BY Scores.score DESC LIMIT 10;";
        try {
            resultSetName = scoreStatement(query);
            if (resultSetName != null) {
                while (resultSetName.next()) {
                    nameArrayList.add(resultSetName.getString("name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSetName != null) {
                resultSetName.close();
            }
        }
        return nameArrayList;
    }

    /**
     * The method retrieves all of the scores of the
     * players to be displayed on the leader board.
     * @return array list of all the top 10 player scores.
     */
    public ArrayList<String> retrieveScore() throws SQLException {
        ArrayList<String> scoreArrayList = new ArrayList<>();
        scoreArrayList.add(null);
        ResultSet resultSetScore = null;

        String query = "SELECT score FROM `projects_SEM-20-20`."
                + "Scores ORDER BY Scores.score DESC LIMIT 10;";
        try {
            resultSetScore = scoreStatement(query);
            if (resultSetScore != null) {
                while (resultSetScore.next()) {
                    scoreArrayList.add(resultSetScore.getString("score"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSetScore != null) {
                resultSetScore.close();
            }
        }
        return scoreArrayList;
    }

    /**
     * This method inserts the name and the score of the current player.
     * @param score the score of the current game.
     * @param playerName the name the player chose.
     */
    public void insertNameAndScore(int score, String playerName) {
        String query = "INSERT INTO `projects_SEM-20-20`.Scores(userID, score, name)"
                + " VALUES(?, ?, ?);";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, 281246);
            preparedStatement.setInt(2, score);
            preparedStatement.setString(3, playerName);
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Helper method to create a prepared statement for other methods.
     * @param query The sql query.
     * @return the result set retrieved from the database.
     */
    private ResultSet scoreStatement(String query) throws SQLException {
        ResultSet resultSetStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
            resultSetStatement = preparedStatement.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSetStatement;

    }

    /**
     * Helper method to create a prepared statement for other methods.
     * @param userRequest username and password provided by the user.
     * @param query the sql query.
     * @return The prepared statement that will be executed by the database.
     */
    public PreparedStatement authenticationStatement(UserRequest userRequest, String query) {
        try {
            preparedStatement = connection.prepareStatement(query);
            String hashedPassword = DigestUtils.sha256Hex(userRequest.getPassword());
            preparedStatement.setString(1, userRequest.getUsername());
            preparedStatement.setString(2, hashedPassword);
            preparedStatement.execute();
            return preparedStatement;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preparedStatement;
    }

    /**
     * Close the connection.
     */
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("cannot close connection");
        }
    }
}
