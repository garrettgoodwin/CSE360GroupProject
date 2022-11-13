import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
public class SignUpPage extends SceneController
{
    @FXML
    public TextField firstNameField;
    public TextField lastNameField;
    public TextField emailAddressField;
    public TextField phoneNumberField;
    public TextField asuriteIDField;
    public TextField usernameField;
    public TextField passwordField;
    public TextField confirmPasswordField;
    public Button signUpButton;
    public Label errorLabel;

    public void CreateAccount(ActionEvent event) throws IOException
    {
        /* Reset UI */
        resetUI();

        /* get fields */
        String name = firstNameField.getText() + ' ' + lastNameField.getText();
        if (name.charAt(0) == ' ') {
            invalidEntry(firstNameField);
            displayErrorMessage("First Name is a Required Field");
            return;
        }
        if (name.charAt(name.length()-1) == ' ') {
            invalidEntry(lastNameField);
            displayErrorMessage("Last Name is a Required Field");
            return;
        }
        String email = emailAddressField.getText();
        String phoneNumber = phoneNumberField.getText();
        String asurite = asuriteIDField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        // make sure password matches confirmation
        if (!password.equals(confirmPasswordField.getText())) {
            invalidEntry(confirmPasswordField);
            displayErrorMessage("Passwords do not match");
            return;
        }

        Response response = App.session.createAccount(username, password, name, email, phoneNumber, asurite);
        if (Response.ok(response)) {
            SwitchToCheckoutPage(event);
        } else {
            invalidSignUp(response);
        }
    }

    private void resetUI() {
        validEntry(firstNameField);
        validEntry(lastNameField);
        validEntry(emailAddressField);
        validEntry(phoneNumberField);
        validEntry(usernameField);
        validEntry(asuriteIDField);
        validEntry(passwordField);
        validEntry(confirmPasswordField);
    }

    private void invalidSignUp(Response response) {
        /* feedback on entry */
        if (User.Name.isNameResponse(response)) {
            invalidEntry(firstNameField);
            invalidEntry(lastNameField);
        }
        if (User.Username.isUsernameResponse(response)) {
            invalidEntry(usernameField);
        }
        if (User.Email.isEmailResponse(response)) {
            invalidEntry(emailAddressField);
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
        displayErrorMessage(response.message);
    }

    private void displayErrorMessage(String errorMessage) {
        errorLabel.setText(errorMessage);
    }
    
}
