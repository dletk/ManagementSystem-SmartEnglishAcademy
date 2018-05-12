package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The model manager class to control all the logic of working with models and database
 * in the program.
 */
public class ModelManager {
    private DatabaseManager databaseManager;
    private ArrayList<User> users;
    private HashMap<User, ArrayList<Shift>> shifts;

    public ModelManager() {
        this.databaseManager = new DatabaseManager();
        this.users = databaseManager.getUsers();
        this.shifts = databaseManager.getShifts();
    }

    public ArrayList<User> getUsers() {
        return this.users;
    }

    public HashMap<User, ArrayList<Shift>> getShifts() {
        return this.shifts;
    }

    /**
     * Method to update the data of a user
     * @param user the user to update the data
     * @param fieldsAndData the data to update in the database
     * @return true if data is updated
     */
    public boolean updateUser(User user, HashMap<String, String> fieldsAndData) {
        String userID = Integer.toString(user.getUserID());

        if (databaseManager.updateUser(userID, fieldsAndData)) {
            users = databaseManager.getUsers();
            shifts = databaseManager.getShifts();
        }

        return true;
    }
}
