
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginPage extends SceneController
{

    @FXML
    public TextField username1; //Username input Field
    public TextField password1; //Password Input Field
    public Label errorLabel;    //Error Label


    //Function is linked to the button on the LoginPage
    //Currently, the only thing is log into 1 of three different account types
    //This needs to be changed and set up with the database users but this just demonstarted functionalitu
    //For Customer, Username: Customer , Password: 1
    //For Processor, Username: Processor , Password: 2
    //For Chef, Username: Chef , Password: 3
    public void Login(ActionEvent event) throws IOException
    {
        String username = username1.getText();
        String password = password1.getText();
        Response response = App.session.login(username, password);

        if (Response.ok(response)) {
            /* Login Successful */
            /* Determine User Type */

            // Chef
            if (App.session.isChef()) {
                SwitchToChefPage(event);
                return;
            }
            // Order Processor
            if (App.session.isOrderProcessor()) {
                SwitchToOrderProcessorPage(event);
                return;
            }
            // Admin
            if (App.session.isAdmin()) {
                SwitchToOrderProcessorPage(event);
                return;
            }
            // Customer
            SwitchToPizzaSelectionPage(event);
        } else {
            /* Invalid Login Attempt */
            invalidLogin(response);
        }
    }

    public void OrderAsGuest(ActionEvent event) throws IOException 
    {
        App.session.continueAsGuest();
        SwitchToPizzaSelectionPage(event);
    }

    private void invalidLogin(Response response)
    {
        /* reset styling */
        validEntry(username1);
        validEntry(password1);
        /* feedback on entry */
        if (User.Username.isUsernameResponse(response)) {
            invalidEntry(username1);
        }
        if (User.Password.isPasswordResponse(response)) {
            invalidEntry(password1);
        }
        /* feedback message */
        errorLabel.setText(response.message);
    }

}
