import java.security.Principal;
import java.security.KeyStore.TrustedCertificateEntry;
import java.util.Optional;
import javax.management.OperationsException;
import org.w3c.dom.html.HTMLTableRowElement;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

 
public class App extends Application
{
    // All current test scenes >All pretty much temporary
    Scene scene1, scene2, scene3, scene4, scene5, scene6, scene7, scene8;
    static Session session = new Session();

    public static void main(String[] args) 
    {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {

        try
        {
            //OrderProcessorPage
            Parent root = FXMLLoader.load(getClass().getResource("LandingPage.fxml"));
            //getClass().getClassLoader().getResource("ui_layout.fxml")
            //Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("OrderProcessorPage.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("Styling.css").toExternalForm());

            //Scenes sceneTest = new Scenes();
            primaryStage.setScene(scene);
            primaryStage.setTitle("SunDevil's Pizza");
            primaryStage.setMaximized(true);
            //primaryStage.setFullScreen(true);
            primaryStage.show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    //Gets the vlaue of a choicebox with a button event
    //Function is pretty much useless as the combobox arg one is much better
    private void GetChoice(ChoiceBox<String> dropDownMenu)
    {
        String option = dropDownMenu.getValue();
        System.out.println(option);
    }

    private void GetChoice(ComboBox<String> comboBox)
    {
        String option = comboBox.getValue();
        System.out.println(option);
    }

    //Function prompts the user if they are sure that they want to exit the program
    public void CloseProgram()
    {
        Boolean answer = ConfirmBox.Display("Exit Program", "Are you sure you would like to exit?");
        if(answer)
        {
            Platform.exit();
        }
    }


    //Displays what 
    private void HandleOptions(CheckBox optionOne, CheckBox optionTwo)
    {
        String message = "";

        if(optionOne.isSelected())
        {
            message += "Option 1 ";
        }
        if(optionTwo.isSelected())
        {
            message += "Option 2 ";
        }

        System.out.println(message);
    }



    //Function demonstrates taking in user input from a textfield and validating it
    private boolean IsInt(TextField input, String message)
    {
        boolean result = false;
        try
        {
            int answer = Integer.parseInt(message);
            result = true;
        }
        catch(NumberFormatException e)
        {
            System.out.println("Editor: " + message + "is not an integer");
        }
        return result;
    }

}
