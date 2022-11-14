import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class ChefPage extends SceneController implements Initializable
{
    @FXML
    public TableView<Order> table; //Links to the table

    // columns of the table
    public TableColumn<Order, String> order;  // Order Number Column
    public TableColumn<Order, String> cookingStatus; // Cooking Status Column
    public TableColumn<Order, String> pizzaType; // Pizza Type Column
    public TableColumn<Order, String> toppings; // Toppings Column
    public TableColumn<Order, String> quantity; // Quantity Column

    // data to add to table
    public final ObservableList<Order> data = FXCollections.observableArrayList();

    // TODO: 
    //    >Move processed orders into table, at first column


    public void test(ActionEvent event) throws IOException {  // test to add and then change it to cooking; leaving page and going back in the page actually shows the orders when changed to cooking
        // create pizzas and order
        App.session.login("customer", "password");
        App.session.addPizza(Pizza.CHEESE, true, true, true, true, 2);
        App.session.addPizza(Pizza.PEPPERONI, true, false, false, true, 2);
        System.out.println("pizzas are added");
        App.session.placeOrder("0000000123456789", "John Customer", "1/1", 1234, 1);
        System.out.println("An order has been added");

        // add the order to the table
        Order order1 = new Order(App.session.getOrderId(), App.session.getOrderStatusText(), "test", "test", 1);
        data.add(order1);
        table.setItems(data);

        // become chef again
        App.session.login("chef", "password");
    }

    public void markToCook(ActionEvent event) {  // marks that chef is cooking the order
        Order clickedOrder = table.getSelectionModel().getSelectedItem();
        if (clickedOrder != null) {
            ObservableList<Order> currentData = table.getItems();
            int currentId = clickedOrder.getOrder();
            for (Order order : currentData) {
                if (order.getOrder() == currentId) {
                    order.setCookingStatus(Order.COOKING_TEXT);
                    App.session.markOrderCooking(order.getOrder());
    
                    table.setItems(currentData);
                    table.refresh();
                }
            }
        }
    }

    public void markReady(ActionEvent event) {  // marks that the order is ready
        Order clickedOrder = table.getSelectionModel().getSelectedItem();
        if (clickedOrder != null) {
            ObservableList<Order> currentData = table.getItems();
            int currentId = clickedOrder.getOrder();
            for (Order order : currentData) {
                if (order.getOrder() == currentId) {
                    order.setCookingStatus(Order.READY_TEXT);
                    App.session.markOrderReady(order.getOrder());
    
                    table.setItems(currentData);
                    table.refresh();
                }
            }
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // columns
        order.setCellValueFactory(new PropertyValueFactory<>("order"));
        cookingStatus.setCellValueFactory(new PropertyValueFactory<>("cookingStatus"));
        pizzaType.setCellValueFactory(new PropertyValueFactory<>("pizzaType"));
        toppings.setCellValueFactory(new PropertyValueFactory<>("toppings"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));


        // load the orders for chef
        Order[] orderArray = App.session.getOrdersForCooking();
        for (int i = 0; i < orderArray.length; i++) {;
            for (int j = 0; j < orderArray[i].getPizzas().length; j++) {
                int order = orderArray[i].getId();
                String cookingStatus = new String(orderArray[i].getStatusText());
                String pizzaType = new String(orderArray[i].getPizzas()[j].getTypeText());
                String toppings = new String(orderArray[i].getPizzas()[j].getToppingsText());
                int quantity = orderArray[i].getPizzas()[j].getQuantity();
                Order orders = new Order(order, cookingStatus, pizzaType, toppings, quantity);
                data.add(orders);
            }
        }

        table.setItems(data);
    }
}
