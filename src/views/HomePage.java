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
    
    // Event Organizer
    private Button createEventButton = new Button("Create Event");
    private Button viewOrganizedEventButton = new Button("View Organized Event");
    
    // Admin
    private Button eventsButton = new Button("Events");
    private Button usersButton = new Button("Users");
	
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
			buttonBox.getChildren().addAll(createEventButton, viewOrganizedEventButton);
		}
		else if(authUser.getUserRole().equalsIgnoreCase("Admin")) {
			buttonBox.getChildren().addAll(eventsButton, usersButton);
		}
		else if(role.equalsIgnoreCase("Vendor")) {
			buttonBox.getChildren().addAll(manageVendor, viewVendor, viewInvitations, viewAcceptedEvent);
		} else if (role.equalsIgnoreCase("Guest")) {
			buttonBox.getChildren().addAll(viewInvitations, viewAcceptedEvent);
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
		if(authUser.getUserRole().equalsIgnoreCase("Admin")) {
			eventsButton.setOnMouseClicked(e -> {
				SceneController.moveScene("view all events");
			});
			usersButton.setOnMouseClicked(e -> {
				SceneController.moveScene("view all users");
			});
		}
		
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
	}
	
	public void setMargin (ObservableList<Node> child) {
		child.forEach((node) -> {
			buttonBox.setMargin(node, new Insets(5, 0, 0, 5));
		});
	}

}
