package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import ui.controllers.MainController;
import utilities.GPS;
import utilities.Position;
import utilities.Telemetry;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ui.controllers.MainController.positions;

/**
 * PROJECT: seniorDesign
 * AUTHOR: Broderick
 * DATE: 12/30/2016
 * <p>
 * DESCRIPTION:
 */
public class MainForm extends Application {
	
	public static String raceChoice = "";
	public static String legChoice = "";
	
	@Override
	public void start(Stage primaryStage) {
		
		List<String> choices = new ArrayList<>();
		File legFolder = new File("legs");
		File[] folders = legFolder.listFiles();
		for (File folder : folders) {
			if (folder.isDirectory()) {
				choices.add(folder.getName());
			}
		}
		java.util.Collections.sort(choices);
		ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
		dialog.setTitle("Choose Race");
		dialog.setHeaderText("Choose a Race");
//		dialog.setContentText("Choose race:");
		Optional<String> result = dialog.showAndWait();
		raceChoice = parseResult(result);
		if (raceChoice == null) {
			return;
		}
		
		choices = new ArrayList<>();
		File stageFolder = new File("legs/" + raceChoice + "/");
		File[] stages = stageFolder.listFiles();
		for (File stage : stages) {
//			System.out.println(stage.getName());
			if (stage.getName().endsWith(".csv")) {
				String[] nameTokens = stage.getName().split("\\.(?=[^\\.]+$)");
				choices.add(nameTokens[0]);
			}
		}
		java.util.Collections.sort(choices);
		dialog = new ChoiceDialog<>(choices.get(0), choices);
		dialog.setTitle("Choose Race Leg");
		dialog.setHeaderText("Choose a Leg");
//		dialog.setContentText("Choose race:");
		result = dialog.showAndWait();
		legChoice = parseResult(result);
		if (legChoice == null) {
			return;
		}
		
		
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/ui/fxml/mainPage.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		primaryStage.setTitle("Solar Car Performance Application");
		primaryStage.setScene(new Scene(root, 1024, 768));
		primaryStage.show();
	}
	
	@Override
	public void stop() {
		System.out.println("Exiting Form");
		if (positions != null) {
			Position.savePositions(positions, MainController.positionsFile);
		}
		GPS.killTask();
		Telemetry.killTask();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private static String parseResult(Optional<String> result) {
		if (result.isPresent()) {
			System.out.println("Your choice: " + result.get());
			return result.get();
		} else {
			Platform.setImplicitExit(true);
			Platform.exit();
			return null;
		}
	}
}
