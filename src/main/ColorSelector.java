package main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ColorSelector {
	public Stage stage;

	public void open () {
	}

	@FXML
	private TextField filterField;

	@FXML
	private TableView<Color> colorTableView;

	@FXML
	private TableColumn<CheckBox, Boolean> selectorColumn;

	@FXML
	private TableColumn<String, String> nameColumn;

	@FXML
	private TableColumn<Color, Color> colorPreviewColumn;
}
