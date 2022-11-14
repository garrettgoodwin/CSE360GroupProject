import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class OrderStatusPage extends SceneController implements Initializable
{
    @FXML
    /* Order Statuses */
    public Rectangle acceptedBox;
    public Label acceptedDescription;
    public Label acceptedLabel;

    public Rectangle readyToCookBox;
    public Label readyToCookDescription;
    public Label readyToCookLabel;

    public Rectangle cookingBox;
    public Label cookingDescription;
    public Label cookingLabel;

    public Rectangle completeBox;
    public Label completeDescription;
    public Label completeLabel;

    /* Order Information */
    public Label orderInformationLabel;
    public Label deliverySystemLabel;
    public Label orderTotalLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateStatus();
        /* Order Information */
        Order order = App.session.getOrder();
        orderInformationLabel.setText(getInformationText(order));
        deliverySystemLabel.setText(order.getDeliveryMethodText());
        orderTotalLabel.setText(order.getTotalText());
    }

    private void updateStatus() {
        Order order = App.session.getOrder();
        if (order.isAccepted())
            orderAccepted();
        if (order.isReadyToCook())
            orderReadyToCook();
        if (order.isCooking())
            orderCooking();
        if (order.isReady())
            orderReady();
        if (order.isComplete())
            orderComplete();
    }

    private void orderAccepted() {
        markStatusInProgress(acceptedBox);
        markStatusInProgress(acceptedDescription);
        acceptedDescription.setText("Your order has been accepted, and is being processed before cooking");
    }

    private void orderReadyToCook() {
        markStatusComplete(acceptedBox);
        markStatusComplete(acceptedDescription);
        markStatusInProgress(readyToCookBox);
        markStatusInProgress(readyToCookDescription);
        acceptedDescription.setText("Your order has been accepted");
        readyToCookDescription.setText("Your order is in queue to be baked");
    }

    private void orderCooking() {
        orderReadyToCook();
        markStatusComplete(readyToCookBox);
        markStatusComplete(readyToCookDescription);
        markStatusInProgress(cookingBox);
        markStatusInProgress(cookingDescription);
        readyToCookDescription.setText("Your order has been processed");
        cookingDescription.setText("Your order is being baked by our talented kitchen");
    }

    private void orderReady() {
        orderCooking();
        markStatusComplete(cookingBox);
        markStatusComplete(cookingDescription);
        markStatusInProgress(completeBox);
        markStatusInProgress(completeDescription);
        if (App.session.getOrder().isDelivery()) {
            completeDescription.setText("Your order is out for delivery");
            completeLabel.setText("Out for Delivery");
        } else {
            completeDescription.setText("Your order is ready to be picked up");
            completeLabel.setText("Ready for Pick Up");
        }
    }

    private void orderComplete() {
        orderCooking();
        markStatusComplete(cookingBox);
        markStatusComplete(cookingDescription);
        markStatusComplete(completeBox);
        markStatusComplete(completeDescription);
        cookingDescription.setText("Your order has finished cooking");

        if (App.session.getOrder().isDelivery()) {
            completeDescription.setText("Your pizza has been delivered. Enjoy!");
            completeLabel.setText("Delivered");
        } else {
            completeDescription.setText("Your pizza has been picked up. Enjoy!");
            completeLabel.setText("Picked Up");
        }
    }

    private String getInformationText(Order order) {
        String information = "";
        Pizza[] pizzas = order.getPizzas();
        if (pizzas.length == 0) {
            return "No Pizzas Ordered";
        }
        int pepperoni = 0;
        int cheese = 0;
        int vegetable = 0;
        String delimeter = ", ";
        for (int i = 0; i < pizzas.length; i++) {
            if (pizzas[i].isPepperoni()) {
                pepperoni += pizzas[i].getQuantity();
            } else if (pizzas[i].isCheese()) {
                cheese += pizzas[i].getQuantity();
            } else if (pizzas[i].isVegetable()) {
                vegetable += pizzas[i].getQuantity();
            }
        }
        if (pepperoni > 0) {
            information += pepperoni + " pepperoni" + delimeter;
        }
        if (cheese > 0) {
            information += cheese + " cheese" + delimeter;
        }
        if (vegetable > 0) {
            information += vegetable + " vegetable" + delimeter;
        }
        // remove final delimeter
        information = information.substring(0, information.length() - delimeter.length());
        return information;
    }

    private void markStatusInProgress(Rectangle box) {
        box.getStyleClass().remove("status-not-yet-met");
        box.getStyleClass().add("status-in-progress");
    }

    private void markStatusInProgress(Label label) {
        label.getStyleClass().remove("status-not-yet-met-desc");
    }

    private void markStatusComplete(Rectangle box) {
        box.getStyleClass().remove("status-not-yet-met");
        box.getStyleClass().remove("status-in-progress");
    }

    private void markStatusComplete(Label label) {
        label.getStyleClass().remove("status-not-yet-met-desc");
    }

}