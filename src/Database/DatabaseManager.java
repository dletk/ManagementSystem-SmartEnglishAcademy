package Database;

import java.sql.*;
import java.sql.PreparedStatement;
import java.util.HashSet;

public class DatabaseManager {

    //    The list of admin
    private HashSet<String> admins;
    private Connection connection;

    /**
     * Constructor for DatabaseManager
     */
    public DatabaseManager() {
//        Prepare the database
        String url = "jdbc:sqlite:seaDatabase.db";

        try {
            connection = DriverManager.getConnection(url);

//            Create the table if not exist
            createTable("Users", "userID INT PRIMARY KEY NOT NULL, " +
                    "username TEXT, firstname text, lastname TEXT, email TEXT, phone INT, role TEXT");
            createTable("Shifts", "startingTime TEXT PRIMARY KEY NOT NULL, endingTime TEXT, length REAL, " +
                    "userID INT, FOREIGN KEY(userID) REFERENCES Users(userID)");
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    /**
     * Create a table in the database if that table does not exist
     *
     * @param tableName
     * @param configuration
     */
    private void createTable(String tableName, String configuration) {
        try {
            PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    configuration +
                    ")");
            statement.execute();

        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
