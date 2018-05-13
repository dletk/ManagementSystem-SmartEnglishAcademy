package ClockInClockOut;

import Model.ModelManager;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
     * Method to test the functionality of the scene, TODO: Comment out or delete when done testing
     *
     * @param event the Action event from the button
     */
    public void buttonClicked(ActionEvent event) {
        ArrayList<User> users = modelManager.getUsers();
        for (User user: users) {
            if (user.getUsername().equals(userNameTextField.getText()) && user.getPassword().equals(passwordTextField.getText())) {
//                TODO:Switch to the scene of hours tracking
                modelManager.addShift(user.getUserID());
                wrongPasswordLabel.setText("Thành công!");
                System.out.println(modelManager.getShifts());
            } else {
                wrongPasswordLabel.setText("Sai mật khẩu hoặc tên đăng nhập");
            }
        }
        wrongPasswordLabel.setVisible(true);
    }
}
