package dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import controllers.UserDaoController;
import generalize.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.LogInRequest;
import requests.SignUpRequest;

/**
 * Test class to test the methods of the UserDao class.
 */
class UserDaoTest {
    private transient DatabaseConnection connection;
    private transient UserDaoController controller;
    private transient UserDao dao;
    private transient ArrayList<String> testArrayList;
    private transient ArrayList<String> actualArrayList;

    private static String UNIQUE = "Cheyenne99";
    private static String DUPLICATE = "Chey";
    private static String PASSWORD = "Password";

    /**
     * Before each test, mock a DatabaseConnection and UserDaoController.
     * Then use the mocks to instantiate a UserDao object.
     * The mocks are used to improve controllability and observability.
     */
    @BeforeEach
    public void initialize() {
        connection = mock(DatabaseConnection.class);
        try {
            // This is a unique username.
            when(connection.isUnique(UNIQUE)).thenReturn(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            // This is a duplicate username.
            when(connection.isUnique(DUPLICATE)).thenReturn(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        controller = mock(UserDaoController.class);

        dao = new UserDao(connection, controller);
    }

    /**
     * Tests a successful registration by the dao instance.
     * Verifies that the registered() method of the controller is called.
     */
    @Test
    public void testRegisterSuccessfully() {
        SignUpRequest request = new SignUpRequest(UNIQUE, "Secret");

        dao.register(request);

        verify(controller).registered();
    }

    @Test
    public void testLogInSuccessfully() {
        LogInRequest logInRequest = new LogInRequest("chey", "123");
        try {
            when(connection.login(logInRequest)).thenReturn(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assert dao.login(logInRequest);

        verify(controller).loggedIn();
    }

    @Test
    public void testLogInFailed() {
        LogInRequest logInRequest = new LogInRequest(UNIQUE, "abc");
        try {
            when(connection.login(logInRequest)).thenReturn(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assertFalse(dao.login(logInRequest));

        verify(controller).logInFailed();
    }

    /**
     *  Tests an unsuccessful registration by the dao instance.
     *  Verifies that the duplicateUsername method of the controller is called.
     *  Verifies that the request is never forwarded to the database.
     */
    @Test
    public void testRegisterDuplicateUsername() {
        SignUpRequest request = new SignUpRequest(DUPLICATE, "Secret");

        dao.register(request);

        verify(controller).duplicateUsername();

        try {
            verify(connection, never()).register(request);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Tests an unsuccessful registration by the dao instance.
     * Verifies that the emptyFields method of the controller is called.
     * Verifies that the request is never forwarded to the database.
     */
    @Test
    public void testRegister_emptyUsername() {
        SignUpRequest request = new SignUpRequest("", PASSWORD);

        dao.register(request);

        verify(controller).emptyFields();

        try {
            verify(connection, never()).register(request);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testLogIn_emptyUsername() {
        LogInRequest logInRequest = new LogInRequest("", PASSWORD);

        assertFalse(dao.login(logInRequest));

        verify(controller).emptyFields();

        try {
            verify(connection, never()).login(logInRequest);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Tests an unsuccessful registration by the dao instance.
     * Verifies that the emptyFields method of the controller is called.
     * Verifies that the request is never forwarded to the database.
     */
    @Test
    public void testRegister_emptyPassword() {
        SignUpRequest request = new SignUpRequest(DUPLICATE, "");

        dao.register(request);

        verify(controller).emptyFields();

        try {
            verify(connection, never()).register(request);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testLogIn_emptyPassword() {
        LogInRequest logInRequest = new LogInRequest("Admin", "");

        assertFalse(dao.login(logInRequest));

        verify(controller).emptyFields();

        try {
            verify(connection, never()).login(logInRequest);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Tests the constructor and databaseConnection getter.
     * Verifies that the getter returns the same DatabaseConnection
     * as the one taken as a method argument by the constructor.
     */
    @Test
    public void testConstructorConnection() {
        assertEquals(connection, dao.getDatabaseConnection());
    }

    /**
     * Tests the constructor and Controller getter.
     * Verifies that the getter returns the same Controller
     * as the one taken as a method argument by the constructor.
     */
    @Test
    public void testConstructorController() {
        assertEquals(controller, dao.getController());
    }

    /**
     * Tests the databaseConnection getter and setter.
     * Verifies that the getter returns the same DatabaseConnection
     * as the one set by the setter..
     */
    @Test
    public void testGetterSetterConnection() {
        UserDao userDao = new UserDao();
        userDao.setDatabaseConnection(connection);
        assertEquals(connection, userDao.getDatabaseConnection());
    }

    /**
     * Tests the Controller getter and setter.
     * Verifies that the getter returns the same Controller
     * as the one set by the setter..
     */
    @Test
    public void testGetterSetterController() {
        UserDao userDao = new UserDao();
        userDao.setController(controller);
        assertEquals(controller, userDao.getController());
    }

    /**
     * Tests the constructor without arguments.
     * Verifies that indeed the DatabaseConnection
     * and Controller are instantiated.
     */
    @Test
    public void testConstructor() {
        UserDao userDao = new UserDao();
        assertNotNull(userDao.getDatabaseConnection());
        assertNotNull(userDao.getController());
    }

    /**
     * Tests the register method in case of a connection failure.
     * Verifies that the connectionFailed method of the Controller
     * is called when the register method of the DatabaseConnection
     * raises an exception
     * @throws SQLException when the database connection fails.
     */
    @Test
    public void testRegister_connectionException() throws SQLException {
        SignUpRequest request = new SignUpRequest(UNIQUE, PASSWORD);

        doThrow(SQLException.class).when(connection).register(request);

        dao.register(request);

        verify(controller).connectionFailed();
    }

    /**
     * Tests the register method in case of a connection failure.
     * Verifies that the connectionFailed method of the Controller
     * is called when the isUnique method of the DatabaseConnection
     * raises an exception.
     * @throws SQLException when the database connection fails.
     */
    @Test
    public void testUnique_connectionException() throws SQLException {
        SignUpRequest request = new SignUpRequest(UNIQUE, "Pw");

        doThrow(SQLException.class).when(connection).isUnique(request.getUsername());

        dao.register(request);

        verify(controller).connectionFailed();
    }

    @Test
    public void testLogIn_connectionException() throws SQLException {
        LogInRequest logInRequest = new LogInRequest(UNIQUE, PASSWORD);

        //doThrow(SQLException.class).when(connection).login(logInRequest);
        when(connection.login(logInRequest)).thenThrow(SQLException.class);
        assertFalse(dao.login(logInRequest));

        verify(controller).connectionFailed();
    }

    @Test
    public void test_retrieveName() throws SQLException {

        testArrayList = new ArrayList<>();
        testArrayList.add("Test");
        testArrayList.add("Database");

        actualArrayList = new ArrayList<>();
        actualArrayList.add("Test");
        actualArrayList.add("Database");
        when(connection.retrieveName()).thenReturn(actualArrayList);

        assertEquals(testArrayList, dao.retrieveName());
    }

    @Test
    public void test_retrieveScore() throws SQLException {

        testArrayList = new ArrayList<>();
        testArrayList.add("10");
        testArrayList.add("20");

        actualArrayList = new ArrayList<>();
        actualArrayList.add("10");
        actualArrayList.add("20");
        when(connection.retrieveScore()).thenReturn(actualArrayList);

        assertEquals(testArrayList, dao.retrieveScore());
    }
}