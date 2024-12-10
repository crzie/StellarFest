package views;

import controllers.EventController;
import controllers.SceneController;
import controllers.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import models.Event;
import models.User;

public class ViewAcceptedEventDetailPage extends VBox implements Page {
    private Event event;

    private Label nameLabel = new Label("Event Name:");
    private Label dateLabel = new Label("Event Date:");
    private Label locLabel = new Label("Event Location:");
    private Label descLabel = new Label("Event Description:");
    private Label organizerLabel = new Label("Event Organizer:");

    private Text nameValue = new Text();
    private Text dateValue = new Text();
    private Text locValue = new Text();
    private Text descValue = new Text();
    private Text organizerValue = new Text();

    private Button backButton = new Button("Back");

    @Override
    public void setLayouts() {
        if (event != null) {
            nameValue.setText(event.getEventName());
            dateValue.setText(event.getEventDate());
            locValue.setText(event.getEventLocation());
            descValue.setText(event.getEventDescription());

            User user = UserController.getUserByUserId(event.getOrganizerId()).data;
            organizerValue.setText(user.getUsername());
        }

        VBox nameBox = createDetailBox(nameLabel, nameValue);
        VBox dateBox = createDetailBox(dateLabel, dateValue);
        VBox locBox = createDetailBox(locLabel, locValue);
        VBox descBox = createDetailBox(descLabel, descValue);
        VBox organizerBox = createDetailBox(organizerLabel, organizerValue);

        backButton.setAlignment(Pos.CENTER);

        this.getChildren().addAll(
            nameBox,
            dateBox,
            locBox,
            descBox,
            organizerBox,
            backButton
        );
    }

    @Override
    public void setStyles() {
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.setPadding(new Insets(20));

        backButton.setStyle(
            "-fx-font-size: 16px; -fx-padding: 10px 20px; -fx-background-radius: 5;"
        );
        backButton.setMaxWidth(150);

        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        dateLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        locLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        descLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        organizerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        nameValue.setFont(Font.font("Arial", 14));
        dateValue.setFont(Font.font("Arial", 14));
        locValue.setFont(Font.font("Arial", 14));
        descValue.setFont(Font.font("Arial", 14));
        organizerValue.setFont(Font.font("Arial", 14));
    }

    @Override
    public void setEvents() {
        // Back button event
        backButton.setOnMouseClicked(e -> SceneController.moveScene("view accepted events"));
    }

    public ViewAcceptedEventDetailPage(String eventId) {
        event = EventController.viewEventDetails(eventId).data;
        initializePage();
    }

    private VBox createDetailBox(Label label, Text value) {
        VBox box = new VBox(5);
        box.setAlignment(Pos.CENTER_LEFT);
        box.getChildren().addAll(label, value);
        box.setPadding(new Insets(5, 10, 5, 10));
        box.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #dcdcdc; -fx-border-radius: 5; -fx-background-radius: 5;");
        return box;
    }
}