package ui.controllers;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Data;
import utilities.MasterData;
import weather.WeatherCaching;
import weather.WeatherCurrent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * PROJECT: seniordesign
 * AUTHOR: aaron  2/22/2017.
 * DATE: 2/22/2017
 *
 * DESCRIPTION:
 *
 *
 * INPUTS:
 *
 *
 * OUTPUTS:
 */
public class MockupController {
	
	private StringProperty clouds;
	private StringProperty shade;
	private StringProperty speedLimit;
	
	ObservableList<MasterData> data;
	private WeatherCurrent[] currentWeather;
	
	
	//data sets for charts
	private XYChart.Series energyGraphData;
	private XYChart.Series cloudData;
	
	
	//region FXML declarations
	
	@FXML
	private Label currentSpeedLimit;
	
	@FXML
	private StackPane tablePane;
	
	@FXML
	private NumberAxis energyYAxis;
	
	@FXML
	private MenuItem carConfig;
	
	@FXML
	private TextField cloudPctOverride;
	
	@FXML
	private ComboBox chartSelector;
	
	@FXML
	private TextField shadePctOverride;
	
	@FXML
	private NumberAxis cloudYAxis;
	
	@FXML
	private TextField speedLimitOverride;
	
	@FXML
	private LineChart cloudChart;
	
	@FXML
	private CategoryAxis energyXAxis;
	
	@FXML
	private Label currentCloudPct;
	
	@FXML
	private CategoryAxis cloudXAxis;
	
	@FXML
	private Label currentShadePct;
	
	@FXML
	private LineChart energyChart;
	
	@FXML
	private TableView table;
	
	@FXML
	void editCarConfig(ActionEvent event) {
		
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/ui/fxml/configEditor.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Car Configuration");
			stage.setScene(new Scene(root, 450, 450));
			stage.show();
			// Hide this current window (if this is what you want)
			//((Node)(event.getSource())).getScene().getWindow().hide();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	void overrideClouds(ActionEvent event) {
		
	}
	
	//endregion
	
	
	@FXML
		// This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		
		//region assertions
		
		assert currentSpeedLimit != null : "fx:id=\"currentSpeedLimit\" was not injected: check your FXML file 'mockup.fxml'.";
		assert tablePane != null : "fx:id=\"tablePane\" was not injected: check your FXML file 'mockup.fxml'.";
		assert energyYAxis != null : "fx:id=\"energyYAxis\" was not injected: check your FXML file 'mockup.fxml'.";
		assert cloudPctOverride != null : "fx:id=\"cloudPctOverride\" was not injected: check your FXML file 'mockup.fxml'.";
		assert chartSelector != null : "fx:id=\"chartSelector\" was not injected: check your FXML file 'mockup.fxml'.";
		assert shadePctOverride != null : "fx:id=\"shadePctOverride\" was not injected: check your FXML file 'mockup.fxml'.";
		assert cloudYAxis != null : "fx:id=\"cloudYAxis\" was not injected: check your FXML file 'mockup.fxml'.";
		assert speedLimitOverride != null : "fx:id=\"speedLimitOverride\" was not injected: check your FXML file 'mockup.fxml'.";
		assert cloudChart != null : "fx:id=\"cloudChart\" was not injected: check your FXML file 'mockup.fxml'.";
		assert energyXAxis != null : "fx:id=\"energyXAxis\" was not injected: check your FXML file 'mockup.fxml'.";
		assert currentCloudPct != null : "fx:id=\"currentCloudPct\" was not injected: check your FXML file 'mockup.fxml'.";
		assert cloudXAxis != null : "fx:id=\"cloudXAxis\" was not injected: check your FXML file 'mockup.fxml'.";
		assert currentShadePct != null : "fx:id=\"currentShadePct\" was not injected: check your FXML file 'mockup.fxml'.";
		assert energyChart != null : "fx:id=\"energyChart\" was not injected: check your FXML file 'mockup.fxml'.";
		assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'mockup.fxml'.";
		
		//endregion
		
		
		data = Data.getData();
		currentWeather = WeatherCaching.loadCurrent("weather-10_locations");
		
		energyGraphData = new XYChart.Series();
		cloudData = new XYChart.Series();
		
		
		//region Override TextView handlers
		cloudPctOverride.setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				
				System.out.println("override cloud percentage");
				
				if (validatePercentage(cloudPctOverride.textProperty())) {
					clouds = cloudPctOverride.textProperty();
					currentCloudPct.textProperty().setValue(clouds.getValue() + "%");
					
					//TODO Aaron: hook cloud pct value into model data
					
				}
				
