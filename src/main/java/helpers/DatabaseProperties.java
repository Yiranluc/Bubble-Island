package helpers;

import static java.nio.file.Paths.get;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Properties;

public class DatabaseProperties {
    private static Properties properties = null;
    private static Path PATH = get("src", "main", "resources", "database_credentials.properties");

    /**
     * Load the database credentials into the Properties object.
     */
    public static void loadProperties() {
        if (properties == null) {
            properties = new Properties();

            try {
                InputStream input = new FileInputStream(PATH.toString());
                properties.load(input);
            } catch (IOException e) {
                System.out.println("oops something went wrong");
            }
        }
    }

    /**
     * Method to retrieve the database host.
     * @return the database host.
     */
    public static String getHost() {
        loadProperties();
        return properties.getProperty("HOST");
    }

    /**
     * Method to retrieve the database name.
     * @return the database name.
     */
    public static String getName() {
        loadProperties();
        return properties.getProperty("DATABASE");
    }

    /**
     * Method to return the user.
     * @return the database user.
     */
    public static String getUser() {
        loadProperties();
        return properties.getProperty("USER");
    }

    /**
     * Method to return the password.
     * @return the database password
     */
    public static String getPass() {
        loadProperties();
        return properties.getProperty("PASS");
    }

    /**
     * Method to return the url.
     * @return the database url.
     */
    public static String getUrl() {
        loadProperties();
        return "jdbc:mysql://"
                + getHost() + "/"
                + getName()
                + "?useTimezone=true&serverTimezone=UTC";
    }

}
