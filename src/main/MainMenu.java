package main;

import javafx.stage.Stage;

public class MainMenu extends Main {
	public static void loadMainMenu (Stage stage) {
		Digitizer digitizer = new Digitizer();
		digitizer.loadScene(stage);
	}
}
