package views;

import java.util.List;

import controllers.EventController;
import controllers.GuestController;
import controllers.InvitationController;
import controllers.SceneController;
import controllers.VendorController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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

public class ViewAcceptedEventPage extends VBox implements Page{
	private User user = AuthUser.get();
	
	private List<Event> events = user.getUserRole().equalsIgnoreCase("Vendor") ? 
			 VendorController.viewAcceptedEvents(user.getUserEmail()).data :
			 GuestController.viewAcceptedEvents(user.getUserEmail()).data ;
			 
	private TableView<Event> table = new TableView<Event>();
	private TableColumn<Event, String> nameCol = new TableColumn("Event Name");
			 
	private VBox vbox = new VBox();
	private Text titleText = new Text("View Accepted Events");
	private Button actionButton = new Button("Details");
	private Button backButton = new Button ("Back");

	@Override
	public void setLayouts() {
		nameCol.setCellValueFactory(
                new PropertyValueFactory<Event, String>("eventName"));
		
		actionButton.setDisable(true);
		
		ObservableList<Event> observableEvent = FXCollections.observableArrayList(events);
	    table.setItems(observableEvent);
		table.getColumns().addAll(nameCol);
		
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
	        nameCol.setPrefWidth(tableWidth);
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
            	SceneController.moveScene("view accepted events detail", selectedEvent.getEventId());;
            }
        });
		
		backButton.setOnMouseClicked((e) -> {
			SceneController.moveScene("home");
		});
	}
	
	public ViewAcceptedEventPage() {
		initializePage();
	}

}
