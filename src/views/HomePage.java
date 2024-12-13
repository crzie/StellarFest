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
    
    // All User
    private Button changeProfileButton = new Button("Change Profile");
    
    // Event Organizer
    private Button createEventButton = new Button("Create Event");
    private Button viewOrganizedEventButton = new Button("View Organized Event");
    
    // Admin
    private Button viewEventButton = new Button("View All Events");
    private Button viewUserButton = new Button("View All Users");
	
	// Vendor
	private Button manageVendor = new Button("Manage Vendor");
	private Button viewVendor = new Button("View Vendor");
	
	// Vendor & Guest
	private Button viewInvitations = new Button("View Invitations");
	private Button viewAcceptedEvent = new Button("View Accepted Event");
	
	
	public HomePage() {
		initializePage();
	}

	@Override
	public void setLayouts() {
		String role = authUser.getUserRole();
		
		if(authUser.getUserRole().equalsIgnoreCase("Event Organizer")) {
			buttonBox.getChildren().addAll(createEventButton, viewOrganizedEventButton, changeProfileButton);
		}
		else if(authUser.getUserRole().equalsIgnoreCase("Admin")) {
			buttonBox.getChildren().addAll(viewEventButton, viewUserButton, changeProfileButton);
		}
		
		else if(role.equalsIgnoreCase("Vendor")) {
			buttonBox.getChildren().addAll(manageVendor, viewVendor, viewInvitations, viewAcceptedEvent, changeProfileButton);
		} else if (role.equalsIgnoreCase("Guest")) {
			buttonBox.getChildren().addAll(viewInvitations, viewAcceptedEvent, changeProfileButton);
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
		viewEventButton.setOnMouseClicked(e -> {
			SceneController.moveScene("view all events");
		});
		viewUserButton.setOnMouseClicked(e -> {
			SceneController.moveScene("view all users");
		});
	
		createEventButton.setOnMouseClicked((e) -> {
			SceneController.moveScene("create event");
		});
		
		viewOrganizedEventButton.setOnMouseClicked(e -> {
			SceneController.moveScene("view organized events");
		});
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
		
		viewAcceptedEvent.setOnMouseClicked(e -> {
			SceneController.moveScene("view accepted events");
		});
		
		// All User
		changeProfileButton.setOnMouseClicked(e -> {
			SceneController.moveScene("change profile");
		});
	}
	
	public void setMargin (ObservableList<Node> child) {
		child.forEach((node) -> {
			buttonBox.setMargin(node, new Insets(5, 0, 0, 5));
		});
	}

}
