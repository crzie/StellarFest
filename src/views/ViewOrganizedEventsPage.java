package views;

import java.util.List;

import controllers.EventOrganizerController;
import controllers.SceneController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import models.Event;
import models.User;
import utils.AuthUser;
import utils.Response;

public class ViewOrganizedEventsPage extends BorderPane implements Page{

	private User user = AuthUser.get();
	private Label viewLabel = new Label("View Organized Event");
	private Label userLabel = new Label(user.getUserId() + " - " + user.getUsername());
	private Label noticeLabel = new Label("click the selected list to view organized event details!");
	
	private Response<List<Event>> eventList = EventOrganizerController.viewOrganizedEvents(user.getUserId());
	
	private ObservableList<Event> eventData = FXCollections.observableArrayList(eventList.data);
	private TableView<Event> eventTable = new TableView<>(eventData);
	
	private TableColumn<Event, String> eventIdColumn = new TableColumn<>("Event Id");
	private TableColumn<Event, String> eventNameColumn = new TableColumn<>("Event Name");
	private TableColumn<Event, String> eventDateColumn = new TableColumn<>("Event Date");
	private TableColumn<Event, String> eventLocationColumn = new TableColumn<>("Event Location");
	
	private VBox vbox = new VBox();
	
	private Label errorLabel = new Label("");
	
	
	@Override
	public void setLayouts() {
		// TODO Auto-generated method stub		
		eventIdColumn.setCellValueFactory(new PropertyValueFactory<>("eventId"));
		eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
		eventDateColumn.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
		eventLocationColumn.setCellValueFactory(new PropertyValueFactory<>("eventLocation"));
		
		eventTable.getColumns().addAll(eventIdColumn, eventNameColumn, eventDateColumn, eventLocationColumn);
		
		vbox.getChildren().addAll(viewLabel, userLabel, noticeLabel);
		
		this.setTop(vbox);
		this.setCenter(eventTable);
		this.setBottom(errorLabel);
		this.setPadding(new Insets(10));
	}

	@Override
	public void setStyles() {
		// TODO Auto-generated method stub
		viewLabel.setFont(Font.font("System", FontWeight.BOLD, 28));
		userLabel.setFont(Font.font("System", FontWeight.BOLD, 12));
		noticeLabel.setFont(Font.font("System", FontWeight.NORMAL, 12));
		
		errorLabel.setStyle("-fx-fill: red;");
		
		viewLabel.setPadding(new Insets(20, 0, 20, 0));
		noticeLabel.setPadding(new Insets(0, 0, 10, 0));
		eventIdColumn.setMinWidth(50);
		eventNameColumn.setMinWidth(250);
		eventDateColumn.setMinWidth(80);
		eventLocationColumn.setMinWidth(250);
	}

	@Override
	public void setEvents() {
		// TODO Auto-generated method stub
		if(eventList.isSuccess) {
			eventTable.setOnMouseClicked(e ->{
				Event event = eventTable.getSelectionModel().getSelectedItem();
				SceneController.moveScene("view organized event details", event);
			});
		}
		else {
			errorLabel.setText(eventList.message);
		}
		
		
	}
	
	public ViewOrganizedEventsPage() {
		initializePage();
	}

}
