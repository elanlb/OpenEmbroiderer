package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Posterizer {
	private Image image;

	@FXML
	protected void mainMenu (ActionEvent event) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));

		Main.primaryStage.setScene(new Scene(root));
	}

	/* choose file when button is pressed and display it in the preview box */
	@FXML
	ImageView imagePreview;

	@FXML
	protected void selectFile (ActionEvent event) {
		FileChooser imageChooser = new FileChooser();
		imageChooser.setTitle("Select image for digitizing");
		imageChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.bmp")
		);
		File selectedFile = imageChooser.showOpenDialog(new Stage());

		if (selectedFile != null) {
			image = new Image(selectedFile.toURI().toString());
			imagePreview.setImage(image); // add the image to the image box
			imagePreview.setFitHeight(Preferences.imagePreviewSize);
			imagePreview.setPreserveRatio(true);
		}
		/* show an error box if they don't choose the right file type or choose an empty file */
		else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("File error");
			alert.setHeaderText("Invalid file");
			alert.setContentText("The file you selected was not valid or you didn't select any file.");
			alert.showAndWait();
		}
	}

	/* when button is pressed, posterize and display the result in the image view */
	@FXML
	ImageView imageResult;

	@FXML
	protected void posterize (ActionEvent event) {
		Image startImage = image;

		Color[] availableColors = {Color.WHITE, Color.BLACK, Color.GREEN}; // read available colors from preferences and let the user choose which ones they want

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

		imageResult.setImage(writableImage);
		imageResult.setFitHeight(Preferences.imagePreviewSize);
		imageResult.setPreserveRatio(true);
	}
}
