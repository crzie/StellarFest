	package views;

import controllers.SceneController;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import models.User;
import utils.AuthUser;

public class HomePage extends BorderPane implements Page {
	private User authUser = AuthUser.get();
	private Text nameText = new Text(authUser.getUsername());
	private Button createEventButton = new Button("Create Event");
	private Button eventsButton = new Button("Events");
	private Button usersButton = new Button("Users");
	private Button viewOrganizedEventButton = new Button("View Organized Event");
	private HBox navigationBar = new HBox();
	
	public HomePage() {
		initializePage();
	}

	@Override
	public void setLayouts() {
		this.setCenter(nameText);
		
		if(authUser.getUserRole().equalsIgnoreCase("Event Organizer")) {
			HBox hbox = new HBox();
			hbox.getChildren().addAll(createEventButton, viewOrganizedEventButton);
			this.setTop(hbox);
		}
		else if(authUser.getUserRole().equalsIgnoreCase("Admin")) {
			navigationBar.getChildren().addAll(eventsButton, usersButton);
			this.setTop(navigationBar);
		}
		else if(authUser.getUserRole().equalsIgnoreCase("Admin")) {
			navigationBar.getChildren().addAll(eventsButton, usersButton);
			this.setTop(navigationBar);
		}
		
	}

	@Override
	public void setStyles() {
		navigationBar.setSpacing(10);

	}

	@Override
	public void setEvents() {
		// TODO Auto-generated method stub
		createEventButton.setOnMouseClicked(e -> {
			SceneController.moveScene("create event");
		});
		viewOrganizedEventButton.setOnMouseClicked(e -> {
			SceneController.moveScene("view organized events");
		});
		if(authUser.getUserRole().equalsIgnoreCase("Admin")) {
			eventsButton.setOnMouseClicked(e -> {
				SceneController.moveScene("view all events");
			});
			usersButton.setOnMouseClicked(e -> {
				SceneController.moveScene("view all users");
			});
		}
		
	}

}
