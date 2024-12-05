package views;

import controllers.SceneController;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import models.User;
import utils.AuthUser;

public class HomePage extends BorderPane implements Page {
	private User authUser = AuthUser.get();
	private Text nameText = new Text(authUser.getUsername());
	private Button createEventButton = new Button("Create Event");
	
	
	public HomePage() {
		initializePage();
	}

	@Override
	public void setLayouts() {
		this.setCenter(nameText);
		
		//maap gatau taro buttonnya dimana
		if(authUser.getUserRole().equalsIgnoreCase("Event Organizer")) {
			this.setBottom(createEventButton);
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
	}

}
