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
    public ToggleButton receiptButton;
    public ListView<Pizza> myListView;
    public Text totalText;

    String recipt;



    @Override
    public void initialize(URL location, ResourceBundle resorces)
    {
        updateList();  
        carryoutButton.setSelected(true);
        totalText.setText(App.session.getOrder().getTotalText());
    }

    public void updateList()
    {
        myListView.getItems().addAll(App.session.getOrder().getPizzas());
    }

    //need to change this event name 
    public void PlaceOrder(ActionEvent event) throws IOException
    {
        resetUI();

        String cardNumber = "";
        String cardholderName = "";
        String exp = "";
        int asuriteNumber;
        int cvv;
        int deliveryMethod;
        


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
                deliveryMethod = Order.DELIVERY;
                if (streetAddressField.getText().length() == 0) {
                    invalidEntry(streetAddressField);
                    return;
                }
                if (zipCodeField.getText().length() == 0) {
                    invalidEntry(zipCodeField);
                    return;
                }
                if (cityField.getText().length() == 0) {
                    invalidEntry(cityField);
                    return;
                }
            }else
            {
                deliveryMethod = Order.PICK_UP;
            }
        }


        Response response;
        //place order if its asurite
        if(asuriteField.getText().length() > 0)
        {
            asuriteNumber = Integer.parseInt(asuriteField.getText());
            response = App.session.placeOrder(asuriteNumber, deliveryMethod);
            if (!Response.ok(response)) {
                invalidAsurite(response);
            }
        } else {
            /* Pay with Card */
            if (cardNumberField.getText().length() == 0) {
                invalidEntry(cardNumberField);
                return;
            }
            if (expirationDateMonthField.getText().length() == 0) {
                invalidEntry(expirationDateMonthField);
                return;
            }
            if (expirationDateYearField.getText().length() == 0) {
                invalidEntry(expirationDateYearField);
                return;
            }
            if (cardholderNameField.getText().length() == 0) {
                invalidEntry(cardholderNameField);
                return;
            }
            if (securityCodeField.getText().length() == 0) {
                invalidEntry(securityCodeField);
                return;
            }

            cardNumber = cardNumberField.getText();
            exp = expirationDateMonthField.getText();
            exp += "/" + expirationDateYearField.getText();
            cardholderName = cardholderNameField.getText();
            cvv = Integer.parseInt(securityCodeField.getText());
            //place order
            response = App.session.placeOrder(cardNumber, cardholderName, exp, cvv, deliveryMethod); 
            if (!Response.ok(response)) {
                invalidPaymentMethod(response);
            }          
        }

        //if user wants a recipt
        if(receiptButton.isSelected())
        {
            App.session.sendEmailToCustomer(App.session.getOrderId()); //returns a string
        }

        //reset toggle buttons
        deliveryButton.setSelected(false);
        carryoutButton.setSelected(false);
        receiptButton.setSelected(false);

        //move to next page
        if (Response.ok(response)) {
            SwitchToOrderStatusPage(event);
        }
    }

    private void invalidAsurite(Response response) {
        asuriteField.setText("");
        invalidEntry(asuriteField);
    }

    private void invalidPaymentMethod(Response response) {
        if (response == Response.INVALID_CARDNUMBER) {
            invalidEntry(cardNumberField);
        } else if (response == Response.CARD_EXPIRED) {
            invalidEntry(expirationDateMonthField);
            invalidEntry(expirationDateYearField);
        } else if (response == Response.INVALID_CVV) {
            invalidEntry(securityCodeField);
        } 
            
    }

    private void resetUI() {
        validEntry(asuriteField);
        validEntry(cardNumberField);
        validEntry(expirationDateMonthField);
        validEntry(expirationDateYearField);
        validEntry(cardholderNameField);
        validEntry(securityCodeField);
        validEntry(billingZipCodeField);
    }


}
