package views;

import java.util.List;

import controllers.EventOrganizerController;
import controllers.SceneController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.cell.PropertyValueFactory;
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

public class ViewOrganizedEventDetailsPage extends BorderPane implements Page{

	private Event event;
	
	private Label detailLabel;
	
//	event details
	private VBox eventVB = new VBox();
	private HBox eventHB = new HBox();
	
	private VBox eventLabelVB = new VBox();
	
	private Label nameLabel = new Label("Event Name\t\t: ");
	private Label dateLabel = new Label("Event Date\t\t: ");
	private Label locationLabel = new Label("Event Location\t: ");
	private Label descLabel = new Label("Event Description\t: ");
	
	private VBox eventDataVB = new VBox();
	
	private Label nameDataLabel;
	private Label dateDataLabel;
	private Label locationDataLabel;
	private Text descDataLabel;
	
	private Button editEventButton = new Button("Edit Event Name");
	
//	guest data
	private VBox guestVB = new VBox();
	
	private Label guestLabel = new Label("Guest:");
	private ObservableList<Guest> guestList;
	private TableView<Guest> guestTable;
	private TableColumn<Guest, String> guestIdColumn = new TableColumn<>("Guest Id");
	private TableColumn<Guest, String> guestEmailColumn = new TableColumn<>("Guest Email");
	private TableColumn<Guest, String> guestNameColumn = new TableColumn<>("Guest Username");
	private Button addGuestButton = new Button("Add Guest");
	
//	vendor data
	private VBox vendorVB = new VBox();
	
	private Label vendorLabel = new Label("Vendor:");
	private ObservableList<Vendor> vendorList;
	private TableView<Vendor> vendorTable;
	private TableColumn<Vendor, String> vendorIdColumn = new TableColumn<>("Vendor Id");
	private TableColumn<Vendor, String> vendorEmailColumn = new TableColumn<>("Vendor Email");
	private TableColumn<Vendor, String> vendorNameColumn = new TableColumn<>("Vendor Username");
	private Button addVendorButton = new Button("Add Vendor");
	
	private HBox tableHbox = new HBox();
	private ScrollPane sp1 = new ScrollPane();
	private ScrollPane sp2 = new ScrollPane();
	
	private Button backButton = new Button("Back");
	
	@Override
	public void setLayouts() {
		// TODO Auto-generated method stub
		
//		masukin kolom buat guest
		guestIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
		guestEmailColumn.setCellValueFactory(new PropertyValueFactory<>("userEmail"));
		guestNameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
		
		guestTable.getColumns().addAll(guestIdColumn, guestEmailColumn, guestNameColumn);
		
//		masukin kolom buat vendor
		vendorIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
		vendorEmailColumn.setCellValueFactory(new PropertyValueFactory<>("userEmail"));
		vendorNameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
		
		vendorTable.getColumns().addAll(vendorIdColumn, vendorEmailColumn, vendorNameColumn);
		
//		vbox and hbox
		eventLabelVB.getChildren().addAll(nameLabel, dateLabel, locationLabel, descLabel);
		eventDataVB.getChildren().addAll(nameDataLabel, dateDataLabel, locationDataLabel, descDataLabel);
		
		eventHB.getChildren().addAll(eventLabelVB, eventDataVB);
		
		eventVB.getChildren().addAll(detailLabel, eventHB, editEventButton);
		guestVB.getChildren().addAll(guestLabel, guestTable, addGuestButton);
		vendorVB.getChildren().addAll(vendorLabel, vendorTable, addVendorButton);
		
		tableHbox.getChildren().addAll(guestVB, vendorVB);
		
//		LAYOUT
		this.setTop(eventVB);
		this.setCenter(tableHbox);
		this.setBottom(backButton);
		
//		alignment
		eventLabelVB.setAlignment(Pos.TOP_LEFT);
		eventDataVB.setAlignment(Pos.TOP_LEFT);
		
		eventHB.setAlignment(Pos.TOP_LEFT);
		eventVB.setAlignment(Pos.CENTER);
		
		this.setAlignment(detailLabel, Pos.CENTER);
		this.setAlignment(eventVB, Pos.CENTER);
		
		tableHbox.setAlignment(Pos.TOP_CENTER);
		
//		scrollpane for table
		sp1.setContent(guestTable);
		sp1.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		sp1.setHbarPolicy(ScrollBarPolicy.NEVER);
		
		sp2.setContent(vendorTable);
		sp2.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		sp2.setHbarPolicy(ScrollBarPolicy.NEVER);
	}

