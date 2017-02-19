package main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ui.DataTable;
import ui.WeatherTable;

/**
 * PROJECT: seniordesign
 * AUTHOR: aaron  1/30/2017.
 * DATE: 1/30/2017
 *
 * DESCRIPTION:
 *
 * A
 * INPUTS:
 *
 *
 * OUTPUTS:
 */


public class Controller {
	
	
	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;
	
	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;
	
	@FXML // fx:id="energyGraphButton"
	private Button energyGraphButton; // Value injected by FXMLLoader
	
	@FXML // fx:id="viewmenu"
	private Menu viewmenu; // Value injected by FXMLLoader
	
	@FXML // fx:id="currentConditionsButton"
	private Button currentConditionsButton; // Value injected by FXMLLoader
	
	@FXML // fx:id="elevationGraphButton"
	private Button elevationGraphButton; // Value injected by FXMLLoader
	
	@FXML // fx:id="temperatureForecastButton"
	private Button temperatureForecastButton; // Value injected by FXMLLoader
	
	@FXML // fx:id="dataTableButton"
	private Button dataTableButton; // Value injected by FXMLLoader
	
	@FXML // fx:id="nav_listview"
	private ListView<?> nav_listview; // Value injected by FXMLLoader
	
	@FXML
	void exit(ActionEvent event) {
		System.exit(0);
	}
	
	@FXML
	void showTemperatureChart(ActionEvent event) {
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/fxml/temperaturechart.fxml"));
		StackPane newWindow = null;
		
		try {
			newWindow = fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//WeatherTableController controller = fxmlLoader.getController();
		
		
		Stage stage = new Stage();
		stage.setTitle("Forecast");
		stage.initOwner(temperatureForecastButton.getScene().getWindow());
		Scene scene = new Scene(newWindow);
		stage.setScene(scene);
		stage.show();
		
	}
	
	@FXML
	void showDataTable(ActionEvent event) {
		Stage stage = new Stage();
		DataTable dt = new DataTable();
		stage = dt.fillDataTable();
		stage.show();
	}
	
	@FXML
	void showCurrentWeather(ActionEvent event) {
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/fxml/weathertable.fxml"));
		VBox newWindow = null;
		
		try {
			newWindow = fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//WeatherTableController controller = fxmlLoader.getController();
		
		
		Stage stage = new Stage();
		stage.setTitle("Current Conditions");
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(temperatureForecastButton.getScene().getWindow());
		Scene scene = new Scene(newWindow);
		stage.setScene(scene);
		stage.show();
		
	}
	
	
	@FXML
	void showElevationChart(ActionEvent event) {
		
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/fxml/elevationchart.fxml"));
		StackPane newWindow = null;
		
		try {
			newWindow = fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		Stage stage = new Stage();
		stage.setTitle("Elevation");
		stage.initOwner(elevationGraphButton.getScene().getWindow());
		Scene scene = new Scene(newWindow);
		stage.setScene(scene);
		stage.show();
		
	}
	
	@FXML
	void showEnergyGraph(ActionEvent event) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/fxml/energygraph.fxml"));
		StackPane newWindow = null;
		
		try {
			newWindow = fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Stage stage = new Stage();
		stage.setTitle("Energy");
		stage.initOwner(energyGraphButton.getScene().getWindow());
		Scene scene = new Scene(newWindow);
		stage.setScene(scene);
		stage.show();
		
	}
	
	@FXML
		// This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert energyGraphButton != null : "fx:id=\"energyGraphButton\" was not injected: check your FXML file 'main.fxml'.";
		assert viewmenu != null : "fx:id=\"viewmenu\" was not injected: check your FXML file 'main.fxml'.";
		assert currentConditionsButton != null : "fx:id=\"currentConditionsButton\" was not injected: check your FXML file 'main.fxml'.";
		assert elevationGraphButton != null : "fx:id=\"elevationGraphButton\" was not injected: check your FXML file 'main.fxml'.";
		assert temperatureForecastButton != null : "fx:id=\"temperatureForecastButton\" was not injected: check your FXML file 'main.fxml'.";
		assert dataTableButton != null : "fx:id=\"dataTableButton\" was not injected: check your FXML file 'main.fxml'.";
		assert nav_listview != null : "fx:id=\"nav_listview\" was not injected: check your FXML file 'main.fxml'.";


//        if (MainForm.arguments.length > 0 && MainForm.arguments[0].equals("brodie")) {
//            dataTableButton.fire();
//        }
	}
	
	
}