				cloudPctOverride.clear();
				
				
			} else {
				
			}
		});
		
		shadePctOverride.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				
				System.out.println("override shade percentage");
				
				if (validatePercentage(shadePctOverride.textProperty())) {
					shade = shadePctOverride.textProperty();
					currentShadePct.textProperty().setValue(shade.getValue() + "%");
					
					//TODO Aaron: hook shade pct value into model data
					
					
				} else {
					System.out.println("Not a valid number");
				}
				
				
				shadePctOverride.clear();
			} else {
				
			}
			
			
		});
		
		speedLimitOverride.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				
				System.out.println("override speed limit");
				
				if (validatePercentage(speedLimitOverride.textProperty())) {
					speedLimit = speedLimitOverride.textProperty();
					currentSpeedLimit.textProperty().setValue(speedLimit.getValue());
					
					//TODO Aaron: hook speed limit value into simulation
					
				} else {
					System.out.println("Not a valid number");
				}
				
				speedLimitOverride.clear();
				
				
			} else {
				
			}
			
			
		});
		
		//endregion
		
		
		//region chart selector choicebox handler
		ObservableList<String> list = FXCollections.observableArrayList("1", "2", "3", "4");
		
		chartSelector.getItems().clear();
		chartSelector.getItems().addAll("Energy", "Cloud Cover");
		
		chartSelector.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				System.out.println(newValue.toString());
				
				//TODO Aaron: change which chart is displayed depending on combobox value
				String choice = newValue.toString();
				switch (choice) {
					case "Energy":
						
						showEnergyGraph();
						
						
						break;
					case "Cloud Cover":
						
						showCloudCoverGraph();
						
						break;
				}
				
				
			}
		});
		//endregion
		
		showDataTable();
	}
	
	
	private void showEnergyGraph() {
		
		assert (data != null);
		
		
		energyXAxis.setLabel("Time");
		energyYAxis.setLabel("Battery (%)");
		
		//add the data if we don't have it already
		if (energyGraphData.getData().isEmpty()) {
			
			for (int i = 0; i < data.size(); i++) {
				energyGraphData.getData().add(new XYChart.Data(data.get(i).getStartTime().getValue(), Float.valueOf(data.get(i).getTotalCharge().getValue())));
			}
			
			energyChart.getData().add(energyGraphData);
			
		}
		
		
		energyChart.setVisible(!energyChart.isVisible());
		cloudChart.setVisible(false);
	}
	
	private void showCloudCoverGraph() {
		
		
		cloudXAxis.setLabel("Location");
		cloudYAxis.setLabel("Current Cloud Cover (%)");
		
		cloudYAxis.setLowerBound(0);
		cloudYAxis.setUpperBound(100);
		
		
		if (cloudData.getData().isEmpty()) {
			//forecasted, current, by location... etc?
			for (int i = 0; i < currentWeather.length; i++) {
				cloudData.getData().add(new XYChart.Data(String.valueOf(i), currentWeather[i].getCloudsPercentage()));
			}
			
			
			cloudChart.getData().addAll(cloudData);
		}
		
		
		cloudData.getNode().setStyle("-fx-stroke: #0052cc");
		
		
		cloudChart.setVisible(!cloudChart.isVisible());
		energyChart.setVisible(false);
		
		
	}
	
	private void showDataTable() {
		
		table.setEditable(false);
		
		TableColumn<MasterData, String> startTimeColumn = new TableColumn("Start Time");
		startTimeColumn.setCellValueFactory(temp -> temp.getValue().getStartTime());
		
		TableColumn<MasterData, String> endTimeColumn = new TableColumn("End Time");
		endTimeColumn.setCellValueFactory(temp -> temp.getValue().getEndTime());
		
		TableColumn<MasterData, String> distanceColumn = new TableColumn("Distance (km)");
		distanceColumn.setCellValueFactory(temp -> temp.getValue().getDistance());
		
		TableColumn<MasterData, String> velocityColumn = new TableColumn("Speed (km/h)");
		velocityColumn.setCellValueFactory(temp -> temp.getValue().getVelocity());
		
		TableColumn<MasterData, String> roadAngleColumn = new TableColumn("Road Angle");
		roadAngleColumn.setCellValueFactory(temp -> temp.getValue().getRoadAngle());
		
		TableColumn<MasterData, String> elevationColumn = new TableColumn("Elevation");
		elevationColumn.setCellValueFactory(temp -> temp.getValue().getElevation());
		
		TableColumn<MasterData, String> batteryChargeColumn = new TableColumn("Bat Charge");
		batteryChargeColumn.setCellValueFactory(temp -> temp.getValue().getBatteryCharge());
		
		TableColumn<MasterData, String> totalChargeColumn = new TableColumn("Total Charge Used");
		totalChargeColumn.setCellValueFactory(temp -> temp.getValue().getTotalCharge());
		
		table.setItems(data);
		table.getColumns().addAll(startTimeColumn, endTimeColumn, velocityColumn, distanceColumn, elevationColumn, roadAngleColumn, batteryChargeColumn, totalChargeColumn);
		
		//Screen screen = Screen.getPrimary();
		//Rectangle2D bounds = screen.getVisualBounds();
		
	}
	
	
	/**
	 * @param text
	 * @return true if text is integer, false if not
	 */
	private boolean validatePercentage(StringProperty text) {
		
		
		String s = text.getValue().trim();
		System.out.println("-> " + s + " <-");
		return (s.matches("[0-9]*") && Integer.valueOf(s) >= 0 && Integer.valueOf(s) <= 100);
		
		
	}
	
	private boolean validateSpeedLimit(StringProperty text) {
		
		
		String s = text.getValue().trim();
		System.out.println("-> " + s + " <-");
		return (s.matches("[0-9]*") && Integer.valueOf(s) >= 0);
		
		
	}
	
	//region getters and setters for overrides
	
	public String getClouds() {
		return clouds.get();
	}
	
	public StringProperty cloudsProperty() {
		return clouds;
	}
	
	public void setClouds(String clouds) {
		this.clouds.set(clouds);
	}
	
	public String getShade() {
		return shade.get();
	}
	
	public StringProperty shadeProperty() {
		return shade;
	}
	
	public void setShade(String shade) {
		this.shade.set(shade);
	}
	
	public String getSpeedLimit() {
		return speedLimit.get();
	}
	
	public StringProperty speedLimitProperty() {
		return speedLimit;
	}
	
	public void setSpeedLimit(String speedLimit) {
		this.speedLimit.set(speedLimit);
	}
	
	//endregion
}
