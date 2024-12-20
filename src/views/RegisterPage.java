package views;

import java.util.ArrayList;

import controllers.SceneController;
import controllers.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import utils.Response;

public class RegisterPage extends VBox implements Page{
	private Text registerText = new Text("Register");
	private Text emailText = new Text("Email");
	private Text usernameText = new Text("Username");
	private Text passwordText = new Text("Password");
	private Text roleText = new Text("Role");
	private TextField emailTF = new TextField();
	private TextField usernameTF = new TextField();
	private PasswordField passwordTF = new PasswordField();
	private ComboBox<String> roleCB = new ComboBox<>();
	private Text errorText = new Text("");
	private Text loginLink = new Text("Login with your account");
	private Button registerButton = new Button("Register");
	
	private VBox emailInputContainer = new VBox();
	private VBox usernameInputContainer = new VBox();
	private VBox pwInputContainer = new VBox();
	private VBox roleInputContainer = new VBox();

	@Override
	public void setLayouts() {
		emailInputContainer.getChildren().addAll(emailText, emailTF);
		usernameInputContainer.getChildren().addAll(usernameText, usernameTF);
		pwInputContainer.getChildren().addAll(passwordText, passwordTF);
		roleInputContainer.getChildren().addAll(roleText, roleCB);
		
		roleCB.getItems().addAll("Guest", "Event Organizer", "Vendor");
		roleCB.setValue("Guest");
		
		this.getChildren().addAll(
				registerText, 
				emailInputContainer, 
				usernameInputContainer, 
				pwInputContainer, 
				roleInputContainer,
				errorText, 
				loginLink, 
				registerButton
			);
	}

	@Override
	public void setStyles() {
		registerText.setFont(Font.font("System", FontWeight.BOLD, 28));
		emailText.setFont(Font.font("System", FontWeight.NORMAL, 16));		
		usernameText.setFont(Font.font("System", FontWeight.NORMAL, 16));
		passwordText.setFont(Font.font("System", FontWeight.NORMAL, 16));
		roleText.setFont(Font.font("System", FontWeight.NORMAL, 16));
		loginLink.setFont(Font.font("System", FontWeight.NORMAL, 16));
		errorText.setFont(Font.font("System", FontWeight.NORMAL, 16));
		
		errorText.setManaged(false);
		errorText.setStyle("-fx-fill: red;");
		
		emailInputContainer.setAlignment(Pos.CENTER_LEFT);
		usernameInputContainer.setAlignment(Pos.CENTER_LEFT);
		pwInputContainer.setAlignment(Pos.CENTER_LEFT);
		roleInputContainer.setAlignment(Pos.CENTER_LEFT);
		emailInputContainer.setMaxWidth(300);
		usernameInputContainer.setMaxWidth(300);
		pwInputContainer.setMaxWidth(300);
		roleInputContainer.setMaxWidth(300);	
		roleCB.setPrefWidth(300);
		emailTF.setMinHeight(32);
		usernameTF.setMinHeight(32);
		passwordTF.setMinHeight(32);
		roleCB.setMinHeight(32);
		
		loginLink.setCursor(Cursor.HAND);
		
		VBox.setMargin(emailInputContainer, new Insets(10, 20, 0, 20));
		VBox.setMargin(usernameInputContainer, new Insets(10, 20, 0, 20));
		VBox.setMargin(pwInputContainer, new Insets(10, 20, 0, 20));
		VBox.setMargin(roleInputContainer, new Insets(10, 20, 0, 20));
		VBox.setMargin(loginLink, new Insets(10, 20, 0, 20));
		VBox.setMargin(errorText, new Insets(5, 20, 0, 20));
		VBox.setMargin(registerButton, new Insets(10, 20, 0, 20));
		registerButton.setPadding(new Insets(6, 20, 6, 20));
		
		this.setAlignment(Pos.CENTER);
	}

	@Override
	public void setEvents() {
		// jika di click labelnya akan fokus pada text fieldnya
		emailText.setOnMouseClicked(e -> {
			emailTF.requestFocus();
		});
		usernameText.setOnMouseClicked(e -> {
			usernameTF.requestFocus();
		});
		passwordText.setOnMouseClicked(e -> {
			passwordTF.requestFocus();
		});
		roleText.setOnMouseClicked(e -> {
			roleCB.requestFocus();
			roleCB.show();
		});
		loginLink.setOnMouseClicked(e -> {
			SceneController.moveScene("login");
		});
		registerButton.setOnMouseClicked(e -> {
			doRegister();
		});
//		enter to do register
		this.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
		        doRegister();
		    }
		});
	}
	
	//melakukan register untuk user dengan memanggul user controller dan melakukan validasi
	private void doRegister() {
		String email = emailTF.getText();
		String password = passwordTF.getText();
		String username = usernameTF.getText();
		String role = roleCB.getValue();
		Response<Void> response = UserController.register(email, username, password, role);

		if(response.isSuccess) {
			SceneController.moveScene("login");
		} else {
			errorText.setManaged(true);
			errorText.setText(response.message);
		}
	}
	
	public RegisterPage() {
		initializePage();
	}
}
