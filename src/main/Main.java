package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

	private static Stage primaryStage;

	@Override
	public void start (Stage stage) {
		stage.setTitle("OpenEmbroiderer");
		stage.show();

		primaryStage = stage;
		loadMainMenu();
	}

	public static void loadMainMenu () {
		Stage stage = Main.primaryStage;

		Button openPosterizer = new Button("Posterizer");
		Button openDigitizer = new Button("Digitizer");
		Button openPreferences = new Button("Preferences");
		VBox menuButtons = new VBox(openPosterizer, openDigitizer, openPreferences);

		Scene scene = new Scene(menuButtons, Preferences.windowWidth, Preferences.windowHeight);
		stage.setScene(scene);

		openPosterizer.setOnAction(event -> {
			Posterizer posterizer = new Posterizer();
			stage.setScene(posterizer.loadScene());
		});

		openDigitizer.setOnAction(event -> {
			Digitizer digitizer = new Digitizer();
			stage.setScene(digitizer.loadScene());
		});

		openPreferences.setOnAction(event -> {
			Preferences preferences = new Preferences();
			stage.setScene(preferences.loadScene());
		});
	}

	public static void main (String[] args) {
		launch(args);
	}
}
