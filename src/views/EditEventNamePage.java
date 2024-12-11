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
	
	private Label nameLabel = new Label("Event Name\t\t:");
	private Label dateLabel = new Label("Event Date");
	private Label locationLabel = new Label("Event Location");
	private Label descLabel = new Label("Event Description: ");
	
	private VBox eventDataVB = new VBox();
	
	private TextField nameDataLabel = new TextField();
	private Label dateDataLabel;
	private Label locationDataLabel;
	private Text descDataLabel;
	
	private Button editButton = new Button("Edit Event Name");
	
	private Label errorLabel = new Label("");
	
	@Override
	public void setLayouts() {
		// TODO Auto-generated method stub
		eventLabelVB.getChildren().addAll(nameLabel, dateLabel, locationLabel, descLabel);
		eventDataVB.getChildren().addAll(nameDataLabel, dateDataLabel, locationDataLabel);
		
		eventHB.getChildren().addAll(eventLabelVB, eventDataVB);
		
		eventVB.getChildren().addAll(titleLabel, eventHB, descDataLabel, editButton, errorLabel);
		
		this.setTop(eventVB);
		
		eventLabelVB.setAlignment(Pos.TOP_LEFT);
		eventDataVB.setAlignment(Pos.TOP_LEFT);
		
		eventHB.setAlignment(Pos.TOP_LEFT);
		eventVB.setAlignment(Pos.CENTER);
		
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
		
		descDataLabel.setWrappingWidth(700);
		eventHB.setMaxWidth(700);
		eventVB.setSpacing(10);
	}

	@Override
	public void setEvents() {
		// TODO Auto-generated method stub
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
	}
	
	public EditEventNamePage(Event event) {
		this.event = event;
		
		Response<Event> eventData = EventOrganizerController.viewOrganizedEventDetails(this.event.getEventId());
		
		titleLabel.setText("Edit Event Name - " + eventData.data.getEventId());
		nameDataLabel.setText(eventData.data.getEventName());
		dateDataLabel = new Label(": " + eventData.data.getEventDate());
		locationDataLabel = new Label(": " + eventData.data.getEventLocation());
		descDataLabel = new Text(eventData.data.getEventDescription());
		
		
		initializePage();
	}

}
