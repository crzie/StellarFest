package views;

import controllers.SceneController;
import controllers.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
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
	private Button backButton = new Button("Back");
	
	private VBox emailInputContainer = new VBox();
	private VBox nameInputContainer = new VBox();
	private VBox oldPwInputContainer = new VBox();
	private VBox newPwInputContainer = new VBox();
	private HBox buttonContainer = new HBox();
	
	@Override
	public void setLayouts() {
		// memasukkan tiap elemen ke dalam kontainer nya masing-masing
		emailInputContainer.getChildren().addAll(emailText, emailTF);
		nameInputContainer.getChildren().addAll(nameText, nameTF);
		oldPwInputContainer.getChildren().addAll(oldPasswordText, oldPasswordTF);
		newPwInputContainer.getChildren().addAll(newPasswordText, newPasswordTF);
		buttonContainer.getChildren().addAll(backButton, changeProfileButton);
		
		// memasukkan elemen ke dalam vbox halaman ini
		this.getChildren().addAll(
				changeProfileText,
				emailInputContainer,
				nameInputContainer,
				oldPwInputContainer,
				newPwInputContainer,
				errorText,
				successText,
				buttonContainer
			);
		
	}

	@Override
	public void setStyles() {
		// memberikan style pada tiap teks
		changeProfileText.setFont(Font.font("System", FontWeight.BOLD, 28));
		nameText.setFont(Font.font("System", FontWeight.NORMAL, 16));
		emailText.setFont(Font.font("System", FontWeight.NORMAL, 16));
		oldPasswordText.setFont(Font.font("System", FontWeight.NORMAL, 16));
		newPasswordText.setFont(Font.font("System", FontWeight.NORMAL, 16));
		errorText.setFont(Font.font("System", FontWeight.NORMAL, 16));
		successText.setFont(Font.font("System", FontWeight.NORMAL, 16));
		
		// membuat teks error tidak terlihat saat di awal
		errorText.setManaged(false);
		errorText.setStyle("-fx-fill: red;");
		
		// membuat teks sukses tidak terlihat saat di awal
		successText.setManaged(false);
		successText.setStyle("-fx-fill: green;");
		
		// mengatur alignment untuk tiap kontainer yang terdapat pada page
		emailInputContainer.setAlignment(Pos.CENTER_LEFT);
		nameInputContainer.setAlignment(Pos.CENTER_LEFT);
		oldPwInputContainer.setAlignment(Pos.CENTER_LEFT);
		newPwInputContainer.setAlignment(Pos.CENTER_LEFT);
		buttonContainer.setAlignment(Pos.CENTER);
		
		// mengatur ukuran tiap kontainer
		emailInputContainer.setMaxWidth(300);
		nameInputContainer.setMaxWidth(300);
		oldPwInputContainer.setMaxWidth(300);
		newPwInputContainer.setMaxWidth(300);
		
		// mengatur ukuran button serta email
		buttonContainer.setMaxWidth(300);
		emailTF.setMinHeight(32);
		nameTF.setMinHeight(32);
		oldPasswordTF.setMinHeight(32);
		newPasswordTF.setMinHeight(32);
		
		// mengatur margin 
		VBox.setMargin(emailInputContainer, new Insets(10, 20, 0, 20));
		VBox.setMargin(nameInputContainer, new Insets(10, 20, 0, 20));
		VBox.setMargin(oldPwInputContainer, new Insets(10, 20, 0, 20));
		VBox.setMargin(newPwInputContainer, new Insets(10, 20, 0, 20));
		VBox.setMargin(errorText, new Insets(5, 20, 0, 20));
		
		// mengautr padding serta spacing
		buttonContainer.setPadding(new Insets(10, 20, 0, 20));
		buttonContainer.setSpacing(10);
		changeProfileButton.setPadding(new Insets(6, 20, 6, 20));
		backButton.setPadding(new Insets(6, 20, 6, 20));
		
		// membuat agar semua elemen berada di tengah page
		this.setAlignment(Pos.CENTER);
		
	}

	@Override
	public void setEvents() {
		// ketika button change profil diklik akan menjalankan fungsi change profile
		changeProfileButton.setOnMouseClicked(e -> {
			changeProfile();
		});
		
		// ketika back button diklik akan kembali ke halaman home
		backButton.setOnMouseClicked(e -> {
			SceneController.moveScene("home");
		});
		
	}
	
	private void changeProfile() {
		// mengambil data yang sudah dimasukkan oleh user
		String name = nameTF.getText();
		String email = emailTF.getText();
		String oldPassword = oldPasswordTF.getText();
		String newPassword = newPasswordTF.getText();
		
		// menyimpan response dari proses ubah profile
		Response<Void> response = UserController.changeProfile(email, name, oldPassword, newPassword);

		if(response.isSuccess) {
			// jika proses mengubah profile berhasil
			errorText.setManaged(false);
			errorText.setText("");
			
			successText.setManaged(true);
			successText.setText("Successfully changed profile!");
		} else {
			// jika proses mengubah profile gagal
			successText.setManaged(false);
			successText.setText("");
			
			errorText.setManaged(true);
			errorText.setText(response.message);
		}
		
	}
	
	public void initializeData() {
		// melakukan inisialisasi data untuk halaman change profile
		emailTF.setText(user.getUserEmail());
		nameTF.setText(user.getUsername());
	}

	public ChangeProfilePage() {
		// melakukan inisialisasi data dan juga halaman
		initializeData();
		initializePage();
	}

}
