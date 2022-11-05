import javax.swing.LookAndFeel;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
 
public class LandingPage
{
    public Scene scene1, scene2;
    Stage primaryStage;

    public LandingPage(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
    }


    private Scene CreateLandingPage()
    {
        Label lbl = new Label();
        lbl.setText("FIrst Scene");
        Label lbl2 = new Label();
        lbl2.setText("Second Scene");

        Button btn = new Button();
        btn.setText("Go to Scene 2");
        btn.setOnAction(e -> primaryStage.setScene(scene2));

        Button btn2 = new Button();
        btn2.setText("Go to Scene 1");
        btn2.setOnAction(e -> primaryStage.setScene(scene1));

        //Displays a popup window that must be closed before proceeding to original window
        Button btn3 = new Button();
        btn3.setOnAction(e -> AlertBox.Display("Alert", "A good ole' popup"));

        Button btn4 = new Button();
        btn4.setOnAction(e -> 
        {
            boolean answer = ConfirmBox.Display("Option", "Pick Yes or No");
            System.out.println(answer);
        });
        
        //Define scene 1
        VBox layout = new VBox(20);
        layout.getChildren().addAll(lbl, btn, btn3);
        scene1 = new Scene(layout, 500, 500);

        //Define Scene 2 -StackPane
        StackPane layout2 = new StackPane();
        layout2.getChildren().addAll(lbl2, btn2, btn4);

        Scene landingPage = new Scene(layout, 600, 600);
        return landingPage;
    }

}