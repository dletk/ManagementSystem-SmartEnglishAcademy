package ClockInClockOut.Controller;

import Model.ModelManager;
import Model.Shift;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;

public class HoursTrackingController extends ConfirmableController {

    //    The hours tracking table elements
    @FXML
    private TableView<Shift> tableHours;
    @FXML
    private TableColumn<Shift, String> dateColumn;
    @FXML
    private TableColumn<Shift, String> startingTimeColumn;
    @FXML
    private TableColumn<Shift, String> endingTimeColumn;
    @FXML
    private TableColumn<Shift, Float> lengthColumn;

    //    Other UI elements
    @FXML
    private Button logOutButton;
    @FXML
    private Label statusLabel;
    @FXML
    private Label welcomeLabel;

    // Variables for this class
    private User user;
    private ModelManager modelManager;

    /**
     * The constructor for the hours tracking controller
     * Reuse the current modelManager nad receive information about current user from the calling controller
     *
     * @param modelManager the current modelManager to reuse
     * @param user         the currently logged in user
     */
    public HoursTrackingController(ModelManager modelManager, User user) {
        this.modelManager = modelManager;
        this.user = user;
    }

    @FXML
    public void initialize() {
        this.populateTableWithData();

        // Change the status label to be the correct status of the current user
        changeStatusLabel();
        // Change the welcome label to the current username
        welcomeLabel.setText("Welcome, " + user.getFirstname());
    }

    /**
     * This method populate the table with the current data
     */
    private void populateTableWithData() {
        // Get all the shifts of current user
        ArrayList<Shift> shiftsOfUser = modelManager.getShifts().get(user.getUsername());

        // If this user does not have any shift, its shift list will be NULL
        // if so, do not populate the table to avoid crashing
        if (shiftsOfUser != null) {
            ObservableList<Shift> data = FXCollections.observableArrayList(shiftsOfUser);

            tableHours.setItems(data);
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            startingTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startingTime"));
            endingTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endingTime"));
            lengthColumn.setCellValueFactory(new PropertyValueFactory<>("length"));
        }
    }


    /**
     * Callback method for log out button.
     * After clicking this button, a new scene will appear to confirm the user's choice.
     *
     * @param event
     */
    public void logOutClicked(ActionEvent event) {
//        Get the current StagesAndScenes of the logOut button
        Button logOut = (Button) event.getSource();
        Stage stage = (Stage) logOut.getScene().getWindow();

//        Load the FXML sepratedly to manually set up the controller
        FXMLLoader logIn = new FXMLLoader(getClass().getResource("/StagesAndScenes/logIn.fxml"));

//        Set up the controller for logIn scene, reuse the current modelManager
        logIn.setController(new LogInController(modelManager));
        try {

            stage.setScene(new Scene(logIn.load()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Callback method for clock in button
     *
     * @param event the event of clicking
     */
    public void clockInClicked(ActionEvent event) {

        // Confirm the user decision to clock in.
        boolean confirmed = showConfirmationAndGetResult("clock in");

        if (confirmed) {
            // Let modelManager start the clock in process
            boolean result = modelManager.clockIn(user);

            if (result) {
                // Clock in has been done successfully.
                // Change the status label to clocked in
                changeStatusLabel();
            } else {
                // TODO: User is not allowed to clock in because miss clock out.
            }

            // Update the hours table with the new data
            populateTableWithData();
        }
    }

    /**
     * Callback method for clock out button
     *
     * @param event the clicking event
     */
    public void clockOutClicked(ActionEvent event) {
        // Confirm user decision to clock out.
        boolean confirmed = showConfirmationAndGetResult("clock out");

        if (confirmed) {
            boolean result = modelManager.clockOut(user);

            if (result) {
                // Clock out has been done successfully
                // Change the status label to clocked out
                changeStatusLabel();
            } else {
                // TODO: User is not allowed to clock out because of missing clock in
            }

            // Update the hours table
            populateTableWithData();
        }
    }

    /**
     * This method use to change the status label to the current status of currently logged-in user
     */
    private void changeStatusLabel() {
        // Check whether the current user is clocked in
        if (modelManager.isUserClockedIn(user)) {
            // This user is currently clocked in
            statusLabel.setText("Status: Clocked in");
        } else {
            // This user is currently clocked out
            statusLabel.setText("Status: Clocked out");
        }
    }
}
