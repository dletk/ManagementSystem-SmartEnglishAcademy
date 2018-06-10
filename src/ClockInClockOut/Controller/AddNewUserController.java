package ClockInClockOut.Controller;

import Model.ModelManager;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddNewUserController {
    // Elements for getting info of user
    @FXML private TextField firstNameTextField;
    @FXML private TextField lastNameTextField;
    @FXML private TextField userNameTextField;
    @FXML private TextField passwordTextField;
    @FXML private TextField emailTextField;
    @FXML private TextField phoneTextField;
    @FXML private ChoiceBox<String> roleChoiceBox;

    // Controller buttons
    @FXML private Button addUserButton;
    @FXML private Button cancelButton;

    // Model manager and other controller elements
    ModelManager modelManager;

    public AddNewUserController(ModelManager modelManager) {
        this.modelManager = modelManager;
    }

    @FXML
    public void initialize() {
        roleChoiceBox.setItems(FXCollections.observableArrayList("admin", "employee"));
    }

    /**
     * Callback method for the addUserButton
     * @param event
     */
    public void addUserButtonClicked(ActionEvent event) {
        String username = userNameTextField.getText();
        String password = passwordTextField.getText();
        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Username or password is empty, cannot add user");
            return;
        } else {
            String firstName = firstNameTextField.getText();
            String lastName = lastNameTextField.getText();
            String email = emailTextField.getText();
            String phone = phoneTextField.getText();
            String role = roleChoiceBox.getValue();

            modelManager.addUser(username, Long.parseLong(phone), firstName, lastName, email, role, password);
        }
    }

    /**
     * Callback method for the cancelButton
     * @param event
     */
    public void cancelButtonClicked(ActionEvent event) {
        Stage currentStage = (Stage) cancelButton.getScene().getWindow();

        currentStage.close();
    }
}
