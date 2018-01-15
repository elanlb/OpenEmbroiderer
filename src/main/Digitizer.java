package main;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import javafx.scene.control.Button;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Digitizer {
	private Scene scene;
	private Image image;

	public void loadScene (Stage stage) {

		Button selectImageButton = new Button("Select image");
		HBox imageSelect = new HBox(selectImageButton); // HBox to store the select button
		HBox imagePreview = new HBox(); // HBox to display the image preview

		Button posterize = new Button("Posterize");
		HBox posterizeSettings = new HBox(posterize); // settings for posterizing

		HBox posterizedImage = new HBox(); // HBox to display the result of posterizing

		VBox vBox = new VBox(imageSelect, imagePreview, posterizeSettings, posterizedImage);
		scene = new Scene(vBox, 600, 400);

		/* choose file when button is pressed */
		selectImageButton.setOnAction(event -> {
			FileChooser imageChooser = new FileChooser();
			imageChooser.setTitle("Select image for digitizing");
			imageChooser.getExtensionFilters().addAll(
					new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.bmp")
			);
			File selectedFile = imageChooser.showOpenDialog(new Stage());

			if (selectedFile != null) {
				image = new Image(selectedFile.toURI().toString());
				ImageView imageView = new ImageView(image);
				imageView.setFitHeight(Preferences.imagePreviewSize);
				imageView.setFitWidth(Preferences.imagePreviewSize);
				imageView.setPreserveRatio(true);
				imagePreview.getChildren().setAll(imageView); // add the image to the image box
			}
			/* show an error box if they don't choose the right file type or choose an empty file */
			else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("File error");
				alert.setHeaderText("Invalid file");
				alert.setContentText("The file you selected was not valid or you didn't select any file.");
				alert.showAndWait();
			}
		});

		posterize.setOnAction(event -> {
			Posterizer posterizer = new Posterizer();
			posterizer.setStartImage(image);

			Color[] availableColors = {Color.WHITE, Color.BLACK, Color.GREEN};
			posterizer.setColors(availableColors);
			Image result = posterizer.posterize();
			ImageView imageView = new ImageView(result);
			imageView.setFitWidth(Preferences.imagePreviewSize);
			imageView.setFitHeight(Preferences.imagePreviewSize);
			imageView.setPreserveRatio(true);
			posterizedImage.getChildren().setAll(imageView);
		});

		stage.setScene(scene);
	}
}
