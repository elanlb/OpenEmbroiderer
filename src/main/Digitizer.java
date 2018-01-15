package main;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import javafx.scene.control.Button;

public class Digitizer {

	public Scene loadScene () {
		Button menuButton = new Button("Main Menu");
		HBox menuButtonSection = new HBox(menuButton);

		Button start = new Button("Start");
		VBox vBox = new VBox(menuButtonSection, start);

		Scene scene = new Scene(vBox, Preferences.windowWidth, Preferences.windowHeight);

		menuButton.setOnAction(event -> Main.loadMainMenu());

		return scene;
	}
}
