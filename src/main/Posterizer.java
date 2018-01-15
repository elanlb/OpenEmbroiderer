package main;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Posterizer {
	private Image image;

	public Scene loadScene () {
		Button menuButton = new Button("Main Menu");
		HBox menuButtonSection = new HBox(menuButton);

		Button selectImageButton = new Button("Select image");
		HBox imageSelect = new HBox(selectImageButton); // HBox to store the select button
		HBox imagePreview = new HBox(); // HBox to display the image preview

		Button posterize = new Button("Posterize");
		HBox posterizeSettings = new HBox(posterize); // settings for posterizing

		HBox posterizedImage = new HBox(); // HBox to display the result of posterizing

		VBox vBox = new VBox(menuButtonSection, imageSelect, imagePreview, posterizeSettings, posterizedImage);
		Scene scene = new Scene(vBox, Preferences.windowWidth, Preferences.windowHeight);

		/* return to main menu if button is pressed */
		menuButton.setOnAction(event -> Main.loadMainMenu());

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

			Color[] availableColors = {Color.WHITE, Color.BLACK, Color.GRAY, Color.LIGHTGRAY, Color.DARKGRAY}; // read available colors from preferences and let the user choose which ones they want
			posterizer.setColors(availableColors);
			Image result = posterizer.posterize();
			ImageView imageView = new ImageView(result);
			imageView.setFitWidth(Preferences.imagePreviewSize);
			imageView.setFitHeight(Preferences.imagePreviewSize);
			imageView.setPreserveRatio(true);
			posterizedImage.getChildren().setAll(imageView);
		});

		return scene;
	}


	private Color[] availableColors;

	private void setColors (Color[] userColors) {
		availableColors = userColors;
	}

	private Image startImage;

	private void setStartImage (Image image) {
		startImage = image;
	}

	private Image posterize () {
		PixelReader pixelReader = startImage.getPixelReader(); // make a pixelReader

		int width = (int) startImage.getWidth(); // get the width and height of the picture
		int height = (int) startImage.getHeight();

		WritableImage writableImage = new WritableImage(width, height);
		PixelWriter pixelWriter = writableImage.getPixelWriter();

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				/* find the distance to each available color and then set to the closest */
				Color currentColor = pixelReader.getColor(x, y);
				double red = currentColor.getRed();
				double green = currentColor.getGreen();
				double blue = currentColor.getBlue();

				double[] distances = new double[availableColors.length]; // distances to each color based on the index

				for (int i = 0; i < availableColors.length; i++) {
					double distance = Math.sqrt(
							Math.pow(availableColors[i].getRed() - red, 2) +
									Math.pow(availableColors[i].getGreen() - green, 2) +
									Math.pow(availableColors[i].getBlue() - blue, 2)
					); // pythagorean theorem in 3d

					distances[i] = distance; // set the distance of each color in the same order the colors came in
				}

				/* get the index of the smallest distance and then get the color from the availableColors array */
				int index = ArrayFunctions.min(distances);

				pixelWriter.setColor(x, y, availableColors[index]);
			}
		}

		return writableImage;
	}
}
