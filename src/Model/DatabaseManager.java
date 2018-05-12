package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseManager {

    ArrayList<User> users;
    HashMap<User, ArrayList<Shift>> shifts;
    private Connection connection;

    /**
     * Constructor for DatabaseManager
     */
    protected DatabaseManager() {
//        Prepare the database
        String url = "jdbc:sqlite:seaDatabase.db";

        try {
            connection = DriverManager.getConnection(url);

//            Create the table if not exist
            createTable("Users", "userID INT PRIMARY KEY NOT NULL, " +
                    "username TEXT NOT NULL, password TEXT NOT NULL, firstname TEXT, lastname TEXT, email TEXT, phone INT, role TEXT");
            createTable("Shifts", "startingTime TEXT PRIMARY KEY NOT NULL, endingTime TEXT, length REAL, " +
                    "userID INT, FOREIGN KEY(userID) REFERENCES Users(userID)");
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }

        createUsersList();
        createShifts();
    }

    /**
     * Create a table in the database if that table does not exist
     *
     * @param tableName     name of the table to be created
     * @param configuration the setting of that table
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

    /**
     * Method to retrieve the users list for other classes to use
     *
     * @return user lists
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * Method to retrieve the Users data from the database
     *
     * @return A lists contain all the users in the database
     */
    private void createUsersList() {
        users = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Users");

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                users.add(new User(result.getInt("userID"),
                        result.getInt("phone"),
                        result.getString("username"),
                        result.getString("firstname"),
                        result.getString("lastname"),
                        result.getString("email"),
                        result.getString("role"),
                        result.getString("password")));
            }
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    /**
     * Method to get the shifts for other classes
     * @return the shifts map
     */
    public HashMap<User, ArrayList<Shift>> getShifts() {
        return shifts;
    }

    /**
     * Method to retrieve the shifts data from the database
     */
    private void createShifts() {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Shifts");
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                int userID = result.getInt("userID");

//                Loop through all users in the users list
                for (User user : users) {
                    if (user.getUserID() == userID) {
//                        If that user is the same ID with shift userID, it means that shift is that user's
                        Shift shift = new Shift(user,
                                result.getString("startingTime"),
                                result.getString("endingTime"),
                                result.getFloat("length"));
//                        Initialize the list if the user is new
                        if (!shifts.containsKey(user)) {
                            shifts.put(user, new ArrayList<>());
                        }
                        shifts.get(user).add(shift);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    /**
     * Method to update the user data in the database with the given id and new field data
     * @param userID the userID to update data
     * @param fieldsAndData the map contains the field and the newdata for that field
     * @return true if the database is updated successfully
     */
    protected boolean updateUser(String userID, HashMap<String, String> fieldsAndData) {
//        Loop through each field in the new data and update
        for (Map.Entry<String, String> entry: fieldsAndData.entrySet()) {
            String field = entry.getKey();
            String data = entry.getValue();
            try {
                PreparedStatement statement = connection.prepareStatement("UPDATE Users SET " +
                        field + " = " + data + " WHERE userID= " + userID);
                statement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        createUsersList();
        createShifts();

        return true;
    }
}
