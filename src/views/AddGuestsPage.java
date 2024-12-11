package views;

import java.util.ArrayList;
import java.util.List;

import controllers.EventOrganizerController;
import controllers.InvitationController;
import controllers.SceneController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import models.Event;
import models.Guest;
import models.User;
import utils.Response;

public class AddGuestsPage extends VBox implements Page{

	private Event event;
	
	private Label titleLabel;
	
	private ObservableList<Guest> guestList;
	private TableView<Guest> guestTable;
	
	private TableColumn<Guest, Boolean> checkBoxColumn = new TableColumn<>("Select");
	private TableColumn<Guest, String> guestIdColumn = new TableColumn<>("Guest ID");
	private TableColumn<Guest, String> guestEmailColumn = new TableColumn<>("Guest Email");
	private TableColumn<Guest, String> guestNameColumn = new TableColumn<>("Guest Username");
	
	private Label errorLabel = new Label("");
	private Button inviteButton = new Button("Invite");
	
	private ArrayList<Guest> userSelected = new ArrayList<>();
	private ScrollPane sp = new ScrollPane();
	
	@Override
	public void setLayouts() {
		// TODO Auto-generated method stub
		checkBoxColumn.setCellFactory(column -> new TableCell<>() {
			private final CheckBox checkBox = new CheckBox();{
				checkBox.setOnAction(e -> {
                    Guest user = getTableView().getItems().get(getIndex());
                    
                    if (checkBox.isSelected()) {
                        userSelected.add(user);
                    } else {
                        userSelected.remove(user);
                    }
                });
				
				setGraphic(checkBox);
				setAlignment(Pos.CENTER);
			}
		});
		
		guestIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
		guestEmailColumn.setCellValueFactory(new PropertyValueFactory<>("userEmail"));
		guestNameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
		
		guestTable.getColumns().addAll(checkBoxColumn, guestIdColumn, guestEmailColumn, guestNameColumn);
		
		sp.setContent(guestTable);
		sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		sp.setHbarPolicy(ScrollBarPolicy.NEVER);
		
		this.getChildren().addAll(titleLabel, guestTable, inviteButton, errorLabel);
		this.setSpacing(10);
		this.setMargin(guestTable, new Insets(10));
		this.setAlignment(Pos.TOP_CENTER);
	}

	@Override
	public void setStyles() {
		// TODO Auto-generated method stub
		titleLabel.setFont(Font.font("System", FontWeight.BOLD, 28));
		
		errorLabel.setManaged(false);
		errorLabel.setStyle("-fx-fill: red;");
		
		guestTable.setMaxHeight(500);
		guestTable.setMaxWidth(800);
		checkBoxColumn.setMinWidth(50);
		guestIdColumn.setMinWidth(150);
		guestEmailColumn.setMinWidth(290);
		guestNameColumn.setMinWidth(285);
		inviteButton.setPadding(new Insets(6, 20, 6, 20));
		this.setMargin(inviteButton, new Insets(20));
	}

	@Override
	public void setEvents() {
		// TODO Auto-generated method stub
		inviteButton.setOnMouseClicked(e ->{
			ArrayList<User> selectedGuests = new ArrayList<>();
            
			if (userSelected.isEmpty()) {
				errorLabel.setManaged(true);
                errorLabel.setText("Please select at least one guest!");
            } 
			
			for (User user : userSelected) {
            	InvitationController.sendInvitation(user.getUserEmail(), event.getEventId()); 
            }
			
			if(!userSelected.isEmpty()) {
				userSelected.removeAll(userSelected);
				SceneController.moveScene("home");
			}
		});
	}
	
	public AddGuestsPage(Event event) {
		this.event = event;

		titleLabel = new Label("Add Guest - " + event.getEventId());
		
		Response<List<Guest>> guestsResponse = EventOrganizerController.getGuests(event.getEventId());
		
		guestList = FXCollections.observableArrayList(guestsResponse.data);
		guestTable = new TableView<>(guestList);
		
		initializePage();
	}

}
