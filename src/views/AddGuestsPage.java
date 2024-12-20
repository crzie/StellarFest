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
import javafx.scene.text.Text;
import models.Event;
import models.Guest;
import models.User;
import utils.Response;

public class AddGuestsPage extends VBox implements Page{

	private Event event;
	
	private Label titleLabel;
	
	private VBox center = new VBox();
	
	//tabel berisi list guest yang belum terinvite pada event tersebut 
	private ObservableList<Guest> guestList;
	private TableView<Guest> guestTable;
	
	private TableColumn<Guest, Boolean> checkBoxColumn = new TableColumn<>("Select");
	private TableColumn<Guest, String> guestIdColumn = new TableColumn<>("Guest ID");
	private TableColumn<Guest, String> guestEmailColumn = new TableColumn<>("Guest Email");
	private TableColumn<Guest, String> guestNameColumn = new TableColumn<>("Guest Username");
	
	private Text errorLabel = new Text("");
	private Button inviteButton = new Button("Invite");
	
	private ArrayList<Guest> userSelected = new ArrayList<>();
	private ScrollPane sp = new ScrollPane();
	
	private Button backButton = new Button("Back");
	
	@Override
	public void setLayouts() {
		//membuat checkbox pada baris tabel
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
		
		//memasukkan kolom tabel
		guestTable.getColumns().addAll(checkBoxColumn, guestIdColumn, guestEmailColumn, guestNameColumn);
		
		sp.setContent(guestTable);
		sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		sp.setHbarPolicy(ScrollBarPolicy.NEVER);
		
		center.getChildren().addAll(guestTable, errorLabel, inviteButton);
		center.setAlignment(Pos.CENTER);
		
		//layout page
		this.getChildren().addAll(titleLabel, center, backButton);
		this.setSpacing(10);
		this.setPadding(new Insets(20));;
		this.setAlignment(Pos.TOP_LEFT);
	}

	@Override
	public void setStyles() {
		// TODO Auto-generated method stub
		titleLabel.setFont(Font.font("System", FontWeight.BOLD, 28));
		
		errorLabel.setManaged(false);
		errorLabel.setStyle("-fx-fill: red;");
		
		//style tabel
		guestTable.setMaxHeight(500);
		guestTable.setMaxWidth(800);
		checkBoxColumn.setMinWidth(50);
		guestIdColumn.setMinWidth(150);
		guestEmailColumn.setMinWidth(280);
		guestNameColumn.setMinWidth(275);
		inviteButton.setPadding(new Insets(6, 20, 6, 20));
		backButton.setPadding(new Insets(6, 20, 6, 20));
		this.setMargin(inviteButton, new Insets(20));
	}

	@Override
	public void setEvents() {
		// jika button invite di click akan melakukan validasi dan mengarahkan pada controller invitation
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
		
		
		//back button untuk mengarah ke page view organized event details 
		backButton.setOnMouseClicked(e -> {
			SceneController.moveScene("view organized event details", event);
		});
	}
	
	//menampilkan title beserta memasukkan data pada tabel
	public AddGuestsPage(Event event) {
		this.event = event;

		titleLabel = new Label("Add Guest - " + event.getEventName());
		
		Response<List<Guest>> guestsResponse = EventOrganizerController.getGuests(event.getEventId());
		
		guestList = FXCollections.observableArrayList(guestsResponse.data);
		guestTable = new TableView<>(guestList);
		
		initializePage();
	}

}
