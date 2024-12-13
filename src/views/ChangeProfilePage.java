package views;

import controllers.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import models.User;
import utils.AuthUser;
import utils.Response;

public class ChangeProfilePage extends VBox implements Page{
	private User user = AuthUser.get();
	
	private Text changeProfileText = new Text("Change Profile");
	
	private Text emailText = new Text("Email");
	private Text nameText = new Text("Name");
	private Text oldPasswordText = new Text("Old Password");
	private Text newPasswordText = new Text("New Password");
	private TextField emailTF = new TextField();
	private TextField nameTF = new TextField();
	private PasswordField oldPasswordTF = new PasswordField();
	private PasswordField newPasswordTF = new PasswordField();
	private Text errorText = new Text("");
	private Text successText = new Text("");
	
	private Button changeProfileButton = new Button("Save");
	
	private VBox emailInputContainer = new VBox();
	private VBox nameInputContainer = new VBox();
	private VBox oldPwInputContainer = new VBox();
	private VBox newPwInputContainer = new VBox();
	
	@Override
	public void setLayouts() {
		emailInputContainer.getChildren().addAll(emailText, emailTF);
		nameInputContainer.getChildren().addAll(nameText, nameTF);
		oldPwInputContainer.getChildren().addAll(oldPasswordText, oldPasswordTF);
		newPwInputContainer.getChildren().addAll(newPasswordText, newPasswordTF);
		
		this.getChildren().addAll(
				changeProfileText,
				emailInputContainer,
				nameInputContainer,
				oldPwInputContainer,
				newPwInputContainer,
				errorText,
				successText,
				changeProfileButton
			);
		
	}

	@Override
	public void setStyles() {
		changeProfileText.setFont(Font.font("System", FontWeight.BOLD, 28));
		nameText.setFont(Font.font("System", FontWeight.NORMAL, 16));
		emailText.setFont(Font.font("System", FontWeight.NORMAL, 16));
		oldPasswordText.setFont(Font.font("System", FontWeight.NORMAL, 16));
		newPasswordText.setFont(Font.font("System", FontWeight.NORMAL, 16));
		errorText.setFont(Font.font("System", FontWeight.NORMAL, 16));
		successText.setFont(Font.font("System", FontWeight.NORMAL, 16));
		
		errorText.setManaged(false);
		errorText.setStyle("-fx-fill: red;");
		
		successText.setManaged(false);
		successText.setStyle("-fx-fill: green;");
		
		emailInputContainer.setAlignment(Pos.CENTER_LEFT);
		nameInputContainer.setAlignment(Pos.CENTER_LEFT);
		oldPwInputContainer.setAlignment(Pos.CENTER_LEFT);
		newPwInputContainer.setAlignment(Pos.CENTER_LEFT);
		emailInputContainer.setMaxWidth(300);
		nameInputContainer.setMaxWidth(300);
		oldPwInputContainer.setMaxWidth(300);
		newPwInputContainer.setMaxWidth(300);
		emailTF.setMinHeight(32);
		nameTF.setMinHeight(32);
		oldPasswordTF.setMinHeight(32);
		newPasswordTF.setMinHeight(32);
		
		VBox.setMargin(emailInputContainer, new Insets(10, 20, 0, 20));
		VBox.setMargin(nameInputContainer, new Insets(10, 20, 0, 20));
		VBox.setMargin(oldPwInputContainer, new Insets(10, 20, 0, 20));
		VBox.setMargin(newPwInputContainer, new Insets(10, 20, 0, 20));
		VBox.setMargin(errorText, new Insets(5, 20, 0, 20));
		VBox.setMargin(changeProfileButton, new Insets(10, 20, 0, 20));
		changeProfileButton.setPadding(new Insets(6, 20, 6, 20));
		
		this.setAlignment(Pos.CENTER);
		
	}

	@Override
	public void setEvents() {
		changeProfileButton.setOnMouseClicked(e -> {
			changeProfile();
		});
		
	}
	
	private void changeProfile() {
		
		String name = nameTF.getText();
		String email = emailTF.getText();
		String oldPassword = oldPasswordTF.getText();
		String newPassword = newPasswordTF.getText();
		Response<Void> response = UserController.changeProfile(email, name, oldPassword, newPassword);
		
		if(response.isSuccess) {
			errorText.setManaged(false);
			errorText.setText("");
			
			successText.setManaged(true);
			successText.setText("Successfully changed profile!");
		} else {
			successText.setManaged(false);
			successText.setText("");
			
			errorText.setManaged(true);
			errorText.setText(response.message);
		}
		
	}

	public ChangeProfilePage() {
		emailTF.setText(user.getUserEmail());
		nameTF.setText(user.getUsername());

		initializePage();
	}

}
