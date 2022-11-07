import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController
{
    private Stage stage;
    private Scene scene;
    private Parent root;
 
    public void SwitchToLandingPage(ActionEvent event) throws IOException
    {
        root = FXMLLoader.load(getClass().getResource("LandingPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void SwitchToLoginPage(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void SwitchToPizzaSelectionPage(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("PizzaSelectionPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void SwitchToOrderStatusPage(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("OrderStatusPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void SwitchToOrderProcessorPage(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("OrderProcessorPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void SwitchToChefPage(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("ChefPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void SwitchToCheckoutPage(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("CheckoutPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
