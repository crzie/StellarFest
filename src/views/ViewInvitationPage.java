package views;

import java.util.List;

import controllers.InvitationController;
import controllers.SceneController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import models.Event;
import models.Invitation;
import models.User;
import utils.AuthUser;

public class ViewInvitationPage extends VBox implements Page{

	private TableView<Event> table = new TableView<Event>();
	private Text titleText = new Text("View Invitations");
	private User user = AuthUser.get();
	private List<Event> events = InvitationController.getInvitations(user.getUserEmail()).data;
	private TableColumn<Event, String> nameCol = new TableColumn("Event Name");
	private TableColumn<Event, String> dateCol = new TableColumn("Event Date");
	private TableColumn<Event, String> locCol = new TableColumn("Event Location");
	private TableColumn<Event, String> descCol = new TableColumn("Event Description");
	private TableColumn<Event, String> organizerCol = new TableColumn("Organizer");
	private Button actionButton = new Button("Accept");
	private Button backButton = new Button ("Back");
	private VBox vbox = new VBox();
    	
	@Override
	public void setLayouts() {
		nameCol.setCellValueFactory(
	                new PropertyValueFactory<Event, String>("eventName"));
		dateCol.setCellValueFactory(
                new PropertyValueFactory<Event, String>("eventDate"));
		locCol.setCellValueFactory(
                new PropertyValueFactory<Event, String>("eventLocation"));
		descCol.setCellValueFactory(
                new PropertyValueFactory<Event, String>("eventDescription"));
		organizerCol.setCellValueFactory(
                new PropertyValueFactory<Event, String>("organizerId"));
		
		actionButton.setDisable(true);
		
		ObservableList<Event> observableEvent = FXCollections.observableArrayList(events);
	    table.setItems(observableEvent);
		table.getColumns().addAll(nameCol,dateCol,locCol,descCol,organizerCol);
		
		vbox.getChildren().addAll(table, actionButton);
        this.getChildren().addAll(titleText, vbox, backButton);
	}

	@Override
	public void setStyles() {
		titleText.setFont(Font.font("System", FontWeight.BOLD, 28));
		
		this.setSpacing(15);
        this.setPadding(new Insets(10, 10, 10, 10));
        this.setMargin(actionButton, new Insets(10, 0, 0, 0));
        vbox.setAlignment(Pos.CENTER);
        
        backButton.setMaxWidth(50);
        actionButton.setMaxWidth(100);
        actionButton.setStyle("-fx-font-size: 14px; -fx-padding: 8;");
        
		table.widthProperty().addListener((obs, oldWidth, newWidth) -> {
	        double tableWidth = newWidth.doubleValue();
	        nameCol.setPrefWidth(tableWidth / 5);
	        dateCol.setPrefWidth(tableWidth / 5);
	        locCol.setPrefWidth(tableWidth / 5);
	        descCol.setPrefWidth(tableWidth / 5);
	        organizerCol.setPrefWidth(tableWidth / 5);
	    });
	}

	@Override
	public void setEvents() {
		table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            actionButton.setDisable(newSelection == null); // Disable if no selection
        });
		
		actionButton.setOnAction(event -> {
            Event selectedEvent = table.getSelectionModel().getSelectedItem();
            if (selectedEvent != null) {
                InvitationController.acceptInvitation(user.getUserId(), selectedEvent.getEventId());

                List<Event> updatedEvents = InvitationController.getInvitations(user.getUserEmail()).data;
                ObservableList<Event> observableEvent = table.getItems();
                observableEvent.clear();
                observableEvent.addAll(updatedEvents);
            }
        });
		
		backButton.setOnMouseClicked((e) -> {
			SceneController.moveScene("home");
		});
		
	}
	
	public ViewInvitationPage() {
		initializePage();
	}

}
