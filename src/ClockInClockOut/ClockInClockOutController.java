package ClockInClockOut;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class ClockInClockOutController {
    /**
     * Method the change the color of the button if the mouse hover through it
     * @param event the MouseEvent
     */
    public void changeBtnBackgroundColor(MouseEvent event) {
//        Get the button
        Button butn = (Button) event.getSource();

        Color backColor = (Color) butn.getBackground().getFills().get(0).getFill();

        butn.setStyle("-fx-background-color: #EDED05");
    }
}
