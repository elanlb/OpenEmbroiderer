package main;

import colorMatcher.ColorMatcher;
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
import java.util.ArrayList;

public class ColorMatcherController {
	private Image image;
	private Image imageResult;

	@FXML
	protected void mainMenu (ActionEvent event) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));

		Main.primaryStage.setScene(new Scene(root));
	}

	@FXML
	protected void selectColors (ActionEvent event) throws Exception {
		ColorSelector.open();
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

			if (ColorSelector.colors == null) { // make sure that colors are selected
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("No Colors");
				alert.setHeaderText("No colors are selected");
				alert.setContentText("You must select colors to use. Click 'Select Colors' to access the color selector.");
				alert.showAndWait();
			} else {
				ColorSelector.updateColorList();
				ArrayList<Color> availableColors = ColorSelector.colors; // read available colors from preferences and let the user choose which ones they want

				imageResult = ColorMatcher.matchColors(image, availableColors);

				imageResultView.setImage(imageResult);
				imageResultView.setFitHeight(Preferences.imagePreviewSize);
				imageResultView.setPreserveRatio(true);
			}
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
