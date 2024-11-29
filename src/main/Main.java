package main;

import controllers.SceneController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import views.LoginPage;

public class Main extends Application {

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		SceneController.setStage(primaryStage);
		
		primaryStage.setTitle("StellarFest");
		SceneController.moveScene("login");
		primaryStage.show();
	}

}
