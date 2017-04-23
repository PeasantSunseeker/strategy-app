package SpeedLimitTool;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventHandler;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import com.lynden.gmapsfx.shapes.Circle;
import com.lynden.gmapsfx.shapes.Polyline;
import com.lynden.gmapsfx.shapes.PolylineOptions;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import netscape.javascript.JSObject;
import utilities.Position;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Adam on 3/25/17.
 */
public class SpeedLimitToolController implements Initializable, MapComponentInitializedListener {
	@FXML
	private GoogleMapView mapView;
	@FXML
	private TextField speedField;
	@FXML
	private ListView<String> enteredSpeeds;
	@FXML
	private VBox container;

	private ObservableList listEntries;
	
	private GoogleMap map;
	
	private GeocodingService geocodingService;
	
	private StringProperty entSpeed = new SimpleStringProperty();
	private Marker mark1, mark2;
	private int mark1idx, mark2idx;
	private LatLong ll1, ll2;
	private Position[] positions;
	private Polyline polyline;
	private boolean safeToClose;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		safeToClose = true;
		mapView.addMapInializedListener(this);
		entSpeed.bind(speedField.textProperty());
		listEntries = FXCollections.observableArrayList();
		enteredSpeeds.setEditable(true);
		enteredSpeeds.setItems(listEntries);

	}
	
	@Override
	public void mapInitialized() {
		MapOptions mapOptions = new MapOptions();
		
		mapOptions.center(new LatLong(40, -97))
				.mapType(MapTypeIdEnum.ROADMAP)
				.overviewMapControl(false)
				.panControl(false)
				.rotateControl(false)
				.scaleControl(false)
				.streetViewControl(false)
				.zoomControl(false)
				.zoom(4);
		
		
		map = mapView.createMap(mapOptions);


		
		MarkerOptions mo = new MarkerOptions();
		mark1 = new Marker(mo);
		mark2 = new Marker(mo);
		ll1 = null;
		ll2 = null;
		
		UIEventHandler clickHandle = new UIEventHandler() {
			
			@Override
			public void handle(JSObject jso) {
				LatLong llClicked;
				Position posClicked;
				int minIdx = -1;
				double minDist = Double.MAX_VALUE;
				
				map.clearMarkers();
				int zoom = map.getZoom();
				map.setZoom(zoom + 1);
				map.setZoom(zoom);
				
				ll1 = ll2;
				llClicked = new LatLong((JSObject) jso.getMember("latLng"));
				posClicked = new Position((float) llClicked.getLatitude(), (float) llClicked.getLongitude(), 0);
				for (int i = 0; i < positions.length; i++) {
					if (Position.getDistance(positions[i], posClicked) < minDist) {
						minDist = Position.getDistance(positions[i], posClicked);
						minIdx = i;
					}
				}
				
				ll2 = new LatLong(positions[minIdx].getLatitude(), positions[minIdx].getLongitude());

				if (ll1 != null) {
					mark1.setPosition(ll1);
					mark1idx = mark2idx;
					map.addMarker(mark1);
				}
				mark2idx = minIdx;
				mark2.setPosition(ll2);
				
				map.addMarker(mark2);

			}
		};
		
		
		map.addUIEventHandler(UIEventType.rightclick, clickHandle);
		container.getScene().getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				close(null);
			}
		});
	}
	
	@FXML
	public void speedEntered(ActionEvent ae) {
		float enteredSpeed = -1;
		try {
			enteredSpeed = Float.parseFloat(speedField.getText());
		} catch (NumberFormatException ex) {
			speedField.setText("Enter a number");
			speedField.requestFocus();
			return;
		}
		if (enteredSpeed == 0) {
			speedField.setText("Enter a number");
			speedField.requestFocus();
			return;
		}

		for (int i = Math.min(mark1idx, mark2idx); i <= Math.max(mark1idx, mark2idx); i++) {
			positions[i].setVelocity(enteredSpeed*(float) 1.60934);

		}
		update_speed_list();
		safeToClose = false;
		
		
	}
	@FXML
	public void save(ActionEvent ae){
		FileChooser fc = new FileChooser();
		File saveFile = fc.showSaveDialog(null);
		Position.savePositions(positions,saveFile.getName().replace(".csv",""));
		safeToClose = true;
	}

	@FXML
	public void load(ActionEvent ae){
		FileChooser fc = new FileChooser();
		fc.setTitle("Select Positions File");
		File posFile = fc.showOpenDialog(null);

		positions = Position.loadPositions(posFile.getAbsolutePath().replace(".csv",""));

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
		update_speed_list();


		enteredSpeeds.setItems(listEntries);
	}

	public void update_speed_list(){
		LatLong start=new LatLong(positions[0].getLatitude(), positions[0].getLongitude());
		LatLong end;
		float speed;
		listEntries.clear();
		String formattedSpeedLimit;
		for(int i=0;i<positions.length-1;i++){
			if(positions[i].getVelocity() != positions[i+1].getVelocity()){
				end = new LatLong(positions[i+1].getLatitude(), positions[i+1].getLongitude());
				formattedSpeedLimit = String.format("from: %.3f,%.3f\n" +
								"to: %.3f,%.3f\n" +
								"Speed Limit: %.0f",
						start.getLatitude(), start.getLongitude(), end.getLatitude(), end.getLongitude(), positions[i].getVelocity()/1.60934
				);
				listEntries.add(formattedSpeedLimit);
				start = new LatLong(positions[i+1].getLatitude(),positions[i+1].getLongitude());

			}

		}
		end = new LatLong(positions[positions.length-1].getLatitude(),positions[positions.length-1].getLongitude());
		formattedSpeedLimit = String.format("from: %.3f,%.3f\n" +
						"to: %.3f,%.3f\n" +
						"Speed Limit: %.0f",
				start.getLatitude(), start.getLongitude(), end.getLatitude(), end.getLongitude(), positions[positions.length-1].getVelocity()/1.60934
		);
		listEntries.add(formattedSpeedLimit);
		enteredSpeeds.setItems(listEntries);
	}

	@FXML
	public void close(ActionEvent ae){

		if(safeToClose){
			Platform.exit();
			System.exit(0);
		}
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Unsaved Changes!");
		alert.setHeaderText("You have some unsaved changes");
		alert.setContentText("Would you like to save before closing?");

		ButtonType saveAndClose = new ButtonType("Save and close");
		ButtonType noSaveClose = new ButtonType("Close without saving");
		ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(saveAndClose, noSaveClose, cancel);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == saveAndClose){
			save(null);
			Platform.exit();
			System.exit(0);
		}
		else if (result.get() == noSaveClose) {
			Platform.exit();
			System.exit(0);
		}
		else {
			return;
		}

	}
}
