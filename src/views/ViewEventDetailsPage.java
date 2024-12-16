package views;

import java.util.List;

import controllers.AdminController;
import controllers.SceneController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

public class ViewEventDetailsPage extends BorderPane implements Page{
	
	private Event event;
		
	private Label detailLabel;
	
	private Response<Event> eventData;
	
	private Label nameLabel = new Label("Event Name");
	private Label dateLabel = new Label("Event Date");
	private Label locationLabel = new Label("Event Location");
	private Label descLabel = new Label("Event Description");
	
	private VBox eventLabelVB = new VBox();
	
	private Label nameDataLabel;
	private Label dateDataLabel;
	private Label locationDataLabel;
	private Text descDataLabel;
	
	private VBox eventDataVB = new VBox();
	
	private HBox eventHB = new HBox();
	private VBox eventVB = new VBox();
	
	private ScrollPane sp1 = new ScrollPane();
	private ScrollPane sp2 = new ScrollPane();
	
	private Label guestTableLabel = new Label("Guest:");
	private Label vendorTableLabel = new Label("Vendor:");
	
	private Response<List<Guest>> guestData;
	private Response<List<Vendor>> vendorData;
	
	private ObservableList<Guest> guestList;
	private TableView<Guest> guestTable;
	private ObservableList<Vendor> vendorList;
	private TableView<Vendor> vendorTable;
	
	private TableColumn<Guest, String> guestIdColumn = new TableColumn<>("Guest ID");
	private TableColumn<Guest, String> guestEmailColumn = new TableColumn<>("Guest Email");
	private TableColumn<Guest, String> guestNameColumn = new TableColumn<>("Guest Name");
	
	private TableColumn<Vendor, String> vendorIdColumn = new TableColumn<>("Vendor ID");
	private TableColumn<Vendor, String> vendorEmailColumn = new TableColumn<>("Vendor Email");
	private TableColumn<Vendor, String> vendorNameColumn = new TableColumn<>("Vendor Name");
	
	private HBox tableContainer = new HBox();
	private VBox guestTableContainer = new VBox();
	private VBox vendorTableContainer = new VBox();
	
	// asumsi terdapat button untuk kembali ke halaman view all events
	private Button backButton = new Button("Back");
	
	@Override
	public void setLayouts() {
		// memasukkan label ke dalam vbox
		eventLabelVB.getChildren().addAll(nameLabel, dateLabel, locationLabel, descLabel);
		eventDataVB.getChildren().addAll(nameDataLabel, dateDataLabel, locationDataLabel, descDataLabel);
		
		// memasukkan vbox detail event ke dalam hbox
		eventHB.getChildren().addAll(eventLabelVB, eventDataVB);
		
		// memasukkan hbox detail event serta judul page ke dalam vbox 
		eventVB.getChildren().addAll(detailLabel, eventHB);
		
		// memanggil atribut id, email, serta username yang dimiliki model user untuk kolom pada tabel guest
		guestIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
		guestEmailColumn.setCellValueFactory(new PropertyValueFactory<>("userEmail"));
		guestNameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
		
		// memanggil atribut id, email, serta username yang dimiliki model user untuk kolom pada tabel vendor
		vendorIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
		vendorEmailColumn.setCellValueFactory(new PropertyValueFactory<>("userEmail"));
		vendorNameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
		
		// memasukkan kolom guest pada tabel guest serta kolom vendor pada tabel vendor
		guestTable.getColumns().addAll(guestIdColumn, guestEmailColumn, guestNameColumn);
		vendorTable.getColumns().addAll(vendorIdColumn, vendorEmailColumn, vendorNameColumn);
		
		// membuat scroll pane untuk tabel
		sp1.setContent(guestTable);
		sp1.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		sp1.setHbarPolicy(ScrollBarPolicy.NEVER);
		sp2.setContent(vendorTable);
		sp2.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		sp2.setHbarPolicy(ScrollBarPolicy.NEVER);
		
		// memasukkan judul tabel serta tabel ke dalam container
		guestTableContainer.getChildren().addAll(guestTableLabel, guestTable);
		vendorTableContainer.getChildren().addAll(vendorTableLabel, vendorTable);
		
		// memasukkan kedua tabel ke dalam container
		tableContainer.getChildren().addAll(guestTableContainer, vendorTableContainer);
		
		// memasukkan detail event serta tabel ke dalam layout borderpane
		this.setTop(eventVB);
		this.setCenter(tableContainer);
		this.setBottom(backButton);
	}

