package ClockInClockOut.Controller;

import Model.ModelManager;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AddNewUserController extends ConfirmableController {
    // Elements for getting info of user
    @FXML private TextField firstNameTextField;
    @FXML private TextField lastNameTextField;
    @FXML private TextField userNameTextField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPassField;
    @FXML private TextField emailTextField;
    @FXML private TextField phoneTextField;
    @FXML private ChoiceBox<String> roleChoiceBox;

    // Elements for displaying warning and errors
    @FXML private Label usernameWarningLabel;
    @FXML private Label passwordWarningLabel;
    @FXML private Label phoneWarningLabel;

    // ConfirmableController buttons
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
        roleChoiceBox.setValue("employee");
    }

    /**
     * Callback method for the addUserButton
     * @param event
     */
    public void addUserButtonClicked(ActionEvent event) {
        // Make sure the warning and error label only display the current error and not the last one
        passwordWarningLabel.setVisible(false);
        usernameWarningLabel.setVisible(false);
        phoneWarningLabel.setVisible(false);

        String username = userNameTextField.getText();
        String password = passwordField.getText();
        String confirmPass = confirmPassField.getText();

        // Check to make sure all the required fields are filled
        if (username.isEmpty() || password.isEmpty() || confirmPass.isEmpty()) {
            System.out.println("Username or password is empty, cannot add user");
            // Display the corresponding error to user
            if (username.isEmpty()) {
                usernameWarningLabel.setText("*Required");
                usernameWarningLabel.setVisible(true);
            } else {
                passwordWarningLabel.setText("*Required");
                passwordWarningLabel.setVisible(true);
            }
            return;
        } else if (!confirmPass.equals(password)) {
            // Password and confirmation do not match
            System.out.println("Password and confirmation do not match");
            passwordWarningLabel.setText("*Wrong password");
            passwordWarningLabel.setVisible(true);
        } else if (!modelManager.isAvailableUsername(username)) {
            // Duplicated username already exists in the database
            usernameWarningLabel.setText("*Duplicated username");
            usernameWarningLabel.setVisible(true);
        } else {
            String firstName = firstNameTextField.getText();
            String lastName = lastNameTextField.getText();
            String email = emailTextField.getText();
            String phone = phoneTextField.getText();
            String role = roleChoiceBox.getValue();

            boolean confirmed = showConfirmationAndGetResult("add user",
                    "Are you sure you want to add this user as an "+role+"?");

            if (confirmed) {
                try {
                    // addUser will only fail if there is a duplicated username, but we checked for this case already
                    boolean added = modelManager.addUser(username, Long.parseLong(phone), firstName, lastName, email, role, password);
                } catch (NumberFormatException e) {
                    phoneWarningLabel.setVisible(true);
                }
            }
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
