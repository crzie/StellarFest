package controllers;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.Event;
import views.*;

public class SceneController {
	private static Stage stage;

    public static void setStage(Stage primaryStage) {
        stage = primaryStage;
    }
    
	private static Pane getPageFromPath(String path, Object... params) {
		switch (path) {
		case "login": return new LoginPage();
		case "register": return new RegisterPage();
		case "home": return new HomePage();
		case "create event": return new CreateEventPage();
		case "add vendors":
			if(params.length == 1 && params[0] instanceof Event) {
				return new AddVendorPage((Event)params[0]);				
			} else {
				return new RegisterPage();
			}
		case "add guests": return new AddGuestsPage();
		case "view organized events": return new ViewOrganizedEventsPage();
		case "view organized event details": return new ViewOrganizedEventDetailsPage();
		case "edit event name": return new EditEventNamePage();
			default: return new RegisterPage();
		}
		
	}
	
	public static void moveScene(String path, Object... params) {
		if (stage == null) {
            throw new IllegalStateException("Stage has not been set.");
        }
		
		Pane page = getPageFromPath(path, params);
        Scene scene = new Scene(page, 800, 600);
        stage.setScene(scene);
	}
}
