package ClockInClockOut.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ConfirmClockInClockOutController {

    // The variable to indicate the confirmation
    private boolean confirmed;
    // Current action to confirm;
    private String currentAction;

    // UI components of confirmation scene
    @FXML
    private Label titleLabel, questionLabel;
    @FXML
    private Button confirmButton, cancelButton;

    /**
     * Constructor for the confirmation window controller
     *
     * @param currentAction the current action to confirm
     */
    public ConfirmClockInClockOutController(String currentAction) {
        super();
        // At first, assume that the confirmation is false (not confirmed)
        this.currentAction = currentAction;
        this.confirmed = false;
    }


    /**
     * Method to initialize the FXML components and the display of the scene
     */
    @FXML
    public void initialize() {
        changeText();
    }

    /**
     * Method to change all the texts in the scene to the proper display for current action
     */
    private void changeText() {
        // Clock in is the default display of the scene, so only change text if the current action is clockOut
        if (currentAction.equals("clockOut")) {
            titleLabel.setText("CLOCK OUT");
            questionLabel.setText("Are you sure you want to clock out?");
            confirmButton.setText("Yes, clock out");
        }
    }

    /**
     * Method to return the value of confirmation
     * @return
     */
    public boolean isConfirmed() {
        return confirmed;
    }

    /**
     * Callback method for confirmButton
     * @param event
     */
    public void confirmButtonClicked(ActionEvent event) {
        confirmed = true;
        closeWindow(event);
    }

    /**
     * Callback method for cancelButton
     * @param event
     */
    public void cancelButtonClicked(ActionEvent event) {
        closeWindow(event);
    }

    /**
     * Method to close the current window
     * @param event
     */
    private void closeWindow(ActionEvent event) {
        // Get the current stage
        Button clickedButton = (Button) event.getSource();
        Stage confirmStage = (Stage) clickedButton.getScene().getWindow();

        // Close the stage
        confirmStage.close();
    }
}
