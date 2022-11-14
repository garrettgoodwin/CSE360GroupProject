
import java.io.IOException;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class PizzaSelectionPage extends SceneController implements Initializable
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
    public ListView<Pizza> myListView;


    int quantity = 1;
    int pizzaType = 3; 
    boolean hasMushroom;
    boolean hasOnion;
    boolean hasExtraCheese;
    boolean hasOlive;

 
    @Override
    public void initialize(URL location, ResourceBundle resorces)
    {
        updateList();  
    }

  
    public void updateList()
    {
      myListView.getItems().clear();
      myListView.getItems().addAll(App.session.getOrder().getPizzas());
    }


    public void RemovePizza(ActionEvent event) throws IOException
    {
      
     // App.session.removePizza(pizzaType); 
      Pizza deletedPizza = myListView.getSelectionModel().getSelectedItem();
      App.session.removePizza(deletedPizza.id);
      updateList();
    }


    public void AddPizza(ActionEvent event) throws IOException
    {

      //START OF INGREDIENT CHECK
      //START OF PIZAA TYPE
      //check for pepperoni
      if(pepperoniButton.isSelected())
      {
        pizzaType = 0;
        pepperoniButton.setSelected(false);
      }

      //check for cheese
      if(cheeseButton.isSelected())
      {
       pizzaType = 2; 
       cheeseButton.setSelected(false);
      }

      //check for vegitable
      if(vegetableButton.isSelected())
      {
        pizzaType = 1;
        vegetableButton.setSelected(false);
      }
      //END OF PIZZA TYPE

      //check for valid pizza type
      if(pizzaType == 3)
      {
        //print error message
        return;
      }
      
      //START OF TOPPINGS
      //check for extra cheese
      if(extraCheeseButton.isSelected())
      {
        hasExtraCheese = true;
        extraCheeseButton.setSelected(false);
      }else
      {
        hasExtraCheese = false;
      }

      //check for mushroom
      if(mushroomButton.isSelected())
      {
        hasMushroom = true;
        mushroomButton.setSelected(false);
      }else
      {
        hasMushroom = false;
      }

      //check for olive
      if(oliveButton.isSelected())
      {
        hasOlive = true;
        oliveButton.setSelected(false);
      }else
      {
        hasOlive = false;
      }

      //check for onion
      if(onionButton.isSelected())
      {
        hasOnion = true;
        onionButton.setSelected(false);
      }else
      {
        hasOnion = false;
      }
      //END OF TOPPINGS
      //END OF INGREDIENT CHECK

      
       //add pizza to user order then update display table
      int newPizzaID = App.session.addPizza(pizzaType, hasMushroom, hasOlive, hasOnion, hasExtraCheese, quantity);
      pizzaType = 3;
      updateList();

    }
}
