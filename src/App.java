import java.security.Principal;
import java.util.Optional;

import javax.management.OperationsException;

import org.w3c.dom.html.HTMLTableRowElement;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
 
public class App //extends Application
{
    //All current test scenes >All pretty much temporary
    // Scene scene1, scene2, scene3, scene4, scene5, scene6, scene7, scene8;

    public static void main(String[] args) {
        // create guest session
        Session session = new Session();
        // log in
        session.login("CUSTOMER", "password");

        System.out.println(session.getUser().getName() + " is Now Logged In.");
        if (session.user.isGuest()) {
            System.out.println(session.getUser().getName() + " is a guest");
        }
        if (session.user.isCustomer()) {
            System.out.println(session.getUser().getName() + " is a customer");
        }
        if (session.user.isEmployee()) {
            System.out.println(session.getUser().getName() + " is an employee");
        }
        if (session.user.isAdmin()) {
            System.out.println(session.getUser().getName() + " is an admin");
        }
        if (session.user.isChef()) {
            System.out.println(session.getUser().getName() + " is a chef");
        }
        if (session.user.isOrderProcessor()) {
            System.out.println(session.getUser().getName() + " is an order processor");
        }
        // launch(args);
    }
    
    // @Override
    // public void start(Stage primaryStage) {

    //     //Switch between two different scenes
    //     //Added labels to differenitaite between scenes
    //     //Label lbl = new Label();
    //     //lbl.setText("FIrst Scene");
    //     //Label lbl2 = new Label();
    //     //lbl2.setText("Second Scene");

    //     //Button btn = new Button();
    //     //btn.setText("Go to Scene 2");
    //     //btn.setOnAction(e -> primaryStage.setScene(scene2));

    //     //Button btn2 = new Button();
    //     //btn2.setText("Go to Scene 1");
    //     //btn2.setOnAction(e -> primaryStage.setScene(scene1));


    //     //Displays a popup window that must be closed before proceeding to original window
    //     //Button btn3 = new Button();
    //     //btn3.setOnAction(e -> AlertBox.Display("Alert", "A good ole' popup"));

    //     //Button btn4 = new Button();
    //     //btn4.setOnAction(e -> 
    //     //{
    //     //    boolean answer = ConfirmBox.Display("Option", "Pick Yes or No");
    //     //    System.out.println(answer);
    //     //});



    //     //Deals with prompting the user if htey meant to exit the program
    //     //Also could be used to save something before exiting the program
    //     primaryStage.setOnCloseRequest(e ->
    //     {
    //         e.consume();
    //         CloseProgram();
    //     });
    //     Button btn5 = new Button();
    //     btn5.setOnAction(e -> CloseProgram());


    //     //Define scene 1
    //     //VBox layout = new VBox(20);
    //     //layout.getChildren().addAll(lbl, btn, btn3, btn5);
    //     //scene1 = new Scene(layout, 500, 500);

    //     //Define Scene 2 -StackPane
    //     //StackPane layout2 = new StackPane();
    //     //layout2.getChildren().addAll(lbl2, btn2, btn4);
    //     //scene2 = new Scene(layout2, 500, 500);

    //     //Define Scene 3 -BorderPane (Embedded layout)
    //     HBox topLayout = new HBox();
    //     Button btn6 = new Button("File");
    //     btn6.setOnAction(e -> System.out.print("File"));
    //     Button btn7 = new Button("Edit");
    //     btn7.setOnAction(e -> System.out.print("Edit"));
    //     Button btn8 = new Button("View");
    //     btn8.setOnAction(e -> System.out.print("View"));
    //     topLayout.getChildren().addAll(btn6, btn7, btn8);

    //     VBox leftLayout = new VBox(10);
    //     Button btn9 = new Button("9");
    //     btn9.setOnAction(e -> System.out.print("9"));
    //     Button btn10 = new Button("10");
    //     btn10.setOnAction(e -> System.out.print("10"));
    //     Button btn11 = new Button("11");
    //     btn11.setOnAction(e -> System.out.print("11"));
    //     leftLayout.getChildren().addAll(btn9, btn10, btn11);

    //     BorderPane borderPane = new BorderPane();
    //     borderPane.setTop(topLayout);
    //     borderPane.setLeft(leftLayout);

    //     scene3 = new Scene(borderPane, 600, 600);


    //     //Define Scene 4 -GridPane (Embedded Layout)
    //     GridPane gridLayout = new GridPane();

    //     //Gridpane spacing
    //     gridLayout.setPadding(new Insets(10,10,10,10));
        
    //     //Individual cell spacing
    //     gridLayout.setVgap(8);
    //     gridLayout.setHgap(10);

    //     //Name label 
    //     Label usernameLabel = new Label("Username:");
    //     GridPane.setConstraints(usernameLabel, 0, 0);

    //     TextField usernameInput = new TextField();
    //     usernameInput.setPromptText("Username...");
    //     GridPane.setConstraints(usernameInput, 1, 0);

    //     Label passwordLabel = new Label("Password:");
    //     GridPane.setConstraints(passwordLabel, 0, 1);

    //     TextField passwordInput = new TextField();
    //     passwordInput.setPromptText("Password...");
    //     GridPane.setConstraints(passwordInput, 1, 1);

