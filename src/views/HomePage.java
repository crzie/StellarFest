package views;

import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import models.User;
import utils.AuthUser;

public class HomePage extends BorderPane implements Page {
	private User authUser = AuthUser.get();
	private Text nameText = new Text(authUser.getUsername());
	
	public HomePage() {
		initializePage();
	}

	@Override
	public void setLayouts() {
		this.setCenter(nameText);
	}

	@Override
	public void setStyles() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setEvents() {
		// TODO Auto-generated method stub

	}

}
