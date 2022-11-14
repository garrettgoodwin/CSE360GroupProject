
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Rectangle;
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
    public Label pricePerToppingLabel;
    public Label totalLabel;

    public Rectangle pepperoniBox;
    public Rectangle cheeseBox;
    public Rectangle vegetableBox;
    public Rectangle mushroomBox;
    public Rectangle onionBox;
    public Rectangle extraCheeseBox;
    public Rectangle oliveBox;


    int quantity = 1;
    int pizzaType = Pizza.PEPPERONI; 
    boolean hasMushroom;
    boolean hasOnion;
    boolean hasExtraCheese;
    boolean hasOlive;

 
    @Override
    public void initialize(URL location, ResourceBundle resorces)
    {
        pricePerToppingLabel.setText("Toppings (+" + getPriceText(Pizza.TOPPING_PRICE) + " per topping)");
        updateList();
    }

  
    public void updateList()
    {
      totalLabel.setText("Total: " + App.session.getOrder().getTotalText());
      myListView.getItems().clear();
      myListView.getItems().addAll(App.session.getOrder().getPizzas());
      pepperoniButton.setSelected(true);
      resetSelections();
    }


    public void RemovePizza(ActionEvent event) throws IOException
    {
     // App.session.removePizza(pizzaType); 
      Pizza deletedPizza = myListView.getSelectionModel().getSelectedItem();
      App.session.removePizza(deletedPizza.getId());
      updateList();
    }


    public void AddPizza(ActionEvent event) throws IOException
    {
      //START OF INGREDIENT CHECK
      //START OF PIZAA TYPE
      //check for pepperoni
      if(pepperoniButton.isSelected())
      {
        pizzaType = Pizza.PEPPERONI;
        pepperoniButton.setSelected(false);
      }

      //check for cheese
      if(cheeseButton.isSelected())
      {
       pizzaType = Pizza.CHEESE; 
       cheeseButton.setSelected(false);
      }

      //check for vegitable
      if(vegetableButton.isSelected())
      {
        pizzaType = Pizza.VEGETABLE;
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
      pizzaType = Pizza.PEPPERONI;
      updateList();

    }

    private void resetBox(Rectangle box, ToggleButton button) {
      if (button.isSelected()) {
        selectBox(box);
      } else {
        unselectBox(box);
      }
    }

    public void selectPepperoni() {
      resetBox(pepperoniBox, pepperoniButton);
      if (pepperoniButton.isSelected()) {
        cheeseButton.setSelected(false);
        resetBox(cheeseBox, cheeseButton);
        vegetableButton.setSelected(false);
        resetBox(vegetableBox, vegetableButton);
      }
      if (!cheeseButton.isSelected() && !vegetableButton.isSelected()) {
        pepperoniButton.setSelected(true);
        resetBox(pepperoniBox, pepperoniButton);
      }
    }
    public void selectCheese() {
      resetBox(cheeseBox, cheeseButton);
      if (cheeseButton.isSelected()) {
        pepperoniButton.setSelected(false);
        resetBox(pepperoniBox, pepperoniButton);
        vegetableButton.setSelected(false);
        resetBox(vegetableBox, vegetableButton);
      }
    }
    public void selectVegetable() {
      resetBox(vegetableBox, vegetableButton);
      if (vegetableButton.isSelected()) {
        pepperoniButton.setSelected(false);
        resetBox(pepperoniBox, pepperoniButton);
        cheeseButton.setSelected(false);
        resetBox(cheeseBox, cheeseButton);
      }
    }

    public void selectOnion() {
      resetBox(onionBox, onionButton);
    }
    public void selectMushroom() {
      resetBox(mushroomBox, mushroomButton);
    }
    public void selectExtraCheese() {
      resetBox(extraCheeseBox, extraCheeseButton);
    }
    public void selectOlive() {
      resetBox(oliveBox, oliveButton);
    }

    private void selectBox(Rectangle box) {
      String className = "selected-option";
      if (!box.getStyleClass().contains(className))
        box.getStyleClass().add(className);
    }

    private void unselectBox(Rectangle box) {
      String className = "selected-option";
      while (box.getStyleClass().contains(className)) {
        box.getStyleClass().remove(className);
      }
    }

    private void resetSelections() {
      //Pizza Type
      resetBox(pepperoniBox, pepperoniButton);
      resetBox(cheeseBox, cheeseButton);
      resetBox(vegetableBox, vegetableButton);

      // Toppings
      resetBox(mushroomBox, mushroomButton);
      resetBox(onionBox, onionButton);
      resetBox(extraCheeseBox, extraCheeseButton);
      resetBox(oliveBox, oliveButton);
    }
}
