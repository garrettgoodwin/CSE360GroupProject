import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.print.attribute.standard.NumberUpSupported;
import javax.print.attribute.standard.PrinterMessageFromOperator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
public class OrderProcessorPage extends SceneController implements Initializable
{

    public ObservableList<Order> orders = FXCollections.observableArrayList();
    @FXML public ListView<Order> orderListView1;
    Order[] unprocessedOrders;

    @FXML public Button acceptButton;
    @FXML public Button declineButton;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
       Pizza p = new Pizza(0,1,false,false,false,false,2);
       ArrayList<Pizza> pList = new ArrayList<>();
       pList.add(p);
       Order o = new Order(10, pList, 1, 0, true);
       orders.add(o);
       orderListView1.setItems(orders);
    }

    public OrderProcessorPage()
    {

    }


    public void AcceptOrder()
    {
        System.out.print("Accepted Order");
    }

    public void DeclineOrder()
    {
        System.out.print("Declined Order");
    }


    public void UpdateOrders()
    {
        // TEST
        App.session.placeOrder("1111111111111111", "Garrett Goodwin", "12/23", 123);
        unprocessedOrders = App.session.getOrdersForProcessing();

        for(int i = 0; i < unprocessedOrders.length; i++)
        {
            unprocessedOrders[i].toString();
        }
    }

    public void AddToListView()
    {
        //orderListView1.getItems().add("ITS A PIZZA");
        //orders.add("Hello World");
    }


    public void AddOrderTest()
    {
        AddToListView();
        //Response response = App.session.placeOrder("cardNumber", "cardholderName", "exp", 111);
    }


    // public void TEMPAddOrder()
    // {



    //     Order newOrder = new Order();


        // int arraySize = unprocessedOrders.length;
        // Order newarr[] = new Order[arraySize + 1];

        // for (int i = 0; i < arraySize; i++)
        //     newarr[i] = unprocessedOrders[i];
    
        // newarr[arraySize] = newOrder;
    // }
    



    //Looking into th elistview bc I think its what we want for this
}
