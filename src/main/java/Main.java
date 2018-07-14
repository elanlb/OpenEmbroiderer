package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	public static Stage primaryStage;

	@Override
	public void start (Stage stage) throws Exception {
		primaryStage = stage;

		Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
		Scene scene = new Scene(root, Preferences.windowWidth, Preferences.windowHeight);

		stage.setWidth(Preferences.windowWidth);
		stage.setHeight(Preferences.windowHeight);

		stage.setTitle("Color Matcher");
		stage.setScene(scene);
		stage.show();
	}

	public static void main (String[] args) {
		launch(args);
	}
}
