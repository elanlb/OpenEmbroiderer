package main;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Preferences {
	public static int windowWidth = 600;
	public static int windowHeight = 400;

	public static int imagePreviewSize = 500;

	public ArrayList<Color> threadColors = new ArrayList<>();


	public Scene loadScene () {
		Button menuButton = new Button("Main Menu");
		HBox menuButtonSection = new HBox(menuButton);

		Button start = new Button("Edit");
		VBox vBox = new VBox(menuButtonSection, start);

		Scene scene = new Scene(vBox, Preferences.windowWidth, Preferences.windowHeight);

		menuButton.setOnAction(event -> Main.loadMainMenu());

		return scene;
	}
}
