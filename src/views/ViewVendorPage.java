package views;

import controllers.SceneController;
import controllers.VendorController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import models.VendorProduct;

public class ViewVendorPage extends VBox implements Page{
	private VendorProduct vendor = VendorController.getProduct().data;
	private Text titleText = new Text("View Vendor");
	private Label nameText = new Label("Product Name");
	private Text productNameText = new Text(vendor.getName());
	private Label descText = new Label("Description");
	private Text productDescText = new Text(vendor.getDescription());
	private Button backButton = new Button("Back");

	@Override
	public void setLayouts() {
		
		VBox nameContainer = createDetailBox(nameText, productNameText);
        VBox descContainer = createDetailBox(descText, productDescText);
		
		this.getChildren().addAll(
				titleText, 
				nameContainer,
				descContainer, 
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

        titleText.setFont(Font.font("System", FontWeight.BOLD, 28));
        
        nameText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        descText.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        productNameText.setFont(Font.font("Arial", 14));
        productDescText.setFont(Font.font("Arial", 14));
	}

	@Override
	public void setEvents() {
		backButton.setOnMouseClicked(e -> {
			back();
		});
	}

	private void back() {
		SceneController.moveScene("home");		
	}

	public ViewVendorPage() {
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
