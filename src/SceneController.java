import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
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
        scene.getStylesheets().add(getClass().getResource("Styling.css").toExternalForm());
        stage.setScene(scene);
        stage.setMaximized(true);
        //stage.setFullScreen(true);
        stage.show();
    }

    public void Test(ActionEvent event) throws IOException
    {
        System.out.println("Entered Test");
    }

    public void SwitchToLoginPage(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("Styling.css").toExternalForm());
        stage.setScene(scene);
        stage.setMaximized(true);
        //stage.setFullScreen(true);
        stage.show();
    }

    public void SwitchToPizzaSelectionPage(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("PizzaSelectionPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("Styling.css").toExternalForm());
        stage.setScene(scene);
        stage.setMaximized(true);
        //stage.setFullScreen(true);
        stage.show();
    }

    public void SwitchToOrderStatusPage(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("OrderStatusPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("Styling.css").toExternalForm());
        stage.setScene(scene);
        stage.setMaximized(true);
        //stage.setFullScreen(true);
        stage.show();
    }

    public void SwitchToOrderStatusCheckPage(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("OrderStatusCheckPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("Styling.css").toExternalForm());
        stage.setScene(scene);
        stage.setMaximized(true);
        //stage.setFullScreen(true);
        stage.show();
    }

    public void SwitchToOrderProcessorPage(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("OrderProcessorPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("Styling.css").toExternalForm());
        stage.setScene(scene);
        stage.setMaximized(true);
        //stage.setFullScreen(true);
        stage.show();
    }

    public void SwitchToChefPage(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("ChefPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("Styling.css").toExternalForm());
        stage.setScene(scene);
        stage.setMaximized(true);
        //stage.setFullScreen(true);
        stage.show();
    }

    public void SwitchToAdminPage(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("AdminPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("Styling.css").toExternalForm());
        stage.setScene(scene);
        stage.setMaximized(true);
        //stage.setFullScreen(true);
        stage.show();
    }

    public void SwitchToSignUpPage(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("SignUpPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("Styling.css").toExternalForm());
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public void SwitchToCheckoutPage(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("CheckoutPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("Styling.css").toExternalForm());
        stage.setScene(scene);
        stage.setMaximized(true);
        //stage.setFullScreen(true);
        stage.show();
    }

    public void SwitchToMenuPage(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("MenuPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("Styling.css").toExternalForm());
        stage.setScene(scene);
        stage.setMaximized(true);
        //stage.setFullScreen(true);
        stage.show();
    }

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



    //All loginpage functions
    // public void Login(ActionEvent event) throws IOException
    // {
    //     System.out.printf("Username : " + username1.getText() + "%n"
    //                     + "Password : " + password1.getText() + "%n");

    //     validate(event);
    // }

    
    // public void validate(ActionEvent event) throws IOException
    // {
    //     switch(username1.getText() + " " + password1.getText())
    //     {
    //         case "Customer 1":
    //             System.out.printf("LOGGING IN AS CUSTOMER %n");
    //             SwitchToPizzaSelectionPage(event);
    //             break;

    //         case "Processor 2":
    //             System.out.printf("LOGGING IN AS PROCCESSOR %n");
    //             SwitchToOrderProcessorPage(event);
    //             break;

    //         case "Chef 3":
    //             System.out.printf("LOGGING IN AS CHEF %n");

    //             //SwitchToChefPage(event);
    //             break;

    //         default:
    //             System.out.printf("Login Unsuccessful %n");
    //     }
    // }

    public void invalidEntry(TextField textField) {
        textField.getStyleClass().add("invalid-entry");
    }

    public void validEntry(TextField textField) {
        textField.getStyleClass().remove("invalid-entry");
    }

}