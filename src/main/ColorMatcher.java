package main;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class ColorMatcher {
	private Image image;
	private Image imageResult;

	@FXML
	protected void mainMenu (ActionEvent event) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));

		Main.primaryStage.setScene(new Scene(root));
	}

	@FXML
	protected void selectColors (ActionEvent event) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("colorSelector.fxml"));
	}

	/* choose file when button is pressed and display it in the preview box */
	@FXML
	ImageView imagePreview;

	@FXML
	protected void selectFile (ActionEvent event) {
		FileChooser imageChooser = new FileChooser();
		imageChooser.setTitle("Select image for color matching");
		imageChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.bmp")
		);
		File selectedFile = imageChooser.showOpenDialog(Main.primaryStage);

		if (selectedFile != null) {
			image = new Image(selectedFile.toURI().toString());
			imagePreview.setImage(image); // add the image to the image box
			imagePreview.setFitHeight(Preferences.imagePreviewSize);
			imagePreview.setPreserveRatio(true);
		}
	}

	/* when button is pressed, match colors and display the result in the image view */
	@FXML
	ImageView imageResultView;

	@FXML
	protected void matchColors (ActionEvent event) {
		if (image != null) {
			Image startImage = image;

			Color[] availableColors = {
					Color.rgb(0, 190,144),
					Color.rgb(223, 69, 81),
					Color.rgb(0xFF, 0xFF, 0xFF),
					Color.rgb(215, 180, 184),
					Color.rgb(138, 125, 100),
					Color.rgb(0, 0, 0)
			}; // read available colors from preferences and let the user choose which ones they want

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

			imageResult = writableImage;

			imageResultView.setImage(writableImage);
			imageResultView.setFitHeight(Preferences.imagePreviewSize);
			imageResultView.setPreserveRatio(true);
		}
		else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("File error");
			alert.setHeaderText("Empty file");
			alert.setContentText("You didn't select a file for color matching.");
			alert.showAndWait();
		}
	}

	@FXML
	protected void saveFile (ActionEvent event) {
		if (imageResult != null) {
			FileChooser imageSaver = new FileChooser();
			imageSaver.setTitle("Save image");
			imageSaver.getExtensionFilters().addAll(
					new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.bmp")
			);
			File saveLocation = imageSaver.showSaveDialog(Main.primaryStage);
			if (saveLocation != null) {
				try {
					ImageIO.write(SwingFXUtils.fromFXImage(imageResult, null), "png", saveLocation);
				} catch (IOException exception) {
					System.out.println(exception.getMessage());
				}
			}
		}
		else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("File error");
			alert.setHeaderText("Empty file");
			alert.setContentText("You can't save an image that doesn't exist.");
			alert.showAndWait();
		}
	}
}
