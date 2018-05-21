package Model;

import java.sql.*;
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
            createTable("Users", "userID INT PRIMARY KEY NOT NULL UNIQUE, " +
                    "username TEXT NOT NULL UNIQUE, password TEXT NOT NULL, firstname TEXT, lastname TEXT, email TEXT, phone INT, role TEXT");
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
                        result.getLong("phone"),
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
     *
     * @return the shifts map
     */
    public HashMap<User, ArrayList<Shift>> getShifts() {
        return shifts;
    }

    /**
     * Method to retrieve the shifts data from the database
     * This method retrieve the shifts in the order of most recent shift.
     * The result is a map of user and his list of shifts, with the order is most recent.
     */
    private void createShifts() {
        shifts = new HashMap<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Shifts ORDER BY datetime(startingTime) DESC");
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
     *
     * @param userID        the userID to update data
     * @param fieldsAndData the map contains the field and the newdata for that field
     * @return true if the database is updated successfully
     */
    protected boolean updateUser(String userID, HashMap<String, String> fieldsAndData) {
//        Loop through each field in the new data and update
        for (Map.Entry<String, String> entry : fieldsAndData.entrySet()) {
            String field = entry.getKey();
            String data = entry.getValue();
            try {
//                TODO: HERE, BREAK DOWN THE STATEMENT TO 2 CASES, if fields is phone or userID, then leave it, if not,
//                put the '' around the data to indicate string value.
                PreparedStatement statement = connection.prepareStatement("UPDATE Users SET " +
                        field + " = " + data + " WHERE userID= " + userID);
                statement.executeUpdate();


                createUsersList();
                createShifts();
            } catch (SQLException e) {
                //            TODO: HANDLE THE ERROR OF EXISTING ID/USERNAME HERE BY SQL CODE
//                Get the code of the exception
                int code = e.getErrorCode();

                System.out.println("====> SQL CODE: " + Integer.toString(code));

                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * Method to add a new user to the database. This method also check and report if the new user is not qualified.
     *
     * @param userID
     * @param phone
     * @param username
     * @param firstname
     * @param lastname
     * @param email
     * @param role
     * @param password
     * @return true if the user is added successfully
     */
    protected boolean addUser(int userID, long phone, String username, String firstname, String lastname, String email, String role, String password) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Users(" +
                    "userID, username, password, firstname, lastname, email, phone, role)" +
                    "VALUES (" +
                    Integer.toString(userID) + ",'" + username + "','" + password + "','" + firstname + "','" + lastname +
                    "','" + email + "'," + Long.toString(phone) + ",'" + role +
                    "')");
            statement.executeUpdate();

            createShifts();
            createUsersList();

            return true;
        } catch (SQLException e) {
            //            TODO: HANDLE THE ERROR OF EXISTING ID/USERNAME HERE BY SQL CODE

            int code = e.getErrorCode();
//            TODO: The return code is 19 - Constraint violation. This is due to duplicate username or password
            System.out.println("====> SQL CODE: " + Integer.toString(code));


            System.out.println(e);
            e.printStackTrace();

            return false;
        }
    }

    /**
     * Method to add a new shift. In theory, a shift should be added with only starting time. The endingTime and length should
     * be added later when the user finishes his shift.
     *
     * @param userID id of the working user
     * @return
     */
    protected boolean addShift(int userID) {
        try {
//            Get the current time in second
            long currentTime = System.currentTimeMillis() / 1000;

            PreparedStatement statement = connection.prepareStatement("INSERT INTO Shifts(userID, startingTime)" +
                    "VALUES (" +
                    Integer.toString(userID) +
                    ", + datetime(" + Long.toString(currentTime) +
                    ", 'unixepoch', 'localtime')" +
                    ")");

            statement.executeUpdate();

//            Update the list of shift with the new updated version
            createShifts();

            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }


    /**
     * Method to update the data of a current shift in database of a user
     *
     * @param shift the shift to be updated
     * @param field the field in the shift database to be updated
     * @param data  the data to update. NOTICE: Time needs to be in second
     * @return true if the shift is updated successfully.
     */
    protected boolean updateShift(Shift shift, String field, String data) {
        // Get userID and startingTime, date because these are the identifiers for Shift in database
        int userID = shift.getUser().getUserID();
        String startingTime = shift.getStartingTime();
        String date = shift.getDate();

        // In the database, startingTime includes date, but we break them down when we create our model
        startingTime = date + " " + startingTime;

        PreparedStatement statement;

        try {
            if (field.equals("startingTime") || field.equals("endingTime")) {
                // These fields have the underlined data be a String, so the syntax are different with int or float
                statement = connection.prepareStatement("UPDATE Shifts SET " +
                        field + "=" + " +  datetime(" + data +
                        ", 'unixepoch', 'localtime') " +
                        "WHERE userID=" + Integer.toString(userID) + " AND " + "startingTime=" + "'" + startingTime + "'");
            } else {
                // The userID and length field have the underlined data to be integer and float, so the syntax does not need
                // to include the ' '
                statement = connection.prepareStatement("UPDATE Shifts SET " +
                        field + "=" + data +
                        " WHERE userID=" + Integer.toString(userID) + " AND " + "startingTime=" + "'" + startingTime + "'");
            }

            statement.executeUpdate();
            // Update the shifts list with the new values
            createShifts();

            return true;

        } catch (SQLException e) {
            int errorCode = e.getErrorCode();
            System.out.println(e.getSQLState());
            System.out.println("Cannot update shift!, error code: " + Integer.toString(errorCode));
            return false;
        }
    }
}
