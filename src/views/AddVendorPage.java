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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import models.Event;
import models.User;
import models.Vendor;
import utils.Response;

public class AddVendorPage extends VBox implements Page{
	private Event event;
	ObservableList<Vendor> vendorList;
	TableView<Vendor> vendorTable;
	
	TableColumn<Vendor, Boolean> checkBoxColumn = new TableColumn<>("Select");
	TableColumn<Vendor, String> vendorIdColumn = new TableColumn<>("Vendor ID");
	TableColumn<Vendor, String> vendorEmailColumn = new TableColumn<>("Vendor Email");
	TableColumn<Vendor, String> vendorNameColumn = new TableColumn<>("Vendor Username");
	
	Label errorLabel = new Label("");
	Button inviteButton = new Button("Invite");
	
	GridPane gp = new GridPane();
	
	private ArrayList<Vendor> userSelected = new ArrayList<>();
	
	@Override
	public void setLayouts() {
		// TODO Auto-generated method stub
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
			}
		});
		vendorIdColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUserId()));
		vendorEmailColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUserEmail()));
		vendorNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUsername()));
		
		vendorTable.getColumns().addAll(checkBoxColumn, vendorIdColumn, vendorEmailColumn, vendorNameColumn);
		

		this.setAlignment(Pos.TOP_CENTER);
		this.getChildren().addAll(vendorTable, inviteButton, errorLabel);
	}

	@Override
	public void setStyles() {
		// TODO Auto-generated method stub
		gp.setHgap(5);
		gp.setVgap(5);
		vendorTable.setMaxHeight(300);
		vendorTable.setMinWidth(800);
		checkBoxColumn.setMinWidth(50);
		vendorIdColumn.setMinWidth(90);
		vendorEmailColumn.setMinWidth(250);
		vendorNameColumn.setMinWidth(250);
		inviteButton.setPadding(new Insets(6, 20, 6, 20));
		this.setMargin(inviteButton, new Insets(20));
	}

	@Override
	public void setEvents() {
		// TODO Auto-generated method stub
		inviteButton.setOnMouseClicked(e ->{
			ArrayList<User> selectedVendors = new ArrayList<>();
            
			if (userSelected.isEmpty()) {
                errorLabel.setText("Please select at least one vendor!");
            } 
			
			for (User user : userSelected) {
            	InvitationController.sendInvitation(user.getUserEmail()); 
            }
//			
//			if(response.isSuccess) {
//				SceneController.moveScene("home");
//			}
//			else {
//				errorLabel.setManaged(true);
//				errorLabel.setText(response.message);
//			}
			
			userSelected.removeAll(userSelected);
			SceneController.moveScene("home");
		});
	}
	
	public AddVendorPage(Event event) {
		this.event = event;

		Response<List<Vendor>> vendorsResponse = EventOrganizerController.getVendors(event.getEventId());
		
		vendorList = FXCollections.observableArrayList(vendorsResponse.data);
		vendorTable = new TableView<>(vendorList);
		
		initializePage();
	}

}
