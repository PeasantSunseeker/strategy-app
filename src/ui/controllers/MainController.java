package ui.controllers;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.MapStateEventType;
import com.lynden.gmapsfx.javascript.event.StateEventHandler;
import com.lynden.gmapsfx.javascript.event.UIEventHandler;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.shapes.Circle;
import com.lynden.gmapsfx.shapes.CircleOptions;
import com.lynden.gmapsfx.shapes.Polyline;
import com.lynden.gmapsfx.shapes.PolylineOptions;
import config.CarConfig;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import main.Data;
import main.MainForm;
import netscape.javascript.JSObject;
import utilities.GPS;
import utilities.MasterData;
import utilities.Position;
import utilities.Telemetry;
import weather.AveragedWeather;
import weather.WeatherCaching;
import weather.WeatherCurrent;
import weather.WeatherForecast;

import java.io.File;
import java.net.URL;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

import static java.lang.Thread.sleep;

/**
 * PROJECT: seniordesign
 * AUTHOR: aaron  2/22/2017.
 * DATE: 2/22/2017
 * <p>
 * DESCRIPTION:
 * <p>
 * <p>
 * INPUTS:
 * <p>
 * <p>
 * OUTPUTS:
 */
public class MainController implements Initializable, MapComponentInitializedListener {
	
	private final String CONFIG_FILE_PATH = "carconfig";
	
	private StringProperty clouds;
	private StringProperty shade;
	private StringProperty speedLimit;
	private StringProperty endingEnergyString;
	private int endingEnergy = 20;
	
	public static ObservableList<MasterData> displayData;
	private WeatherCurrent[] currentWeather;
	private ArrayList<WeatherForecast> forecasts;
	
	//data sets for charts
	private XYChart.Series energyGraphData;
	private XYChart.Series actualEnergyGraphData;
	private XYChart.Series cloudData;
	
	
	//region FXML declarations
	
	@FXML
	private Label currentSpeedLimit;
	
	@FXML
	private StackPane tablePane;
	
	@FXML
	private NumberAxis energyYAxis;
	
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
	private Label currentEndingEnergy;
	
	
	@FXML
	private TextField endingEnergyOverride;
	
	@FXML
	private Button runSimulation;
	
	@FXML
	private GoogleMapView mapView;
	
	private GoogleMap map;
	
	private Polyline polyline;
	
	public static Circle positionCircle;
	
	public static Position[] positions;
	
	GPS gps;
	
	@FXML // fx:id="carConfigMenu"
	private Menu carConfigMenu; // Value injected by FXMLLoader
	
	@FXML
	void overrideClouds(ActionEvent event) {
	
	}
	
	@FXML
	protected void runSimulationAction(ActionEvent event) {
		displayData = Data.getData(endingEnergy);
		fillDataTable(displayData);
		updateEnergyGraph();
	}
	
