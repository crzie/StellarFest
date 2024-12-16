package views;

import java.util.List;

import controllers.AdminController;
import controllers.SceneController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import models.Admin;
import models.Event;
import utils.AuthUser;
import utils.Response;

public class ViewAllEventsPage extends BorderPane implements Page {
	
	private Label viewAllEventsLabel = new Label("View All Events");
	private Label noticeLabel = new Label("Click on the selected event row on the table, then click on the details or delete button to see details or delete an event!");
	
	private Response<List<Event>> eventData = AdminController.viewAllEvents();
	
	private ObservableList<Event> eventList = FXCollections.observableArrayList(eventData.data);
	private TableView<Event> eventTable = new TableView<>(eventList);
	
	private TableColumn<Event, String> eventIdColumn = new TableColumn<>("Event ID");
	private TableColumn<Event, String> eventNameColumn = new TableColumn<>("Event Name");
	private TableColumn<Event, String> eventDateColumn = new TableColumn<>("Event Date");
	private TableColumn<Event, String> eventLocationColumn = new TableColumn<>("Event Location");
	private TableColumn<Event, String> eventDescriptionColumn = new TableColumn<>("Event Description");
	private TableColumn<Event, String> organizerIdColumn = new TableColumn<>("Organizer ID");
	
	private String eventId;
	
	private Button detailsButton = new Button("Details");
	private Button deleteButton = new Button("Delete");
	private Button backButton = new Button("Back");
	
	private VBox labelVBox = new VBox();
	private HBox buttonHBox = new HBox(); 
	
	@Override
	public void setLayouts() {
		// memanggil atribut id, name, date, location, description, organizer id yang dimiliki model event untuk kolom pada tabel event
		eventIdColumn.setCellValueFactory(new PropertyValueFactory<>("eventId"));
		eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
		eventDateColumn.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
		eventLocationColumn.setCellValueFactory(new PropertyValueFactory<>("eventLocation"));
		eventDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("eventDescription"));
		organizerIdColumn.setCellValueFactory(new PropertyValueFactory<>("organizerId"));
		
		// memasukkan kolom untuk tabel event
		eventTable.getColumns().addAll(eventIdColumn, eventNameColumn, eventDateColumn, eventLocationColumn, eventDescriptionColumn, organizerIdColumn);
		
		// memasukkan judul serta label pemberitahuan untuk vbox
		labelVBox.getChildren().addAll(viewAllEventsLabel, noticeLabel);
		// memasukkan button untuk hbox
		buttonHBox.getChildren().addAll(backButton, detailsButton, deleteButton);

		// memasukkan elemen untuk border pane page ini
		this.setTop(labelVBox);
		this.setCenter(eventTable);
		this.setBottom(buttonHBox);
	}

	@Override
	public void setStyles() {
		// mengatur style label 
		viewAllEventsLabel.setFont(Font.font("System", FontWeight.BOLD, 28));
		noticeLabel.setFont(Font.font("System", FontWeight.NORMAL, 12));
		noticeLabel.setPadding(new Insets(0, 0, 10, 0));
		
		// memastikan agar tiap kolom memiliki ukuran lebar yang sama besar
		eventIdColumn.prefWidthProperty().bind(eventTable.widthProperty().divide(6));
		eventNameColumn.prefWidthProperty().bind(eventTable.widthProperty().divide(6));
		eventDateColumn.prefWidthProperty().bind(eventTable.widthProperty().divide(6));
		eventLocationColumn.prefWidthProperty().bind(eventTable.widthProperty().divide(6));
		eventDescriptionColumn.prefWidthProperty().bind(eventTable.widthProperty().divide(6));
		organizerIdColumn.prefWidthProperty().bind(eventTable.widthProperty().divide(6));
		
		// memberikan padding pada border pane
		this.setPadding(new Insets(10));
		// memberikan padding bagian bawah untuk label judul
		viewAllEventsLabel.setPadding(new Insets(0, 0, 5, 0));
		
		// memberikan margin bagian bawah untuk tabel event
		this.setMargin(eventTable, new Insets(0, 0, 10, 0));
		// memberikan spacing antar hbox
		buttonHBox.setSpacing(10);
	}

	@Override
	public void setEvents() {
		// ketika details button diklik akan dibawa ke event details event yang dipilih
		detailsButton.setOnAction(e -> {
			TableSelectionModel<Event> tsm = eventTable.getSelectionModel();
			tsm.setSelectionMode(SelectionMode.SINGLE);
			Event event = tsm.getSelectedItem();

			SceneController.moveScene("view event details", event);
		});
		
		// ketika delete button diklik akan menghapus event yang dipilih
		deleteButton.setOnAction(e -> {
			TableSelectionModel<Event> tsm = eventTable.getSelectionModel();
			tsm.setSelectionMode(SelectionMode.SINGLE);
			Event event = tsm.getSelectedItem();
			
			AdminController.deleteEvent(event.getEventId());
			refreshTable();
		});
		
		// ketika back button diklik akan kembali ke halaman home
		backButton.setOnMouseClicked(e -> {
			SceneController.moveScene("home");
		});
		
	}
	
	public void refreshTable() {
		// setelah menghapus data, tabel akan kembali dimasukkan dengan data terbaru
		eventList.clear();
		eventData = AdminController.getAllEvents();
		eventList = FXCollections.observableArrayList(eventData.data);
		eventTable.setItems(eventList);
	}
	
	public ViewAllEventsPage() {
		initializePage();
	}

}
