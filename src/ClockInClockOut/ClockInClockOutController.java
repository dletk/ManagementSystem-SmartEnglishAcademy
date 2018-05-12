package ClockInClockOut;

import Model.ModelManager;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class ClockInClockOutController {

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
    public ClockInClockOutController() {
        this.modelManager = new ModelManager();
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
     * @param event the MouseEvent
     */
    public void buttonClicked(ActionEvent event) {
        ArrayList<User> users = modelManager.getUsers();
        for (User user: users) {
            if (user.getUsername().equals(userNameTextField.getText()) && user.getPassword().equals(passwordTextField.getText())) {
                wrongPasswordLabel.setText("Thành công!");
            } else {
                wrongPasswordLabel.setText("Sai mật khẩu hoặc tên đăng nhập");
            }
        }
        wrongPasswordLabel.setVisible(true);
    }
}