	//endregion
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		positions = Position.loadPositions("legs/" + MainForm.raceChoice + "/" + MainForm.legChoice);
		initializeForm();
		mapView.addMapInializedListener(this);
	}
	
	@Override
	public void mapInitialized() {
		MapOptions mapOptions = new MapOptions();
//		39.4999  ,-84.41028
//		41.31823, -81.58775
		mapOptions.center(new LatLong(39.4999, -84.41028))
				.mapType(MapTypeIdEnum.ROADMAP)
				.overviewMapControl(false)
				.panControl(false)
				.rotateControl(false)
				.scaleControl(false)
				.streetViewControl(false)
				.zoomControl(false)
				.zoom(6);
		
		map = mapView.createMap(mapOptions);
		
		UIEventHandler clickEvent = new UIEventHandler() {
			@Override
			public void handle(JSObject jsObject) {
//				GPS.updateLocation(new LatLong((JSObject)jsObject.getMember("latLng")));
//			    map.removeMapShape(polyline);
//			    map.setZoom(map.getZoom()+1);
//			    map.setZoom(map.getZoom()-1);
//			    System.out.println("Adding polyline");
//			    map.addMapShape(polyline);
			}
		};
		
		StateEventHandler zoomEvent = () -> {
//			System.out.printf("zoomEvent\n");
			LatLongBounds bounds = map.getBounds();
			LatLong latLong = bounds.getNorthEast();
			double distance = latLong.distanceFrom(bounds.getSouthWest());
			positionCircle.setRadius(distance * .01);
//			positionCircle.setRadius(17.7 * Math.pow(2, (21 - map.getZoom())) * 0.1);
		};
		
		map.addUIEventHandler(UIEventType.click, clickEvent);
		
		map.addStateEventHandler(MapStateEventType.zoom_changed, zoomEvent);

//		positions = Position.loadPositions("leg-1-10_items");
//		positions = Position.loadPositions("leg-1-complete");
		
		MVCArray path = new MVCArray();
		for (Position position : positions) {
			path.push(new LatLong(position.getLatitude(), position.getLongitude()));
		}
		
		PolylineOptions polylineOptions = new PolylineOptions();
		polylineOptions.strokeColor("#ff0000");
		polylineOptions.strokeOpacity(1.0);
		polylineOptions.strokeWeight(2);
		polylineOptions.path(path);
		polylineOptions.clickable(false);
		
		polyline = new Polyline(polylineOptions);
		map.addMapShape(polyline);
		
		CircleOptions circleOptions = new CircleOptions();
		circleOptions.center(new LatLong(positions[0].getLatitude(), positions[0].getLongitude()));
		circleOptions.radius(17.712 * Math.pow(2, (21 - map.getZoom() + 1)) * 0.01);
		circleOptions.strokeColor("#000000");
		circleOptions.strokeOpacity(1);
		circleOptions.strokeWeight(2);
		circleOptions.fillColor("#498dfc");
		circleOptions.fillOpacity(1);
		circleOptions.clickable(false);
		
		positionCircle = new Circle(circleOptions);
		
		map.addMapShape(positionCircle);
		
		gps = new GPS(this);
		
		gps.startTask();
		
		Telemetry.startTask();
	}
	
	// This method is called by the FXMLLoader when initialization is complete
	private void initializeForm() {
		
		//region assertions
		
		assert currentSpeedLimit != null : "fx:id=\"currentSpeedLimit\" was not injected: check your FXML file 'mainPage.fxml'.";
		assert tablePane != null : "fx:id=\"tablePane\" was not injected: check your FXML file 'mainPage.fxml'.";
		assert energyYAxis != null : "fx:id=\"energyYAxis\" was not injected: check your FXML file 'mainPage.fxml'.";
		assert cloudPctOverride != null : "fx:id=\"cloudPctOverride\" was not injected: check your FXML file 'mainPage.fxml'.";
		assert chartSelector != null : "fx:id=\"chartSelector\" was not injected: check your FXML file 'mainPage.fxml'.";
		assert shadePctOverride != null : "fx:id=\"shadePctOverride\" was not injected: check your FXML file 'mainPage.fxml'.";
		assert cloudYAxis != null : "fx:id=\"cloudYAxis\" was not injected: check your FXML file 'mainPage.fxml'.";
		assert speedLimitOverride != null : "fx:id=\"speedLimitOverride\" was not injected: check your FXML file 'mainPage.fxml'.";
		assert cloudChart != null : "fx:id=\"cloudChart\" was not injected: check your FXML file 'mainPage.fxml'.";
		assert energyXAxis != null : "fx:id=\"energyXAxis\" was not injected: check your FXML file 'mainPage.fxml'.";
		assert currentCloudPct != null : "fx:id=\"currentCloudPct\" was not injected: check your FXML file 'mainPage.fxml'.";
		assert cloudXAxis != null : "fx:id=\"cloudXAxis\" was not injected: check your FXML file 'mainPage.fxml'.";
		assert currentShadePct != null : "fx:id=\"currentShadePct\" was not injected: check your FXML file 'mainPage.fxml'.";
		assert energyChart != null : "fx:id=\"energyChart\" was not injected: check your FXML file 'mainPage.fxml'.";
		assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'mainPage.fxml'.";
		assert currentEndingEnergy != null : "fx:id=\"currentEndingEnergy\" was not injected: check your FXML file 'mainPage.fxml'.";
		assert endingEnergyOverride != null : "fx:id=\"endingEnergyOverride\" was not injected: check your FXML file 'mainPage.fxml'.";
		assert runSimulation != null : "fx:id=\"runSimulation\" was not injected: check your FXML file 'mainPage.fxml'.";
		assert mapView != null : "fx:id=\"mapView\" was not injected: check your FXML file 'mainPage.fxml'.";
		assert carConfigMenu != null : "fx:id=\"carConfigMenu\" was not injected: check your FXML file 'mainPage.fxml'.";
		
		//endregion
		
		//region load carconfig and carconfig menu
		CarConfig.loadCarConfig("config.properties");
		
		initializeCarConfigMenu();
		//endregion
		
		
		displayData = Data.getData(endingEnergy);
		
		WeatherCaching.main(null);
		
		currentWeather = WeatherCaching.getCurrentWeather("current_weather-10_locations");
		forecasts = WeatherCaching.getWeatherForecast("weather-forecast-10_locations");
		
		energyGraphData = new XYChart.Series();
		actualEnergyGraphData = new XYChart.Series();
		cloudData = new XYChart.Series();

//		Image img = new Image("file:Map-Demo.png");
//		ImageView mapDemoPic = new ImageView(img);
//		mapDemoPic.setVisible(true);
		
		
		//region TextView handlers for manual override fields
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
		
		endingEnergyOverride.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				
				System.out.println("override ending energy");
				
				if (validatePercentage(endingEnergyOverride.textProperty())) {
					endingEnergyString = endingEnergyOverride.textProperty();
					System.out.println(endingEnergyString.getValue());
					endingEnergy = Integer.parseInt(endingEnergyString.getValue());
					currentEndingEnergy.textProperty().setValue(endingEnergyString.getValue() + "%");
					runSimulation.fire();
					
				} else {
					System.out.println("Not a valid number");
				}
				
				endingEnergyOverride.clear();
				
				
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
		
		
		initializeEnergyGraph();
		updateEnergyGraph();
		showDataTable();
		
		//display energy graph by default
		chartSelector.getSelectionModel().select("Energy");
	}
	
	/**
	 * Scans the carconfig directory for config files. Puts config files in a toggle-able group,
	 * and implements a listener to update car configuration when a new config file is selected.
	 */
	private void initializeCarConfigMenu() {
		
		
		ToggleGroup toggleGroup = new ToggleGroup();
		
		String[] configFileNames = getConfigFiles();
		if (configFileNames == null) {
			
			carConfigMenu.getItems().add(new MenuItem("No car config files found"));
		} else {
			RadioMenuItem current;
			
			for (int i = 0; i < configFileNames.length; i++) {
				
				current = new RadioMenuItem(configFileNames[i]);
				current.setToggleGroup(toggleGroup);
				carConfigMenu.getItems().add(current);
				
			}
		}
		
		/**
		 * When a configuration file is selected, load that car configuration.
		 */
		toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {
				
				RadioMenuItem selected = (RadioMenuItem) t1.getToggleGroup().getSelectedToggle(); // Cast object to radio button
				//System.out.println("Selected Radio Button - "+selected.getText());
				CarConfig.loadCarConfig(selected.getText());
				//System.out.println("Switching to car config " + selected.getText());
				//System.out.println("New config: ");
				//CarConfig.printConfig();
				
				//TODO: Aaron showing alert window when config changed. If too annoying scrap it
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Car Configuration");
				alert.setHeaderText(null);
				
				StringBuilder sb = new StringBuilder();
				sb.append("Car Configuration changed to ");
				sb.append(selected.getText());
				sb.append("\n\n" + CarConfig.asString() + "\n\n");
				sb.append("Please rerun simulation to use new config.");
				
				alert.setContentText(sb.toString());
				alert.showAndWait();
			}
		});
		
	}
	
	
	private void showEnergyGraph() {
		
		//TODO: Aaron- turned off animation from energy graph when I was testing car config switcher
		energyChart.setAnimated(false);
		energyChart.setVisible(true);
		cloudChart.setVisible(false);
	}
	
	public void updateEnergyGraph() {
		assert (displayData != null);
		
		energyGraphData.getData().clear();
		actualEnergyGraphData.getData().clear();
		for (int i = 0; i < displayData.size(); i++) {
			String totalCharge = displayData.get(i).getTotalCharge().getValue();
			String startTime = displayData.get(i).getStartTime().getValue();
			if (startTime != null && !startTime.isEmpty()) {
				if (totalCharge != null && !totalCharge.isEmpty()) {
					energyGraphData.getData().add(new XYChart.Data(startTime, Float.valueOf(totalCharge)));
				}
				
				String actualTotalCharge = displayData.get(i).getActualTotalCharge().getValue();
				if (actualTotalCharge != null && !actualTotalCharge.isEmpty()) {
					actualEnergyGraphData.getData().add(new XYChart.Data(startTime, Float.valueOf(actualTotalCharge)));
				}
			}
		}
	}
	
	private void initializeEnergyGraph() {
		energyXAxis.setLabel("Time");
		energyYAxis.setLabel("Battery (%)");
		energyGraphData.setName("Estimated Energy");
		actualEnergyGraphData.setName("Recorded Energy");
		energyChart.getData().add(energyGraphData);
		energyChart.getData().add(actualEnergyGraphData);
	}
	
	
	private String[] getConfigFiles() {
		
		ObservableList<RadioMenuItem> fileNames = null;
		
		File configFileFolder = new File(CONFIG_FILE_PATH);
		String[] listOfFiles = configFileFolder.list();
		
		return listOfFiles;
	}
	
	private void showCloudCoverGraph() {
		
		
		AveragedWeather aw;
		
		//TODO Aaron: currently this shows the data for current weather. However we probably want to search for each time in the table/simulation and get the weather data for that time.
		cloudXAxis.setLabel("Location");
		cloudYAxis.setLabel("Current Cloud Cover (%)");
		
		cloudYAxis.setLowerBound(0);
		cloudYAxis.setUpperBound(100);
		
		
		if (cloudData.getData().isEmpty()) {
			//forecasted, current, by location... etc?
			for (int i = 0; i < displayData.size(); i++) {
				
				//convert time from table data to UTC, from decimal
				String wanted = displayData.get(i).getStartTime().getValue();
				float lat = currentWeather[i].getLatitude();
				float lon = currentWeather[i].getLongitude();
				
				//System.out.println("Searching with " + toUTC(wanted) + " ," + lat + " ," + lon);
				aw = WeatherCaching.weatherSearch(toUTC(wanted), lat, lon);
				
				
				cloudData.getData().add(new XYChart.Data(displayData.get(i).getStartTime().getValue(), aw.getAvgCloudPercentage()));
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
		
		table.setItems(displayData);
		table.getColumns().addAll(startTimeColumn, endTimeColumn, velocityColumn, distanceColumn, elevationColumn, roadAngleColumn, batteryChargeColumn, totalChargeColumn);
		
		//Screen screen = Screen.getPrimary();
		//Rectangle2D bounds = screen.getVisualBounds();
		
	}
	
	public void fillDataTable(ObservableList<MasterData> data) {
		table.setItems(data);
	}
	
	
	/**
	 * @param text
	 * @return true if text is integer 0-100, false if not
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
	
	/**
	 * @param timeStr a string in the format HH:MM where HH is an hour 00-23
	 * @return timeStr as a ZonedDateTime in UTC with today's date
	 */
	private ZonedDateTime toUTC(String timeStr) {
		
		ZonedDateTime utc = null;
		
		String[] hoursMinutes = timeStr.split(":");
		
		Calendar now = Calendar.getInstance();
		
		//System.out.println("Hours = "+Integer.valueOf(hoursMinutes[0]));
		//System.out.println("Minutes = "+Integer.valueOf(hoursMinutes[1]));
//			Date date = sdf.parse(timeStr);
		utc = ZonedDateTime.of(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, (now.get(Calendar.DAY_OF_MONTH)), 0, 0, 0, 0, ZoneId.of("UTC"));
		utc = utc.plusHours(Integer.valueOf(hoursMinutes[0]) + 5);
		
		//System.out.println("Converted " + timeStr + " to UTC format" + utc.toString());
		return utc;
		
		
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
