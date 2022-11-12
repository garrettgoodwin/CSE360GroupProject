import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ChefPage extends SceneController
{
    @FXML
    public TableView table; //Links to the table

    public TableColumn column;  //First Chef Column
    public TableColumn column1; //Incoming orders column

    // TODO: 
    //    >Move processed orders into table, at first column

}
