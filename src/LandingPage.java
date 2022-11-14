import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;


public class LandingPage extends SceneController implements Initializable
{
    @FXML
    public Button accountButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (App.session.isLoggedIn()) {
            accountButton.setText(App.session.getUser().getFirstName() + " >");
        }
    }

    public void AccountButtonPressed(ActionEvent event) throws IOException
    {
        // customer or guest
        if (App.session.isCustomer()) {
            GoToOrder(event);
        } else {
            // logged in as order processor, chef, or admin
            if (App.session.isOrderProcessor()) {
                SwitchToOrderProcessorPage(event);
            } else if (App.session.isChef()) {
                SwitchToChefPage(event);
            } else if (App.session.isAdmin()) {
                SwitchToAdminPage(event);
            } 
        }
    }

    public void GoToOrder(ActionEvent event) throws IOException
    {
        if (App.session.isLoggedIn()) {
            if (App.session.getOrder().isPlaced()) {
                SwitchToOrderStatusPage(event);
            } else {
                SwitchToPizzaSelectionPage(event);
            }
        } else {
            SwitchToLoginPage(event);
        }
    }
}
