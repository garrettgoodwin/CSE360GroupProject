import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
public class AdminPage extends SceneController implements Initializable
{
    @FXML
    /* New User Form */
    public TextField firstNameField;
    public TextField lastNameField;
    public TextField newUserEmailAddressField;
    public TextField phoneNumberField;
    public TextField asuriteIDField;
    public TextField usernameField;
    public TextField passwordField;
    public TextField confirmPasswordField;
    public ChoiceBox newUserRoleChoiceBox;
    public Button newUserSignUpButton;
    public Label newUserErrorLabel;

    /* Existing User Form */
    public TextField changeRoleEmailAddressField;
    public TextField changeRoleConfirmEmailAddressField;
    public ChoiceBox changeRoleRoleChoiceBox;
    public Button changeRoleSignUpButton;
    public Label changeRoleErrorLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /* Initialize Role Choice Boxes */
        newUserRoleChoiceBox.getItems().addAll(Customer.TYPE_TEXT, OrderProcessor.TYPE_TEXT, Chef.TYPE_TEXT, Admin.TYPE_TEXT);
        newUserRoleChoiceBox.setValue(Customer.TYPE_TEXT);
        changeRoleRoleChoiceBox.getItems().addAll(Customer.TYPE_TEXT, OrderProcessor.TYPE_TEXT, Chef.TYPE_TEXT, Admin.TYPE_TEXT);
        changeRoleRoleChoiceBox.setValue(Customer.TYPE_TEXT);
    }

    public void CreateUser(ActionEvent event) throws IOException
    {
        /* Reset UI */
        resetUI();        

        /* get fields */
        String name = firstNameField.getText() + ' ' + lastNameField.getText();
        if (name.charAt(0) == ' ') {
            invalidEntry(firstNameField);
            displayCreateUserErrorMessage("First Name is a Required Field");
            return;
        }
        if (name.charAt(name.length()-1) == ' ') {
            invalidEntry(lastNameField);
            displayCreateUserErrorMessage("Last Name is a Required Field");
            return;
        }
        String email = newUserEmailAddressField.getText();
        String phoneNumber = phoneNumberField.getText();
        String asurite = asuriteIDField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String role = changeRoleRoleChoiceBox.getValue().toString();
        int type = User.textToType(role);

        // make sure password matches confirmation
        if (!password.equals(confirmPasswordField.getText())) {
            invalidEntry(confirmPasswordField);
            displayCreateUserErrorMessage("Passwords do not match");
            return;
        }

        Response response = App.session.createAccount(username, password, name, email, phoneNumber, asurite, type);
        if (Response.ok(response)) {
            SwitchToAdminPage(event);   // reset page
        } else {
            invalidCreateUser(response);
        }
    }

    public void GivePermissions(ActionEvent event) throws IOException
    {
        /* Reset UI */
        resetUI();     

        String email = changeRoleEmailAddressField.getText();
        // make sure email has entry
        if (email.length() == 0) {
            invalidEntry(changeRoleEmailAddressField);
            displayChangeRoleErrorMessage("Email is a Required Field");
            return;
        }
        // make sure email matches confirmation
        if (!email.equals(changeRoleConfirmEmailAddressField.getText())) {
            invalidEntry(changeRoleConfirmEmailAddressField);
            displayChangeRoleErrorMessage("Emails do not match");
            return;
        }
        String role = changeRoleRoleChoiceBox.getValue().toString();
        int type = User.textToType(role);
        Response response = App.session.givePermissions(email, type);
        if (Response.ok(response)) {
            SwitchToAdminPage(event);   // reset page
        } else {
            invalidGivePermissions(response);
        }
    }

    private void resetUI() {
        validEntry(firstNameField);
        validEntry(lastNameField);
        validEntry(newUserEmailAddressField);
        validEntry(phoneNumberField);
        validEntry(usernameField);
        validEntry(asuriteIDField);
        validEntry(passwordField);
        validEntry(confirmPasswordField);
        displayCreateUserErrorMessage("");

        validEntry(changeRoleEmailAddressField);
        validEntry(changeRoleConfirmEmailAddressField);
        displayChangeRoleErrorMessage("");
    }

    private void invalidCreateUser(Response response) {
        /* feedback on entry */
        if (User.Name.isNameResponse(response)) {
            invalidEntry(firstNameField);
            invalidEntry(lastNameField);
        }
        if (User.Username.isUsernameResponse(response)) {
            invalidEntry(usernameField);
        }
        if (User.Email.isEmailResponse(response)) {
            invalidEntry(newUserEmailAddressField);
        }
        if (User.PhoneNumber.isPhoneNumberResponse(response)) {
            invalidEntry(phoneNumberField);
        }
        if (User.Asurite.isAsuriteResponse(response)) {
            invalidEntry(asuriteIDField);
        }
        if (User.Password.isPasswordResponse(response)) {
            invalidEntry(passwordField);
        }
        /* feedback message */
        displayCreateUserErrorMessage(response.message);
    }

    private void invalidGivePermissions(Response response) {
        invalidEntry(changeRoleEmailAddressField);
        invalidEntry(changeRoleConfirmEmailAddressField);
        displayChangeRoleErrorMessage(response.message);
    }

    private void displayCreateUserErrorMessage(String errorMessage) {
        newUserErrorLabel.setText(errorMessage);
    }

    private void displayChangeRoleErrorMessage(String errorMessage) {
        changeRoleErrorLabel.setText(errorMessage);
    }
    
}
