package ClockInClockOut;

import Model.ModelManager;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
        for (User user: users) {
            if (user.getUsername().equals(userNameTextField.getText()) && user.getPassword().equals(passwordTextField.getText())) {
//                TODO:Switch to the scene of hours tracking
//                wrongPasswordLabel.setText("Thành công!");
                Button butn = (Button) event.getSource();
                Stage stage = (Stage) butn.getScene().getWindow();

//                Load the new Scene
                FXMLLoader hoursTracking = new FXMLLoader(getClass().getResource("hoursTracking.fxml"));
                hoursTracking.setController(new HoursTrackingController(modelManager, user));

                stage.setScene(new Scene(hoursTracking.load()));
                return;

            } else {
                wrongPasswordLabel.setText("Sai mật khẩu hoặc tên đăng nhập");
                return;
            }
        }
        wrongPasswordLabel.setVisible(true);
    }

    /**
     * Callback method for forget password button
     * @param event
     */
    public void forgetPasswordClicked(ActionEvent event) {
        ArrayList<User> users = modelManager.getUsers();
        for (User user: users) {
            if (user.getUsername().equals(userNameTextField.getText()) && user.getPassword().equals(passwordTextField.getText())) {
//                TODO:Switch to the scene of hours tracking
                modelManager.addShift(user.getUserID());
                return;
            } else {
                wrongPasswordLabel.setText("Sai mật khẩu hoặc tên đăng nhập");
                return;
            }
        }
        wrongPasswordLabel.setVisible(true);
    }
}
