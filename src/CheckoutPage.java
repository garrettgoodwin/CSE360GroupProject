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

public class CheckoutPage extends SceneController
{
    @FXML
    public TextField cardNumberField;
    public TextField expirationDateMonthField;
    public TextField expirationDateYearField;
    public TextField securityCodeField;
    public TextField billingZipCodeField;
    public TextField streetAddressField;
    public TextField zipCodeField;
    public TextField cityField;
    public Button deliveryButton;
    public Button carryoutButton;
    public Button editOrderButton;
    public Button placeOrderButton;
}
