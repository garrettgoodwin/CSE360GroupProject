import java.io.IOException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class CheckoutPage extends SceneController implements Initializable
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
    public TextField asuriteField;
    public TextField cardholderNameField;
    public Button editOrderButton;
    public Button placeOrderButton;
    public ToggleButton deliveryButton;
    public ToggleButton carryoutButton;
    public ToggleButton payWithAsuriteButton;
    public ToggleButton payWithCardButton;
    public ToggleButton reciptButton;
    public ListView<Pizza> myListView;
    public Text totalText;

    String recipt;



    @Override
    public void initialize(URL location, ResourceBundle resorces)
    {
        updateList();  
        totalText.setText(App.session.getOrder().getTotalText());
    }

    public void updateList()
    {
        myListView.getItems().addAll(App.session.getOrder().getPizzas());
    }

    //need to change this event name 
    public void PlaceOrder(ActionEvent event) throws IOException
    {
        String cardNumber = "";
        String cardholderName = "";
        String exp = "";
        int asuriteNumber;
        int cvv;
        int deliveryMethod;

        //reset toggle buttons
        deliveryButton.setSelected(false);
        carryoutButton.setSelected(false);
        payWithAsuriteButton.setSelected(false);
        payWithCardButton.setSelected(false);
        reciptButton.setSelected(false);


        //confirm payment option
        if((payWithAsuriteButton.isSelected() && payWithCardButton.isSelected()) || (!payWithAsuriteButton.isSelected() && !payWithCardButton.isSelected()))
        {
            //if both are selected or neither then return 
            return;
        }
        


        //confirm delivery option 
        if((deliveryButton.isSelected() && carryoutButton.isSelected()) || (!deliveryButton.isSelected() && !carryoutButton.isSelected()))
        {
            //if both are selected or neither then return
            return;
        }else
        {
            //get delivery option
            if(deliveryButton.isSelected())
            {
                deliveryMethod = 1;
            }else
            {
                deliveryMethod = 0;
            }
        }


        //place order if its asurite
        if(payWithAsuriteButton.isSelected())
        {
            //if field is empty
            if(asuriteField.getText().equals(null))
            {
                return;
            }
            asuriteNumber = Integer.parseInt(asuriteField.getText());
            App.session.placeOrder(asuriteNumber, deliveryMethod);
        }

        //place order if its card
        if(payWithCardButton.isSelected())
        {
            if(cardNumberField.getText().equals(null) || expirationDateMonthField.getText().equals(null) || expirationDateYearField.getText().equals(null)  || securityCodeField.getText().equals(null) || cardholderNameField.getText().equals(null))
            {
                //the fields were not filled out
                return;
            }
            cardNumber = cardNumberField.getText();
            exp = expirationDateMonthField.getText();
            exp += expirationDateYearField.getText();
            cardholderName = cardholderNameField.getText();
            cvv = Integer.parseInt(securityCodeField.getText());
            //place order
            App.session.placeOrder(cardNumber, cardholderName, exp, cvv, deliveryMethod);           
        }

        //if user wants a recipt
        if(reciptButton.isSelected())
        {
            App.session.getOrder().getReceipt(); //returns a string
        }

        //move to next page
        SwitchToOrderStatusPage(event);
    }


}
