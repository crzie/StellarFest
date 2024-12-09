package views;

import java.util.List;

import controllers.InvitationController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.Invitation;
import models.User;
import utils.AuthUser;

public class ViewInvitationPage extends VBox implements Page{

	private TableView<Invitation> table = new TableView<Invitation>();
	private Text titleText = new Text("View Invitations");
	private User user = AuthUser.get();
	private List<Invitation> invitations = InvitationController.getInvitations(user.getUserEmail()).data;
	private TableColumn<Invitation, String> invitationIdCol = new TableColumn("Invitation Id");
	private TableColumn<Invitation, String> eventIdCol = new TableColumn("Event Id");
	private TableColumn<Invitation, String> userIdCol = new TableColumn("User Id");
	private TableColumn<Invitation, String> invitationRoleCol = new TableColumn("Invitation Role");
	private TableColumn<Invitation, Void> actionCol = new TableColumn<>("Action");
    	
	@Override
	public void setLayouts() {
		invitationIdCol.setCellValueFactory(
	                new PropertyValueFactory<Invitation, String>("invitationId"));
		eventIdCol.setCellValueFactory(
                new PropertyValueFactory<Invitation, String>("eventId"));
		userIdCol.setCellValueFactory(
                new PropertyValueFactory<Invitation, String>("userId"));
		invitationRoleCol.setCellValueFactory(
                new PropertyValueFactory<Invitation, String>("invitationRole"));
		
		 actionCol.setCellFactory(col -> new TableCell<>() {
		        private final Button actionButton = new Button("Accept");
	
		        {
		        	actionButton.setStyle("-fx-font-size: 10px; -fx-padding: 4;");
		            actionButton.setOnAction(event -> {
		                Invitation invitation = getTableView().getItems().get(getIndex());
		                InvitationController.acceptInvitation(invitation.getUserId(), invitation.getEventId());
		                invitations = InvitationController.getInvitations(user.getUserEmail()).data;
		            });
		        }
	
		        @Override
		        protected void updateItem(Void item, boolean empty) {
		            super.updateItem(item, empty);
		            if (empty) {
		                setGraphic(null);
		            } else {
		                setGraphic(actionButton);
		                setStyle("-fx-alignment: CENTER;");
		            }
		        }
		    });
		
		ObservableList<Invitation> observableInvitations = FXCollections.observableArrayList(invitations);
	    table.setItems(observableInvitations);
		table.getColumns().addAll(invitationIdCol,eventIdCol,userIdCol,invitationRoleCol,actionCol);
		
        this.getChildren().addAll(titleText, table);
	}

	@Override
	public void setStyles() {
		this.setSpacing(5);
        this.setPadding(new Insets(10, 10, 0, 10));
        
		table.widthProperty().addListener((obs, oldWidth, newWidth) -> {
	        double tableWidth = newWidth.doubleValue();
	        invitationIdCol.setPrefWidth(tableWidth / 5);
	        eventIdCol.setPrefWidth(tableWidth / 5);
	        userIdCol.setPrefWidth(tableWidth / 5);
	        invitationRoleCol.setPrefWidth(tableWidth / 5);
	        actionCol.setPrefWidth(tableWidth/5);
	    });
	}

	@Override
	public void setEvents() {
		// TODO Auto-generated method stub
		
	}
	
	public ViewInvitationPage() {
		initializePage();
	}

}