	@Override
	public void setStyles() {
		// TODO Auto-generated method stub
		detailLabel.setFont(Font.font("System", FontWeight.BOLD, 28));
		nameLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
		dateLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
		locationLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
		descLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
		guestLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
		vendorLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
		nameDataLabel.setFont(Font.font("System", FontWeight.NORMAL, 14));
		dateDataLabel.setFont(Font.font("System", FontWeight.NORMAL, 14));
		locationDataLabel.setFont(Font.font("System", FontWeight.NORMAL, 14));
		descDataLabel.setFont(Font.font("System", FontWeight.NORMAL, 14));
		
		detailLabel.setPadding(new Insets(10));
		
		descDataLabel.setWrappingWidth(500);
		eventHB.setMaxWidth(700);
		eventVB.setSpacing(10);
		
		guestTable.setMaxHeight(270);
		vendorTable.setMaxHeight(270);
		
		guestVB.setSpacing(10);
		vendorVB.setSpacing(10);
		
		guestIdColumn.setMinWidth(800/6 - 20);
		guestNameColumn.setMinWidth(800/6 - 5);
		guestEmailColumn.setMinWidth(800/6);
		vendorIdColumn.setMinWidth(800/6 - 20);
		vendorNameColumn.setMinWidth(800/6 - 5);
		vendorEmailColumn.setMinWidth(800/6);
		
		tableHbox.setSpacing(10);
		tableHbox.setPadding(new Insets(10));
		tableHbox.setMaxHeight(300);
		
		this.setMargin(backButton, new Insets(0, 20, 20, 20));
	}

	@Override
	public void setEvents() {
		// TODO Auto-generated method stub
		editEventButton.setOnAction(e->{
			SceneController.moveScene("edit event name", this.event);
		});
		
		addGuestButton.setOnAction(e-> {
			SceneController.moveScene("add guests", this.event);
		});
		
		addVendorButton.setOnAction(e ->{
			SceneController.moveScene("add vendors", this.event);
		});
		
		backButton.setOnMouseClicked(e -> {
			SceneController.moveScene("view organized events");
		});
	}

	public ViewOrganizedEventDetailsPage(Event event) {
		initializeData(event);
		initializePage();
	}
	
	public void initializeData(Event event) {
		this.event = event;
		
		Response<Event> eventData = EventOrganizerController.viewOrganizedEventDetails(this.event.getEventId());
		Response<List<Guest>> guestData = EventOrganizerController.getGuestsByTransactionId(this.event.getEventId());
		Response<List<Vendor>> vendorData = EventOrganizerController.getVendorsByTransactionId(this.event.getEventId());
		
		detailLabel = new Label("View Organized Event Details - " + eventData.data.getEventId());
		nameDataLabel = new Label(eventData.data.getEventName());
		dateDataLabel = new Label(eventData.data.getEventDate());
		locationDataLabel = new Label(eventData.data.getEventLocation());
		descDataLabel = new Text(eventData.data.getEventDescription());
		
		guestList = FXCollections.observableArrayList(guestData.data);
		guestTable = new TableView<>(guestList);
		
		vendorList = FXCollections.observableArrayList(vendorData.data);
		vendorTable = new TableView<>(vendorList);
	}
}
