package views;

import java.util.List;

import controllers.AdminController;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import models.Event;
import models.User;
import utils.Response;

public class ViewAllUsersPage extends BorderPane implements Page {
	
private Label viewAllUsersLabel = new Label("View All Users");
	
	private Response<List<User>> userData = AdminController.getAllUsers();
	
	private ObservableList<User> userList = FXCollections.observableArrayList(userData.data);
	private TableView<User> userTable = new TableView<>(userList);
	
	private TableColumn<User, String> userIdColumn = new TableColumn<>("User ID");
	private TableColumn<User, String> userEmailColumn = new TableColumn<>("User Email");
	private TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
	
	private String eventId;

	private Button deleteButton = new Button("Delete");

	@Override
	public void setLayouts() {
		userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
		userEmailColumn.setCellValueFactory(new PropertyValueFactory<>("userEmail"));
		usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
		
		userTable.getColumns().addAll(userIdColumn, userEmailColumn, usernameColumn);
		
		this.setTop(viewAllUsersLabel);
		this.setCenter(userTable);
		this.setBottom(deleteButton);
		
	}

	@Override
	public void setStyles() {
		viewAllUsersLabel.setFont(Font.font("System", FontWeight.BOLD, 28));
		
		userIdColumn.prefWidthProperty().bind(userTable.widthProperty().divide(3));
		userEmailColumn.prefWidthProperty().bind(userTable.widthProperty().divide(3));
		usernameColumn.prefWidthProperty().bind(userTable.widthProperty().divide(3));
		
		this.setPadding(new Insets(10));
		viewAllUsersLabel.setPadding(new Insets(0, 0, 5, 0));
		
		this.setMargin(userTable, new Insets(0, 0, 10, 0));
	}

	@Override
	public void setEvents() {
		deleteButton.setOnAction(e -> {
			TableSelectionModel<User> tsm = userTable.getSelectionModel();
			tsm.setSelectionMode(SelectionMode.SINGLE);
			User user = tsm.getSelectedItem();
			
			AdminController.deleteUser(user.getUserId());
			refreshTable();
		});
		
	}
	
	public void refreshTable() {
		userList.clear();
		userData = AdminController.getAllUsers();
		userList = FXCollections.observableArrayList(userData.data);
		userTable.setItems(userList);
	}
	
	public ViewAllUsersPage() {
		initializePage();
	}

}
