package ClockInClockOut.Controller;

import Model.ModelManager;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LogInController {

    private ModelManager modelManager;

    //    Fields from the scene built by FXML

    @FXML
    private Label wrongPasswordLabel;
    @FXML
    private TextField passwordTextField, userNameTextField;

    /**
     * The constructor of the ConfirmableController
     * NOTE: This constructor is called before the FXML elements are injected. DO NOT access any FXML element here.
     */
    public LogInController() {
        this.modelManager = new ModelManager();

//        modelManager.addUser("admin", 1216702299, "Duc", "Le", "dle@macalester.edu", "admin", "abc123");
    }

    /**
     * This is the constructor to create the LoginController from other stages and reuse the current modelManager
     *
     * @param modelManager the current modelManager, passed by the current scene
     */
    public LogInController(ModelManager modelManager) {
        this.modelManager = modelManager;
    }

    /**
     * This method is called after the constructor and all the FXML elements has been loaded
     * The @FXML annotation will populate all the @FXML annotated elements before doing this method
     */
    @FXML
    public void initialize() {
//        Load the data to the view, populate the view,... here
    }

    /**
     * Method to callback when log in button is clicked
     *
     * @param event the Action event from the button
     */
    public void logInClicked(ActionEvent event) {
        // Check to see whether the information is from a valid user
        if (modelManager.isValidPassword(userNameTextField.getText(), passwordTextField.getText())) {

            User user = modelManager.getUser(userNameTextField.getText());
            Button butn = (Button) event.getSource();

//                Switch to the scene of hours tracking
            Stage stage = (Stage) butn.getScene().getWindow();

//                Load the new Scene
            FXMLLoader hoursTracking = new FXMLLoader(getClass().getResource("/StagesAndScenes/hoursTracking.fxml"));
            hoursTracking.setController(new HoursTrackingController(modelManager, user));

            try {
                stage.setScene(new Scene(hoursTracking.load()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        } else {
            wrongPasswordLabel.setText("Sai mật khẩu hoặc tên đăng nhập");
            wrongPasswordLabel.setVisible(true);
            return;
        }
    }

    /**
     * Callback method for forget password button
     *
     * @param event
     */
    public void adminLogInClicked(ActionEvent event) {
        // Check to see whether the current user is in admin list
        if (modelManager.isValidAdmin(userNameTextField.getText(), passwordTextField.getText())) {
            // Get the current user from database
            User user = modelManager.getUser(userNameTextField.getText());
            FXMLLoader adminPage = new FXMLLoader(getClass().getResource("/StagesAndScenes/AdminPage.fxml"));
            adminPage.setController(new AdminPageController(modelManager, user));

            Button butn = (Button) event.getSource();

//                Switch to the scene of hours tracking
            Stage stage = (Stage) butn.getScene().getWindow();
            try {
                stage.setScene(new Scene(adminPage.load()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        } else {
            wrongPasswordLabel.setText("You are not an admin or wrong password!");
            wrongPasswordLabel.setVisible(true);
            return;
        }
    }
}
