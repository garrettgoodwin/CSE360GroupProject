
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

public class PizzaSelectionPage extends SceneController
{

  //These should be toggle buttons but too lazy
  //to change it rn
    @FXML
    public Button removePizzaButton;
    public Button checkoutButton;
    public Button addPizzaButton;
    public ToggleButton pepperoniButton;
    public ToggleButton cheeseButton;
    public ToggleButton vegetableButton;
    public ToggleButton mushroomButton;
    public ToggleButton onionButton;
    public ToggleButton extraCheeseButton;
    public ToggleButton oliveButton;






      //List to link view
    // for (int i=1;i<=5;i++)
    //  {
    //    //Concatinate each loop 
    //    fieldContent.append("    Field "+i+"\n");
    //  }
    //  ta.setText(fieldContent.toString());
    //MOVE CLASSES UNDER ME
    public void AddPizza(ActionEvent event) throws IOException
    {
       // System.out.println("U CLICKED ME");
        //private StringBuilder fieldContent = new StringBuilder(""); 
    }
}
