package views;

import java.util.List;

import controllers.EventOrganizerController;
import controllers.SceneController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import models.Event;
import models.Guest;
import models.Vendor;
import utils.Response;

public class EditEventNamePage extends BorderPane implements Page{

	private Event event;
	
	private Label titleLabel = new Label();
	
	private VBox eventVB = new VBox();
	private HBox eventHB = new HBox();
	
	private VBox eventLabelVB = new VBox();
	
	//menampilkan detail event
	private Label nameLabel = new Label("Event Name\t\t: ");
	private Label dateLabel = new Label("Event Date\t\t: ");
	private Label locationLabel = new Label("Event Location\t: ");
	private Label descLabel = new Label("Event Description\t: ");
	
	private VBox eventDataVB = new VBox();
	
	private TextField nameDataLabel = new TextField();
	private Label dateDataLabel;
	private Label locationDataLabel;
	private Label descDataLabel;
	
	private HBox buttonHB = new HBox();
	
	private Button editButton = new Button("Edit Event Name");
	private Button backButton = new Button("Back");
	
	private Text errorLabel = new Text("");
	
	private HBox nameHB = new HBox();
	private HBox dateHB = new HBox();
	private HBox locationHB = new HBox();
	private HBox descHB = new HBox();
	
	
	@Override
	public void setLayouts() {
		// TODO Auto-generated method stub
		
		nameHB.getChildren().addAll(nameLabel, nameDataLabel);
		dateHB.getChildren().addAll(dateLabel, dateDataLabel);
		locationHB.getChildren().addAll(locationLabel, locationDataLabel);
		descHB.getChildren().addAll(descLabel, descDataLabel);
		buttonHB.getChildren().addAll(backButton, editButton);
		
		eventVB.getChildren().addAll(titleLabel, nameHB, dateHB, locationHB, descHB, errorLabel, buttonHB);
		
		this.setTop(eventVB);
		
		nameHB.setAlignment(Pos.CENTER_LEFT);
		dateHB.setAlignment(Pos.CENTER_LEFT);
		locationHB.setAlignment(Pos.CENTER_LEFT);
		descHB.setAlignment(Pos.CENTER_LEFT);
		
		eventVB.setAlignment(Pos.CENTER);
		
		buttonHB.setAlignment(Pos.CENTER);
		
		this.setAlignment(titleLabel, Pos.CENTER);
		this.setAlignment(eventVB, Pos.CENTER);
	}

	@Override
	public void setStyles() {
		// TODO Auto-generated method stub
		titleLabel.setFont(Font.font("System", FontWeight.BOLD, 28));
		nameLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
		dateLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
		locationLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
		descLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
		nameDataLabel.setFont(Font.font("System", FontWeight.NORMAL, 14));
		dateDataLabel.setFont(Font.font("System", FontWeight.NORMAL, 14));
		locationDataLabel.setFont(Font.font("System", FontWeight.NORMAL, 14));
		descDataLabel.setFont(Font.font("System", FontWeight.NORMAL, 14));
		
		errorLabel.setManaged(false);
		errorLabel.setStyle("-fx-fill: red;");
		
		titleLabel.setPadding(new Insets(10));
		
		buttonHB.setPadding(new Insets(10, 20, 0, 20));
		buttonHB.setSpacing(10);
		editButton.setPadding(new Insets(6, 20, 6, 20));
		backButton.setPadding(new Insets(6, 20, 6, 20));
		
		eventVB.setMaxWidth(500);
		eventVB.setSpacing(10);
	}

	@Override
	public void setEvents() {
		
		//jika di edit button di click akan mengedit event name dan mengarah ke page view organized event details
		editButton.setOnAction(e->{
			Response<Void> response = EventOrganizerController.editEventName(event.getEventId(), nameDataLabel.getText());
			
			if(response.isSuccess) {
				SceneController.moveScene("view organized event details", this.event);
			}
			else {
				errorLabel.setManaged(true);
				errorLabel.setText(response.message);
			}
		});
		
		//back button untuk mengarah ke page view organized event details 
		backButton.setOnMouseClicked(e -> {
			SceneController.moveScene("view organized event details", event);
		});
	}
	
	//menampilkan data detail event 
	public EditEventNamePage(Event event) {
		this.event = event;
		
		Response<Event> eventData = EventOrganizerController.viewOrganizedEventDetails(this.event.getEventId());
		
		titleLabel.setText("Edit Event Name - " + eventData.data.getEventName());
		nameDataLabel.setText(eventData.data.getEventName());
		dateDataLabel = new Label(eventData.data.getEventDate());
		locationDataLabel = new Label(eventData.data.getEventLocation());
		descDataLabel = new Label(eventData.data.getEventDescription());
		
		
		initializePage();
	}

}
