package main.java;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class ColorSelector {
	private static Stage stage;

	public static void open () throws Exception {
		if (stage == null) {
			stage = new Stage();
		}

		Parent root = FXMLLoader.load(ColorSelector.class.getResource("colorSelector.fxml")); // can't use getClass
		stage.setScene(new Scene(root));
		stage.show();
	}

	@FXML
	protected void closeWindow () {
		stage.hide();
	}

	public static ArrayList<Color> colors;
	private static ObservableList<ColorPicker> colorPickers; // stores the color pickers

	@FXML
	private ListView<ColorPicker> colorPickerListView; // displays each color picker

	public void initialize () {
		if (colorPickers == null) {
			colorPickers = FXCollections.observableArrayList();
		}

		colorPickerListView.setItems(colorPickers);

		if (colors == null) {
			colors = new ArrayList<>();
		}
	}

	public static void updateColorList () {
		colors = new ArrayList<>();
		IntStream.range(0, colorPickers.size()).forEach(i -> colors.add(colorPickers.get(i).getValue()));
		if (!colors.contains(Color.TRANSPARENT)) {
			colors.add(Color.TRANSPARENT);
		}
	}

	@FXML
	protected void addItem () {
		colorPickers.add(new ColorPicker());
	}

	@FXML
	protected void removeItem () {
		colorPickers.remove(colorPickerListView.getSelectionModel().getSelectedItem());
	}
}
