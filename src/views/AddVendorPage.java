package views;

import java.util.ArrayList;
import java.util.List;

import controllers.EventController;
import controllers.EventOrganizerController;
import controllers.InvitationController;
import controllers.SceneController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import models.Event;
import models.User;
import models.Vendor;
import utils.Response;

public class AddVendorPage extends VBox implements Page{
	private Event event;
	
	private Label titleLabel;
	
	private VBox center = new VBox();
	
	//tabel berisi list vendor yang belum terinvite pada event tersebut
	private ObservableList<Vendor> vendorList;
	private TableView<Vendor> vendorTable;
	
	private TableColumn<Vendor, Boolean> checkBoxColumn = new TableColumn<>("Select");
	private TableColumn<Vendor, String> vendorIdColumn = new TableColumn<>("Vendor ID");
	private TableColumn<Vendor, String> vendorEmailColumn = new TableColumn<>("Vendor Email");
	private TableColumn<Vendor, String> vendorNameColumn = new TableColumn<>("Vendor Username");
	
	private Text errorLabel = new Text("");
	private Button inviteButton = new Button("Invite");
	
	private ArrayList<Vendor> userSelected = new ArrayList<>();
	private ScrollPane sp = new ScrollPane();
	
	private Button backButton = new Button("Back");
	
	@Override
	public void setLayouts() {
		// membuat checkbox pada baris tabel
		checkBoxColumn.setCellFactory(column -> new TableCell<>() {
			private final CheckBox checkBox = new CheckBox();{
				checkBox.setOnAction(e -> {
                    Vendor user = getTableView().getItems().get(getIndex());
                    
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
		
		vendorIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
		vendorEmailColumn.setCellValueFactory(new PropertyValueFactory<>("userEmail"));
		vendorNameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
		
		//memasukkan kolom table
		vendorTable.getColumns().addAll(checkBoxColumn, vendorIdColumn, vendorEmailColumn, vendorNameColumn);
		
		sp.setContent(vendorTable);
		sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		sp.setHbarPolicy(ScrollBarPolicy.NEVER);
		
		center.getChildren().addAll(vendorTable, errorLabel, inviteButton);
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
		vendorTable.setMaxHeight(500);
		vendorTable.setMaxWidth(800);
		checkBoxColumn.setMinWidth(50);
		vendorIdColumn.setMinWidth(150);
		vendorEmailColumn.setMinWidth(280);
		vendorNameColumn.setMinWidth(275);
		inviteButton.setPadding(new Insets(6, 20, 6, 20));
		backButton.setPadding(new Insets(6, 20, 6, 20));
		this.setMargin(inviteButton, new Insets(20));
	}

	@Override
	public void setEvents() {
		// jika button di invite di click akan melakukan
		inviteButton.setOnMouseClicked(e ->{
			ArrayList<User> selectedVendors = new ArrayList<>();
            
			if (userSelected.isEmpty()) {
				errorLabel.setManaged(true);
                errorLabel.setText("Please select at least one vendor!");
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
	public AddVendorPage(Event event) {
		this.event = event;

		titleLabel = new Label("Add Vendor - " + event.getEventName());
		
		Response<List<Vendor>> vendorsResponse = EventOrganizerController.getVendors(event.getEventId());
		
		vendorList = FXCollections.observableArrayList(vendorsResponse.data);
		vendorTable = new TableView<>(vendorList);
		
		initializePage();
	}

}
