package views;

import controllers.SceneController;
import controllers.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import utils.Response;

public class LoginPage extends VBox implements Page {
	private Text loginText = new Text("Login");
	private Text emailText = new Text("Email");
	private Text passwordText = new Text("Password");
	private TextField emailTF = new TextField();
	private PasswordField passwordTF = new PasswordField();
	private Text errorText = new Text("");
	private Text registerLink = new Text("Register to StellarFest");
	private Button loginButton = new Button("Login");
	
	private VBox emailInputContainer = new VBox();
	private VBox pwInputContainer = new VBox();

	@Override
	public void setLayouts() {
		emailInputContainer.getChildren().addAll(emailText, emailTF);
		pwInputContainer.getChildren().addAll(passwordText, passwordTF);
		
		this.getChildren().addAll(
				loginText, 
				emailInputContainer, 
				pwInputContainer, 
				errorText,
				registerLink, 
				loginButton
			);
	}

	@Override
	public void setStyles() {
		loginText.setFont(Font.font("System", FontWeight.BOLD, 28));
		emailText.setFont(Font.font("System", FontWeight.NORMAL, 16));
		passwordText.setFont(Font.font("System", FontWeight.NORMAL, 16));
		registerLink.setFont(Font.font("System", FontWeight.NORMAL, 16));
		errorText.setFont(Font.font("System", FontWeight.NORMAL, 16));
		
		errorText.setManaged(false);
		errorText.setStyle("-fx-fill: red;");
		
		emailInputContainer.setAlignment(Pos.CENTER_LEFT);
		pwInputContainer.setAlignment(Pos.CENTER_LEFT);
		emailInputContainer.setMaxWidth(300);
		pwInputContainer.setMaxWidth(300);
		emailTF.setMinHeight(32);
		passwordTF.setMinHeight(32);
		
		registerLink.setCursor(Cursor.HAND);
		
		VBox.setMargin(emailInputContainer, new Insets(10, 20, 0, 20));
		VBox.setMargin(pwInputContainer, new Insets(10, 20, 0, 20));
		VBox.setMargin(registerLink, new Insets(10, 20, 0, 20));
		VBox.setMargin(errorText, new Insets(5, 20, 0, 20));
		VBox.setMargin(loginButton, new Insets(10, 20, 0, 20));
		loginButton.setPadding(new Insets(6, 20, 6, 20));
		
		this.setAlignment(Pos.CENTER);
	}

	@Override
	public void setEvents() {
		emailText.setOnMouseClicked(e -> {
			emailTF.requestFocus();
		});
		passwordText.setOnMouseClicked(e -> {
			passwordTF.requestFocus();
		});
		registerLink.setOnMouseClicked(e -> {
			SceneController.moveScene("register");
		});
		loginButton.setOnMouseClicked(e -> {
			doLogin();
		});
//		enter to do login
		this.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
		        doLogin();
		    }
		});
	}
	
	private void doLogin() {
		String email = emailTF.getText();
		String password = passwordTF.getText();
		Response<Void> response = UserController.login(email, password);

		if(response.isSuccess) {
			SceneController.moveScene("home");
		} else {
			errorText.setManaged(true);
			errorText.setText(response.message);
		}
	}
	
	public LoginPage() {
		initializePage();
	}
}
