package views;

import controllers.SceneController;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import models.User;
import utils.AuthUser;

public class HomePage extends BorderPane implements Page {
	private User authUser = AuthUser.get();
	private Text nameText = new Text("Welcome " + authUser.getUsername());
    private HBox buttonBox = new HBox();
	
	// Vendor
	private Button manageVendor = new Button("Manage Vendor");
	private Button viewVendor = new Button("View Vendor");
	
	// Vendor & Guest
	private Button viewInvitations = new Button("View Invitations");
	
	
	public HomePage() {
		initializePage();
	}

	@Override
	public void setLayouts() {
		
		//maap gatau taro buttonnya dimana
		if(authUser.getUserRole().equalsIgnoreCase("Vendor")) {
			buttonBox.getChildren().addAll(manageVendor, viewVendor, viewInvitations);
		}
	}

	@Override
	public void setStyles() {
		nameText.setFont(Font.font("System", FontWeight.BOLD, 28));
				
		setMargin(buttonBox.getChildren());
		
		this.setTop(buttonBox);
		this.setCenter(nameText);
	}

	@Override
	public void setEvents() {
		// Vendor
		manageVendor.setOnMouseClicked(e -> {
			SceneController.moveScene("manage vendor");
		});
		
		viewVendor.setOnMouseClicked(e -> {
			SceneController.moveScene("view vendor");
		});
		
		// Vendor & Guest
		viewInvitations.setOnMouseClicked(e -> {
			SceneController.moveScene("view invitations");
		});
	}
	
	public void setMargin (ObservableList<Node> child) {
		child.forEach((node) -> {
			buttonBox.setMargin(node, new Insets(5, 0, 0, 5));
		});
	}

}
