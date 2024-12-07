package views;

import controllers.SceneController;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import models.User;
import utils.AuthUser;

public class HomePage extends BorderPane implements Page {
	private User authUser = AuthUser.get();
	private Text nameText = new Text(authUser.getUsername());
	private Button createEventButton = new Button("Create Event");
	private Button viewOrganizedEventButton = new Button("View Organized Event");
	
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
		
	}

	@Override
	public void setStyles() {
		// TODO Auto-generated method stub

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
	}

}
