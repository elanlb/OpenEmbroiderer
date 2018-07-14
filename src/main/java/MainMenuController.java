package main.java;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class MainMenuController {

	@FXML
	protected void loadPosterizer (ActionEvent event) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("colorMatcher.fxml"));

		Main.primaryStage.setScene(new Scene(root));
	}

	@FXML
	protected void quit (ActionEvent event) {
		Platform.exit();
	}
}
