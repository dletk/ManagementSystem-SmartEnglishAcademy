package ClockInClockOut.Controller;

import Model.ModelManager;
import Model.Shift;
import Model.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;

public class CheckHoursController {
    @FXML
    private TableView tableHours;
    @FXML
    private TableColumn<Shift, String> dateColumn;
    @FXML
    private TableColumn<Shift, String> startingTimeColumn;
    @FXML
    private TableColumn<Shift, String> endingTimeColumn;
    @FXML
    private TableColumn<Shift, Float> lengthColumn;

    // Choicebox to select the user
    @FXML
    private ChoiceBox<String> selectUserBox;

    // The label to display the current selected user
    @FXML
    private Label hoursLabel;

    // The cancel button to close the window
    @FXML
    private Button cancelButton;

    // Model manager variable to control the data
    private ModelManager modelManager;

    public CheckHoursController(ModelManager modelManager) {
        this.modelManager = modelManager;
    }

    @FXML
    public void initialize() {
        // Populate the choice box with user full name
        ArrayList<String> usersFullName = new ArrayList<>();
        for (User user: modelManager.getUsers()) {
            usersFullName.add(user.getFirstname() + " " + user.getLastname());
        }
        selectUserBox.setItems(FXCollections.observableArrayList(usersFullName));

        // Create the event handler for change in value of the choice box
        // When the value of the choiceBox changes, the hours table will be updated accordingly, except if the value is -1
        selectUserBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                int currentIndex = newValue.intValue();
                if (currentIndex != -1) {
                    changeHourLabel(usersFullName.get(currentIndex));
                    populateTableWithData(modelManager.getUsers().get(currentIndex));
                }
            }
        });
    }

    /**
     * Method to change the hour label to display the current selected user name
     */
    private void changeHourLabel(String name) {
        hoursLabel.setText("Hours: " + name);
    }

    /**
     * This method populate the table with the data of the input user
     *
     * @param user the user to display hours
     */
    private void populateTableWithData(User user) {
        // Get all the shifts of current user
        ArrayList<Shift> shiftsOfUser = modelManager.getShifts().get(user.getUsername());

        // If this user does not have any shift, its shift list will be NULL
        // if so, do not populate the table to avoid crashing
        if (shiftsOfUser != null && shiftsOfUser.size() > 0) {
            ObservableList<Shift> data = FXCollections.observableArrayList(shiftsOfUser);

            tableHours.setItems(data);
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            startingTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startingTime"));
            endingTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endingTime"));
            lengthColumn.setCellValueFactory(new PropertyValueFactory<>("length"));
        } else {
            tableHours.getItems().clear();
        }
    }

    /**
     * Callback method for the cancel button to close the window
     *
     * @param event
     */
    public void cancelButtonClicked(ActionEvent event) {
        Stage currentStage = (Stage) cancelButton.getScene().getWindow();
        currentStage.close();
    }
}