    //     Button loginButton = new Button("Login");
    //     GridPane.setConstraints(loginButton, 1, 2);
    //     loginButton.setOnAction(e ->
    //     {
    //         boolean answer = IsInt(usernameInput, usernameInput.getText());
    //         System.out.println(usernameInput.getText());
    //     });

    //     gridLayout.getChildren().addAll(usernameLabel, usernameInput, passwordInput, passwordLabel, loginButton);
    //     scene4 = new Scene(gridLayout, 600, 600);


    //     //Define Scene 5
    //     //Checkbox scene
    //     CheckBox option1CB = new CheckBox("Option #1");
    //     CheckBox option2CB = new CheckBox("Option #2");
    //     Button btn12 = new Button("Confirm Options");
    //     btn12.setOnAction(e ->
    //     {
    //         HandleOptions(option1CB, option2CB);
    //     });
    //     option2CB.setSelected(true);
    //     VBox checkBoxLayout = new VBox(10);
    //     checkBoxLayout.getChildren().addAll(option1CB, option2CB, btn12);
    //     scene5 = new Scene(checkBoxLayout, 600, 600);


    //     //Define Scene 6
    //     //ChoiceBoxes seem to be a worse version of a combobox
    //     ChoiceBox<String> dropDownMenu = new ChoiceBox<>();
    //     dropDownMenu.getItems().addAll("A", "B", "C", "D");
    //     dropDownMenu.setValue("A");
    //     Button btn13 = new Button("Confirm");
    //     btn13.setOnAction(e -> GetChoice(dropDownMenu));

    //     //Listen for choicebox selection change
    //     dropDownMenu.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> System.out.println(newValue));
    //     VBox choiceBoxLayout = new VBox(10);
    //     choiceBoxLayout.getChildren().addAll(dropDownMenu, btn13);
    //     scene6 = new Scene(choiceBoxLayout, 600, 600);


    //     //Define Scene 7
    //     //Better than ChoiceBox as it does not need a listener and has other functionalities
    //     ComboBox<String> comboBox = new ComboBox<>();
    //     comboBox.getItems().addAll("A", "B", "C", "D");
    //     comboBox.setPromptText("Choose Letter");
    //     comboBox.setOnAction(e ->
    //     {
    //         System.out.println("User Selected:" + comboBox.getValue());
    //     });
    //     Button btn14 = new Button("Confirm");
    //     btn14.setOnAction(e -> GetChoice(comboBox));

    //     Button btn15 = new Button("Login as Customer");
    //     btn15.setOnAction(e ->
    //     {
    //         Customer customer = new Customer();
    //         int numb = customer.getCustomerNumb();
    //         System.out.println(numb);
    //     });

    //     Button btn16 = new Button("Login as Chef");
    //     btn16.setOnAction(e ->
    //     {
    //         Chef chef = new Chef();
    //         int numb = chef.getPersonNumb();
    //         System.out.println(numb);
    //     });

    //     Button btn17 = new Button("Login as Order Proccessor");
    //     btn17.setOnAction(e ->
    //     {
    //         OrderProccessor orderProccessor = new OrderProccessor();
    //         int numb = orderProccessor.getOrderProccessorNumb();
    //         System.out.println(numb);
    //     });

    //     //Can also make a combobox editable
    //     //comboBox.setEditable(true);

    //     VBox comboBoxLayout = new VBox(10);
    //     comboBoxLayout.getChildren().addAll(comboBox, btn14, btn15, btn16, btn17);
    //     scene7 = new Scene(comboBoxLayout, 600, 600);


    //     //Initializes the first Scene on the stage (window), sets name on window, and displays 
    //     //stage to user 
    //     //For testing purpses, I was just throwing in the scene I wasnted to view
    //     //in here. This will be changed when they all get linked together and we start actually creating
    //     //scenes
    //     LandingPage landPage = new LandingPage(primaryStage);
    //     primaryStage.setScene(landPage.scene1);
    //     primaryStage.setTitle("Hello World!");
    //     primaryStage.show();
    // }


    // //Gets the vlaue of a choicebox with a button event
    // //Function is pretty much useless as the combobox arg one is much better
    // private void GetChoice(ChoiceBox<String> dropDownMenu)
    // {
    //     String option = dropDownMenu.getValue();
    //     System.out.println(option);
    // }

    // private void GetChoice(ComboBox<String> comboBox)
    // {
    //     String option = comboBox.getValue();
    //     System.out.println(option);
    // }

    // //Function prompts the user if they are sure that they want to exit the program
    // public void CloseProgram()
    // {
    //     Boolean answer = ConfirmBox.Display("Exit Program", "Are you sure you would like to exit?");
    //     if(answer)
    //     {
    //         Platform.exit();
    //     }
    // }


    // //Displays what 
    // private void HandleOptions(CheckBox optionOne, CheckBox optionTwo)
    // {
    //     String message = "";

    //     if(optionOne.isSelected())
    //     {
    //         message += "Option 1 ";
    //     }
    //     if(optionTwo.isSelected())
    //     {
    //         message += "Option 2 ";
    //     }

    //     System.out.println(message);
    // }



    // //Function demonstrates taking in user input from a textfield and validating it
    // private boolean IsInt(TextField input, String message)
    // {
    //     boolean result = false;
    //     try
    //     {
    //         int answer = Integer.parseInt(message);
    //         result = true;
    //     }
    //     catch(NumberFormatException e)
    //     {
    //         System.out.println("Editor: " + message + "is not an integer");
    //     }
    //     return result;
    // }

}
