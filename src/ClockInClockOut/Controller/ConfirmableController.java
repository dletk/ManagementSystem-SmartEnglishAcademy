package ClockInClockOut.Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 * The base class for any controller requiring a confirmation view
 */
public class ConfirmableController {

    /**
     * Method to show the confirmation window when clock in or clock out
     * Get the result back to conduct further step
     *
     * @param action the action selected
     * @return true if confirmed
     */
    protected boolean showConfirmationAndGetResult(String action, String message) {
        Stage confirmStage = new Stage(StageStyle.DECORATED);
        // Block all other window until this stage is closed
        confirmStage.initModality(Modality.APPLICATION_MODAL);

        // Prepare the scene
        FXMLLoader scene = new FXMLLoader(getClass().getResource("../StagesAndScenes/ConfirmPage.fxml"));

        // Create the controller with the input is the selected action
        ConfirmPageController controller;
        if (message.isEmpty()) {
            controller = new ConfirmPageController(action);
        } else {
            controller = new ConfirmPageController(action, message);
        }
        scene.setController(controller);

        try {
            confirmStage.setScene(new Scene(scene.load()));
            confirmStage.showAndWait();

            // After the window is close, get the confirmation result
            return controller.isConfirmed();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Method to show the confirmation window with default confirming message
     * @param action the action to be confirmed
     * @return true if the action is confirmed
     */
    protected boolean showConfirmationAndGetResult(String action) {
        return this.showConfirmationAndGetResult(action, "");
    }
}
