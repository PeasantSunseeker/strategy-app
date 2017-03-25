package ui.controllers;

import com.lynden.gmapsfx.MapComponentInitializedListener;
import javafx.fxml.Initializable;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventHandler;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.service.directions.*;
import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import netscape.javascript.JSObject;
/**
 * Created by adam on 3/8/17.
 */
public class SpeedEntryController implements Initializable, MapComponentInitializedListener {
    @FXML
    private GoogleMapView mapView;

    @FXML
    private TextField speedField;

    private GoogleMap map;

    private GeocodingService geocodingService;

    private StringProperty entSpeed = new SimpleStringProperty();
    private Marker mark1,mark2;
    private LatLong ll1,ll2;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapView.addMapInializedListener(this);
        entSpeed.bind(speedField.textProperty());
    }
    @Override
    public void mapInitialized() {
        geocodingService = new GeocodingService();
        MapOptions mapOptions = new MapOptions();

        mapOptions.center(new LatLong(47.6097, -122.3331))
                .mapType(MapTypeIdEnum.ROADMAP)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .zoom(12);

        map = mapView.createMap(mapOptions);
        MarkerOptions mo = new MarkerOptions();
        mark1 = new Marker(mo);
        mark2 = new Marker(mo);
        ll1 = null;
        ll2 = null;




        UIEventHandler uieh = new UIEventHandler() {
            @Override
            public void handle(JSObject jso) {
                map.clearMarkers();
                int zoom = map.getZoom();
                map.setZoom(zoom+1);
                map.setZoom(zoom);
                System.out.println("cleared markers");

                ll1 = ll2;
                ll2 = new LatLong((JSObject) jso.getMember("latLng"));

                if(ll1 != null) {
                    mark1.setPosition(ll1);
                    map.addMarker(mark1);
                }
                mark2.setPosition(ll2);
                map.addMarker(mark2);

            }
        };
        map.addUIEventHandler(UIEventType.click, uieh);

    }

    @FXML
    public void speedEnt(ActionEvent ae){
        System.out.println(ll2.getLongitude()+","+ll2.getLatitude()+":"+entSpeed.getValue());
    }

}
