
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginPage extends SceneController
{

    @FXML
    public TextField username1; //Username input Field
    public TextField password1; //Password Input Field


    //Function is linked to the button on the LoginPage
    //Currently, the only thing is log into 1 of three different account types
    //This needs to be changed and set up with the database users but this just demonstarted functionalitu
    //For Customer, Username: Customer , Password: 1
    //For Processor, Username: Processor , Password: 2
    //For Chef, Username: Chef , Password: 3
    public void Login(ActionEvent event) throws IOException
    {
        System.out.printf("Username : " + username1.getText() + "%n"
                        + "Password : " + password1.getText() + "%n");
        validate(event);
    }

    public void validate(ActionEvent event) throws IOException
    {
        switch(username1.getText() + " " + password1.getText())
        {
            case "Customer 1":
                System.out.printf("LOGGING IN AS CUSTOMER %n");
                SwitchToPizzaSelectionPage(event);
                break;

            case "Processor 2":
                System.out.printf("LOGGING IN AS PROCCESSOR %n");
                SwitchToOrderProcessorPage(event);
                break;

            case "Chef 3":
                System.out.printf("LOGGING IN AS CHEF %n");
                SwitchToChefPage(event);
                //SwitchToChefPage(event);
                break;

            default:
                System.out.printf("Login Unsuccessful %n");
        }
    }

    // public static boolean isValidEmailAddress(String email) {
    //     boolean result = true;
    //     try {
    //        InternetAddress emailAddr = new InternetAddress(email);
    //        emailAddr.validate();
    //     } catch (AddressException ex) {
    //        result = false;
    //     }
    //     return result;
    //  }
}
