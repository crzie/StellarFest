package controllers;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import views.HomePage;
import views.LoginPage;
import views.RegisterPage;

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
