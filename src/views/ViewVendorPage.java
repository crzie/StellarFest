package views;

import controllers.SceneController;
import controllers.VendorController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import models.VendorProduct;

public class ViewVendorPage extends VBox implements Page{
	private VendorProduct vendor = VendorController.getProduct().data;
	private Text titleText = new Text("View Vendor");
	private Text nameText = new Text("Product Name");
	private Text productNameText = new Text(vendor.getName());
	private Text descText = new Text("Description");
	private Text productDescText = new Text(vendor.getDescription());
	private Button backButton = new Button("Back");
	
	private VBox nameContainer = new VBox();
	private VBox descContainer = new VBox();

	@Override
	public void setLayouts() {
		nameContainer.getChildren().addAll(nameText, productNameText);
		descContainer.getChildren().addAll(descText, productDescText);
		
		this.getChildren().addAll(
				titleText, 
				nameContainer,
				descContainer, 
				backButton
			);
	}

	@Override
	public void setStyles() {
		titleText.setFont(Font.font("System", FontWeight.BOLD, 28));
		nameText.setFont(Font.font("System", FontWeight.NORMAL, 18));
		productNameText.setFont(Font.font("System", FontWeight.NORMAL, 14));
		descText.setFont(Font.font("System", FontWeight.NORMAL, 18));
		productDescText.setFont(Font.font("System", FontWeight.NORMAL, 14));
		
		nameContainer.setAlignment(Pos.CENTER);
		descContainer.setAlignment(Pos.CENTER);
		nameContainer.setMaxWidth(300);
		descContainer.setMaxWidth(300);
		
		VBox.setMargin(nameContainer, new Insets(10, 20, 0, 20));
		VBox.setMargin(descContainer, new Insets(10, 20, 0, 20));
		VBox.setMargin(backButton, new Insets(10, 20, 0, 20));
		backButton.setPadding(new Insets(6, 20, 6, 20));
		
		this.setAlignment(Pos.CENTER);
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
}
