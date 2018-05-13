package ClockInClockOut;

import Model.ModelManager;
import Model.Shift;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;

public class HoursTrackingController {
    @FXML
    private TableView<Shift> tableHours;
    @FXML
    private TableColumn<Shift, String> dateColumn;
    @FXML
    private TableColumn<Shift, String> startingTimeColumn;
    @FXML
    private TableColumn<Shift, String>endingTimeColumn;
    @FXML
    private TableColumn<Shift, Float> lengthColumn;

    private User user;
    private ModelManager modelManager;

    public HoursTrackingController(ModelManager modelManager, User user) {
        this.modelManager = modelManager;
        this.user = user;
    }

    @FXML
    public void initialize() {
        ObservableList<Shift> data = FXCollections.observableArrayList(modelManager.getShifts().get(user));

        tableHours.setItems(data);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        startingTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startingTime"));
        endingTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endingTime"));
        lengthColumn.setCellValueFactory(new PropertyValueFactory<>("length"));
    }


    /**
     * Callback method for log out button.
     * After clicking this button, a new scene will appear to confirm the user's choice.
     * @param event
     */
    public void logOutClicked(ActionEvent event) {
        Button logOut = (Button) event.getSource();
        Stage stage = (Stage) logOut.getScene().getWindow();


    }
}