	@Override
	public void setStyles() {
		// memberikan style untuk label yang terdapat pada page
		detailLabel.setFont(Font.font("System", FontWeight.BOLD, 28));
		
		nameLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
		dateLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
		locationLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
		descLabel.setFont(Font.font("System", FontWeight.BOLD, 14));

		nameDataLabel.setFont(Font.font("System", FontWeight.NORMAL, 14));
		dateDataLabel.setFont(Font.font("System", FontWeight.NORMAL, 14));
		locationDataLabel.setFont(Font.font("System", FontWeight.NORMAL, 14));
		descDataLabel.setFont(Font.font("System", FontWeight.NORMAL, 14));
		
		guestTableLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
		vendorTableLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
		
		// membuat tabel guest dan vendor memiliki ukuran selebar border pane page dibagi 2
		guestTable.prefWidthProperty().bind(this.widthProperty().divide(2));
		vendorTable.prefWidthProperty().bind(this.widthProperty().divide(2));
		
		// membuat tiap kolom pada tabel guest memiliki ukuran lebar yang sama besarnya
		guestIdColumn.prefWidthProperty().bind(guestTableContainer.widthProperty().divide(3));
		guestEmailColumn.prefWidthProperty().bind(guestTableContainer.widthProperty().divide(3));
		guestNameColumn.prefWidthProperty().bind(guestTableContainer.widthProperty().divide(3));
		
		// membuat tiap kolom pada tabel vendor memiliki ukuran lebar yang sama besarnya
		vendorIdColumn.prefWidthProperty().bind(vendorTableContainer.widthProperty().divide(3));
		vendorEmailColumn.prefWidthProperty().bind(vendorTableContainer.widthProperty().divide(3));
		vendorNameColumn.prefWidthProperty().bind(vendorTableContainer.widthProperty().divide(3));
		
		// memberikan jarak antara tabel guest dengan vendor
		tableContainer.setSpacing(10);
		
		// memberikan padding pada satu page
		this.setPadding(new Insets(10));
		
		// memberikan margin bagian bawah antara detail event dengan tabel guest dan vendor
		this.setMargin(eventVB, new Insets(0, 0, 10, 0));
		eventVB.setSpacing(10);
		
		// memberikan spacing untuk judul tabel dengan tabel
		guestTableContainer.setSpacing(10);
		vendorTableContainer.setSpacing(10);
	}

	@Override
	public void setEvents() {
		// ketika tombol back diklik akan kembali ke halaman view all events
		backButton.setOnMouseClicked(e -> {
			SceneController.moveScene("view all events");
		});
		
	}
	
	public void initializeData(Event event) {
		// memasukkan event yang dipassing sebagai atribut event pada page ini
		this.event = event;
		
		// mengambil data menggunakan controller
		eventData = AdminController.viewEventDetails(event.getEventId());
		guestData = AdminController.getGuestsByTransactionID(event.getEventId());
		vendorData = AdminController.getVendorsByTransactionID(event.getEventId());
		
		// memasukkan data yang diambil ke dalam label
		detailLabel = new Label("View Event Details: " + eventData.data.getEventId());
		nameDataLabel = new Label(": " + eventData.data.getEventName());
		dateDataLabel = new Label(": " + eventData.data.getEventDate());
		locationDataLabel = new Label(": " + eventData.data.getEventLocation());
		descDataLabel = new Text(": " + eventData.data.getEventDescription());
		
		// memasukkan data guest dan vendor ke dalam tabel
		guestList = FXCollections.observableArrayList(guestData.data);
		guestTable = new TableView<>(guestList);
		vendorList = FXCollections.observableArrayList(vendorData.data);
		vendorTable = new TableView<>(vendorList);
	}
	
	public ViewEventDetailsPage(Event event) {
		// melakukan inisialisasi data serta halaman
		initializeData(event);
		initializePage();
	}


}
