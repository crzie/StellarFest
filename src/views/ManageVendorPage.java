package views;

import controllers.SceneController;
import controllers.UserController;
import controllers.VendorController;
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

public class ManageVendorPage extends VBox implements Page{
	private Text manageText = new Text("Manage Vendor");
	private Text nameText = new Text("Product Name");
	private Text descText = new Text("Description");
	private TextField nameTF = new TextField();
	private TextField descTF = new TextField();
	private Text errorText = new Text("");
	private Text viewLink = new Text("View Vendor");
	private Button manageButton = new Button("Manage");
	
	private VBox nameInputContainer = new VBox();
	private VBox descInputContainer = new VBox();

	@Override
	public void setLayouts() {
		nameInputContainer.getChildren().addAll(nameText, nameTF);
		descInputContainer.getChildren().addAll(descText, descTF);
		
		this.getChildren().addAll(
				manageText, 
				nameInputContainer, 
				descInputContainer, 
				errorText,
				viewLink, 
				manageButton
			);
	}

	@Override
	public void setStyles() {
		manageText.setFont(Font.font("System", FontWeight.BOLD, 28));
		nameText.setFont(Font.font("System", FontWeight.NORMAL, 16));
		descText.setFont(Font.font("System", FontWeight.NORMAL, 16));
		viewLink.setFont(Font.font("System", FontWeight.NORMAL, 16));
		errorText.setFont(Font.font("System", FontWeight.NORMAL, 16));
		
		errorText.setManaged(false);
		errorText.setStyle("-fx-fill: red;");
		
		nameInputContainer.setAlignment(Pos.CENTER_LEFT);
		descInputContainer.setAlignment(Pos.CENTER_LEFT);
		nameInputContainer.setMaxWidth(300);
		descInputContainer.setMaxWidth(300);
		nameTF.setMinHeight(32);
		descTF.setMinHeight(32);
		
		viewLink.setCursor(Cursor.HAND);
		
		VBox.setMargin(nameInputContainer, new Insets(10, 20, 0, 20));
		VBox.setMargin(descInputContainer, new Insets(10, 20, 0, 20));
		VBox.setMargin(viewLink, new Insets(10, 20, 0, 20));
		VBox.setMargin(errorText, new Insets(5, 20, 0, 20));
		VBox.setMargin(manageButton, new Insets(10, 20, 0, 20));
		manageButton.setPadding(new Insets(6, 20, 6, 20));
		
		this.setAlignment(Pos.CENTER);
	}

	@Override
	public void setEvents() {
		nameText.setOnMouseClicked(e -> {
			nameTF.requestFocus();
		});
		descText.setOnMouseClicked(e -> {
			descTF.requestFocus();
		});
		viewLink.setOnMouseClicked(e -> {
			SceneController.moveScene("view vendor");
		});
		manageButton.setOnMouseClicked(e -> {
			manageVendor();
		});
		
		this.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				manageVendor();
		    }
		});
	}
	
	private void manageVendor() {
		String name = nameTF.getText();
		String desc = descTF.getText();
		Response<Void> response = VendorController.manageVendor(desc, name);

		if(response.isSuccess) {
			SceneController.moveScene("home");
		} else {
			errorText.setManaged(true);
			errorText.setText(response.message);
		}
	}
	
	public ManageVendorPage() {
		initializePage();
	}

}
