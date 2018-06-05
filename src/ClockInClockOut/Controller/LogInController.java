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

import java.util.ArrayList;

public class LogInController {

    private ModelManager modelManager;

    //    Fields from the scene built by FXML

    @FXML
    private Label wrongPasswordLabel;
    @FXML
    private TextField passwordTextField, userNameTextField;

    /**
     * The constructor of the Controller
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
    public void logInClicked(ActionEvent event) throws Exception {
        ArrayList<User> users = modelManager.getUsers();
        for (User user : users) {
            if (modelManager.isValidPassword(userNameTextField.getText(), passwordTextField.getText())) {
                Button butn = (Button) event.getSource();

//                Switch to the scene of hours tracking
                Stage stage = (Stage) butn.getScene().getWindow();

//                Load the new Scene
                FXMLLoader hoursTracking = new FXMLLoader(getClass().getResource("../StagesAndScenes/hoursTracking.fxml"));
                hoursTracking.setController(new HoursTrackingController(modelManager, user));

                stage.setScene(new Scene(hoursTracking.load()));
                return;

            } else {
                wrongPasswordLabel.setText("Sai mật khẩu hoặc tên đăng nhập");
                wrongPasswordLabel.setVisible(true);
                return;
            }
        }
    }

    /**
     * Callback method for forget password button
     *
     * @param event
     */
    public void forgetPasswordClicked(ActionEvent event) {
        ArrayList<User> users = modelManager.getUsers();
        for (User user : users) {
            if (user.getUsername().equals(userNameTextField.getText()) && user.getPassword().equals(passwordTextField.getText())) {
//                HashMap<String, String> fieldsData = new HashMap<>();
//                fieldsData.put("userID", "3");

                modelManager.addUser("admin", 1215702299, "Trinh", "Nguyen", "trinh060606@gmail.com", "user", "ducle");
                return;
            } else {
                wrongPasswordLabel.setText("Sai mật khẩu hoặc tên đăng nhập");
                return;
            }
        }
        wrongPasswordLabel.setVisible(true);
    }
}
