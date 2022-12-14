import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
 
public class ConfirmBox 
{
    static boolean answer;



    public static boolean Display(String title, String message)
    {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(message);

        Button confirmButton = new Button("Yes");
        confirmButton.setOnAction(e ->
        {
            answer = true;
            window.close();;
            
        });

        Button DenyButton = new Button("No");
        DenyButton.setOnAction(e ->
        {
            answer = false;
            window.close();




        });



        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, confirmButton, DenyButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }
}

