import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class OrderStatusCheckPage extends SceneController
{
    @FXML
    public TextField orderIdField;
    public TextField usernameField;
    public TextField passwordField;

    public void CheckOrderStatus(ActionEvent event) throws IOException
    {
        /* Reset UI Feedback */
        validEntry(orderIdField);
        validEntry(usernameField);
        validEntry(passwordField);

        if (orderIdField.getText().length() > 0) {
            if (getOrderId() == -1)
                return;
            App.session.setOrderForStatus(getOrderId());
            if (App.session.orderIsBlank()) {
                invalidEntry(orderIdField);
            }
        } else {
            App.session.setOrderForStatus(usernameField.getText(), passwordField.getText());
            if (App.session.orderIsBlank()) {
                invalidEntry(usernameField);
                invalidEntry(passwordField);
            }
        }
        if (!App.session.orderIsBlank()) {
            SwitchToOrderStatusPage(event);
        }
    }

    private int getOrderId() {
        try {
            if (Integer.parseInt(orderIdField.getText()) <= 0) {
                return -1;
            }
        } catch (Exception e) {
            return -1;
        }
        return Integer.parseInt(orderIdField.getText());
    }
}