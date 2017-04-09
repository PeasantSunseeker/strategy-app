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
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import netscape.javascript.JSObject;
import utilities.Position;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by adam on 3/25/17.
 */
public class SpeedLimitToolController implements Initializable, MapComponentInitializedListener {
	@FXML
	private GoogleMapView mapView;
	@FXML
	private TextField speedField;
	@FXML
	private ListView<String> enteredSpeeds;
	private ObservableList listEntries;
	
	private GoogleMap map;
	
	private GeocodingService geocodingService;
	
	private StringProperty entSpeed = new SimpleStringProperty();
	private Marker mark1, mark2;
	private int mark1idx, mark2idx;
	private LatLong ll1, ll2;
	private Position[] positions;
	private Polyline polyline;
	
	private String KML_URL = "http://www.google.com/maps/d/u/0/kml?forcekml=1&mid=16hXmON2hIuj99y045M-sDK73-XE";
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
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


//        positions = Position.loadPositions("leg-1-10_items");
		positions = Position.loadPositions("leg-1-complete");
		
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
		
		MarkerOptions mo = new MarkerOptions();
		mark1 = new Marker(mo);
		mark2 = new Marker(mo);
		ll1 = new LatLong(positions[0].getLatitude(), positions[0].getLongitude());
		ll2 = new LatLong(positions[0].getLatitude(), positions[0].getLongitude());
		
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
				mark2idx = minIdx;
				if (ll1 != null) {
					mark1.setPosition(ll1);
					mark1idx = mark2idx;
					map.addMarker(mark1);
				}
				mark2.setPosition(ll2);
				
				map.addMarker(mark2);
				for (int i = Math.min(mark1idx, mark2idx); i <= Math.max(mark1idx, mark2idx); i++) {
					positions[i].setVelocity(Float.parseFloat(speedField.getText()));
				}
				
				
			}
		};
		
		
		map.addUIEventHandler(UIEventType.rightclick, clickHandle);
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
			positions[i].setVelocity(enteredSpeed);
		}
		String formattedSpeedLimit = String.format("from: %.3f,%.3f\n" +
						"to: %.3f,%.3f\n" +
						"Speed Limit: %.0f",
				ll1.getLatitude(), ll1.getLongitude(), ll2.getLatitude(), ll2.getLongitude(), enteredSpeed
		);
		System.out.println(formattedSpeedLimit);
		listEntries.add(formattedSpeedLimit);
		enteredSpeeds.setItems(listEntries);
		
		
	}
	
}
