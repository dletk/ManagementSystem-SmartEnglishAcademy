package ClockInClockOut.Controller;

import Model.ModelManager;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AdminPageController {
    // Logic elements of the controller
    ModelManager modelManager;
    User user;
    // Elements from UI
    @FXML
    private Button addUserButton;
    @FXML
    private Button logOutButton;
    @FXML
    private Button checkHoursButton;

    public AdminPageController(ModelManager modelManager, User user) {
        this.modelManager = modelManager;
        this.user = user;
    }

    @FXML
    public void initialize() {

    }

    /**
     * Callback method for logoutButton
     *
     * @param event
     * @throws Exception
     */
    public void logOutButtonClicked(ActionEvent event) {
        // Get the current StagesAndScenes of the logOut button
        Button logOut = (Button) event.getSource();
        Stage stage = (Stage) logOut.getScene().getWindow();
        try {
            loadLogInScene(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Callback method for addUserButton
     *
     * @param event
     * @throws Exception
     */
    public void addUserButtonClicked(ActionEvent event) {
        // Popup a new window for adding user
        Stage addUserStage = new Stage();
        // Block all other stage
        addUserStage.initModality(Modality.APPLICATION_MODAL);
        addUserStage.setTitle("Trung tâm anh ngữ SEA");

        // Load the FXML
        FXMLLoader addUserPage = new FXMLLoader(getClass().getResource("/StagesAndScenes/AddUserPage.fxml"));

        // Set up the controller for addUserPage, passing the current modelManager to reuse
        addUserPage.setController(new AddNewUserController(modelManager));

        // Show and wait for the stage to be closed
        try {

            addUserStage.setScene(new Scene(addUserPage.load()));
            addUserStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkHoursButtonClicked(ActionEvent event) {
        // Popup a new window for checking hours
        Stage checkHoursStage = new Stage();
        checkHoursStage.setTitle("Trung tâm anh ngữ SEA");
        checkHoursStage.initModality(Modality.APPLICATION_MODAL);

        // Load the FXML file
        FXMLLoader checkHoursPage = new FXMLLoader(getClass().getResource("/StagesAndScenes/CheckHoursPage.fxml"));

        // Set up the controller
        checkHoursPage.setController(new CheckHoursController(modelManager));

        // Show and wait to finish
        try {
            checkHoursStage.setScene(new Scene(checkHoursPage.load()));
            checkHoursStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Load the log in scene to the given stage
     *
     * @param stage
     * @throws Exception
     */
    private void loadLogInScene(Stage stage) throws Exception{
//        Load the FXML sepratedly to manually set up the controller
        FXMLLoader logIn = new FXMLLoader(getClass().getResource("/StagesAndScenes/logIn.fxml"));

//        Set up the controller for logIn scene, reuse the current modelManager
        logIn.setController(new LogInController(modelManager));

        stage.setScene(new Scene(logIn.load()));
    }
}
