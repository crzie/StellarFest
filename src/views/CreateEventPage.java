package views;

import java.time.LocalDate;

import controllers.EventController;
import controllers.SceneController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import models.User;
import utils.AuthUser;
import utils.Response;

public class CreateEventPage extends VBox implements Page{

	private Label createEventLabel = new Label("Create Event");
	
	private Label nameLabel = new Label("Event Name");
	private Label dateLabel = new Label("Event Date");
	private Label locationLabel = new Label("Event Location");
	private Label descLabel = new Label("Event Description");
	
	private TextField nameTF = new TextField();
	private DatePicker dateDP = new DatePicker();
	private TextField locationTF = new TextField();
	private TextArea descTA = new TextArea();
	
	private Text errorLabel = new Text("");
	private Button createButton = new Button("Create");
	private Button backButton = new Button("Back");
	
	private VBox nameInputContainer = new VBox();
	private VBox dateInputContainer = new VBox();
	private VBox locationInputContainer = new VBox();
	private VBox descInputContainer = new VBox();
	private HBox buttonContainer = new HBox();
	
	@Override
	public void setLayouts() {
		// TODO Auto-generated method stub
		
		nameInputContainer.getChildren().addAll(nameLabel, nameTF);
		dateInputContainer.getChildren().addAll(dateLabel, dateDP);
		locationInputContainer.getChildren().addAll(locationLabel, locationTF);
		descInputContainer.getChildren().addAll(descLabel, descTA);
		buttonContainer.getChildren().addAll(backButton, createButton);

		this.getChildren().addAll(
				createEventLabel, 
				nameInputContainer, 
				dateInputContainer,
				locationInputContainer,
				descInputContainer,
				errorLabel,
				buttonContainer
				);
	}

	@Override
	public void setStyles() {
		// TODO Auto-generated method stub
		createEventLabel.setFont(Font.font("System", FontWeight.BOLD, 28));
		nameLabel.setFont(Font.font("System", FontWeight.NORMAL, 12));
		dateLabel.setFont(Font.font("System", FontWeight.NORMAL, 12));
		locationLabel.setFont(Font.font("System", FontWeight.NORMAL, 12));
		descLabel.setFont(Font.font("System", FontWeight.NORMAL, 12));
		
		errorLabel.setManaged(false);
		errorLabel.setStyle("-fx-fill: red;");
		
		nameInputContainer.setAlignment(Pos.CENTER_LEFT);
		dateInputContainer.setAlignment(Pos.CENTER_LEFT);
		locationInputContainer.setAlignment(Pos.CENTER_LEFT);
		descInputContainer.setAlignment(Pos.CENTER_LEFT);
		buttonContainer.setAlignment(Pos.CENTER);
		nameInputContainer.setMaxWidth(300);
		dateInputContainer.setMaxWidth(300);
		locationInputContainer.setMaxWidth(300);
		descInputContainer.setMaxWidth(300);
		buttonContainer.setMaxWidth(300);
		nameTF.setMinHeight(32);
		locationTF.setMinHeight(32);
		descTA.setMinHeight(32);
		descTA.setWrapText(true);
		
		VBox.setMargin(nameInputContainer, new Insets(10, 20, 0, 20));
		VBox.setMargin(dateInputContainer, new Insets(10, 20, 0, 20));
		VBox.setMargin(locationInputContainer, new Insets(10, 20, 0, 20));
		VBox.setMargin(descInputContainer, new Insets(10, 20, 0, 20));
		VBox.setMargin(errorLabel, new Insets(5, 20, 0, 20));
		VBox.setMargin(createButton, new Insets(10, 20, 0, 20));
		buttonContainer.setPadding(new Insets(10, 20, 0, 20));
		buttonContainer.setSpacing(10);
		createButton.setPadding(new Insets(6, 20, 6, 20));
		backButton.setPadding(new Insets(6, 20, 6, 20));
		
		this.setAlignment(Pos.CENTER);
	}

	@Override
	public void setEvents() {
		// TODO Auto-generated method stub
		nameLabel.setOnMouseClicked(e -> {
			nameTF.requestFocus();
		});
		dateLabel.setOnMouseClicked(e -> {
			dateDP.requestFocus();
		});
		locationLabel.setOnMouseClicked(e -> {
			locationTF.requestFocus();
		});
		descLabel.setOnMouseClicked(e -> {
			descTA.requestFocus();
		});
		createButton.setOnMouseClicked(e ->{
			createEvent();
		});
		
		this.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
		        createEvent();
		    }
		});
		
		backButton.setOnMouseClicked(e -> {
			SceneController.moveScene("home");
		});
	}
	
	public void createEvent() {
		String name = nameTF.getText();
		LocalDate date = dateDP.getValue();
		String location = locationTF.getText();
		String desc = descTA.getText();
		
		User organizerId = AuthUser.get();
		
		Response<Void> response = EventController.createEvent(name, date, location, desc, organizerId.getUserId());
		
		if(response.isSuccess) {
			SceneController.moveScene("home");
		}
		else {
			errorLabel.setVisible(true);
			errorLabel.setManaged(true);
			errorLabel.setText(response.message);
		}
	}
	
	public CreateEventPage() {
		initializePage();
	}

}
