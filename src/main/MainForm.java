package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * PROJECT: seniorDesign
 * AUTHOR: Broderick
 * DATE: 12/30/2016
 *
 * DESCRIPTION:
 *
 *
 */
public class MainForm extends Application {
	
	private TableView table = new TableView();
	public static String[] arguments;
	
	@Override
	public void start(Stage primaryStage) {
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
	
	public static void main(String[] args) {
		arguments = args;
		launch(args);
	}
}
