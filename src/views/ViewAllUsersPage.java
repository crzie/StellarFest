package views;

import java.util.List;

import controllers.AdminController;
import controllers.SceneController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
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
import models.Event;
import models.User;
import utils.Response;

public class ViewAllUsersPage extends BorderPane implements Page {
	
	private Label viewAllUsersLabel = new Label("View All Users");
	private Label noticeLabel = new Label("Click on the selected user row on the table, then click on the delete button to see delete a user!");
	
	private Response<List<User>> userData = AdminController.getAllUsers();
	
	private ObservableList<User> userList = FXCollections.observableArrayList(userData.data);
	private TableView<User> userTable = new TableView<>(userList);
	
	private TableColumn<User, String> userIdColumn = new TableColumn<>("User ID");
	private TableColumn<User, String> userEmailColumn = new TableColumn<>("User Email");
	private TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
	private TableColumn<User, String> userRoleColumn = new TableColumn<>("User Role");
	
	private String eventId;
	
	private VBox labelVBox = new VBox();

	private Button deleteButton = new Button("Delete");
	private Button backButton = new Button("Back");
	
	private HBox hb = new HBox();

	@Override
	public void setLayouts() {
		
		// memanggil atribut id, email, username, serta role yang dimiliki model user untuk kolom pada tabel user
		userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
		userEmailColumn.setCellValueFactory(new PropertyValueFactory<>("userEmail"));
		usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
		userRoleColumn.setCellValueFactory(new PropertyValueFactory<>("userRole"));
		
		// memasukkan semua kolom ke dalam tabel user
		userTable.getColumns().addAll(userIdColumn, userEmailColumn, usernameColumn, userRoleColumn);
		
		// memasukkan label judul serta informasi ke dalam vbox
		labelVBox.getChildren().addAll(viewAllUsersLabel, noticeLabel);
		// memasukkan button back dan delete ke dalam hbox
		hb.getChildren().addAll(backButton, deleteButton);
		
		// memasukkan elemen ke layout borderpane
		this.setTop(labelVBox);
		this.setCenter(userTable);
		this.setBottom(hb);
		
	}

	@Override
	public void setStyles() {
		// mengatur style untuk teks pada label
		viewAllUsersLabel.setFont(Font.font("System", FontWeight.BOLD, 28));
		noticeLabel.setFont(Font.font("System", FontWeight.NORMAL, 12));
		
		// memberikan padding bagian bawah untuk label informasi
		noticeLabel.setPadding(new Insets(0, 0, 10, 0));
		
		// membuat tiap ukuran kolom sama besarnya
		userIdColumn.prefWidthProperty().bind(userTable.widthProperty().divide(4));
		userEmailColumn.prefWidthProperty().bind(userTable.widthProperty().divide(4));
		usernameColumn.prefWidthProperty().bind(userTable.widthProperty().divide(4));
		userRoleColumn.prefWidthProperty().bind(userTable.widthProperty().divide(4));
		
		// memberikan padding untuk border pane page ini
		this.setPadding(new Insets(10));
		// memberikan padding bagian bawah untuk label judul
		viewAllUsersLabel.setPadding(new Insets(0, 0, 5, 0));
		
		// memberikan margin bagian bawah pada tabel user
		this.setMargin(userTable, new Insets(0, 0, 10, 0));
		
		// memberikan spacing untuk hbox
		hb.setSpacing(10);
	}

	@Override
	public void setEvents() {
		// membuat delete button dapat berfungsi setelah memilih baris tabel user yang ingin dihapus
		deleteButton.setOnAction(e -> {
			TableSelectionModel<User> tsm = userTable.getSelectionModel();
			tsm.setSelectionMode(SelectionMode.SINGLE);
			User user = tsm.getSelectedItem();
			
			AdminController.deleteUser(user.getUserId());
			refreshTable();
		});
		
		// ketika tombol back diklik akan kembali ke halaman home
		backButton.setOnMouseClicked(e -> {
			SceneController.moveScene("home");
		});
		
	}
	
	public void refreshTable() {
		// setelah menghapus data, tabel akan kembali dimasukkan dengan data terbaru
		userList.clear();
		userData = AdminController.getAllUsers();
		userList = FXCollections.observableArrayList(userData.data);
		userTable.setItems(userList);
	}
	
	public ViewAllUsersPage() {
		// melakukan inisialisasi untuk halaman
		initializePage();
	}

}
