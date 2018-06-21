package ClockInClockOut;

import ClockInClockOut.Controller.LogInController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader logIn = new FXMLLoader(getClass().getResource("/StagesAndScenes/logIn.fxml"));
            logIn.setController(new LogInController());

            primaryStage.setTitle("Trung tâm anh ngữ SEA");
            primaryStage.setScene(new Scene(logIn.load()));

            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
