package SpeedLimitTool;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventHandler;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import com.lynden.gmapsfx.shapes.Circle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import netscape.javascript.JSObject;

import java.awt.event.MouseEvent;
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

    private GoogleMap map;

    private GeocodingService geocodingService;

    private StringProperty entSpeed = new SimpleStringProperty();
    private Marker mark1,mark2;
    private LatLong ll1,ll2;

    private String KML_URL = "http://www.google.com/maps/d/u/0/kml?forcekml=1&mid=16hXmON2hIuj99y045M-sDK73-XE";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapView.addMapInializedListener(this);
        entSpeed.bind(speedField.textProperty());
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


        mapView.getWebview().getEngine().executeScript(
                "var ctaLayer = new google.maps.KmlLayer({\n" +
                        "url: '"+ KML_URL +"',\n" +
                        "map: " + map.getVariableName() +"\n"+
                        "});");



        MarkerOptions mo = new MarkerOptions();
        mark1 = new Marker(mo);
        mark2 = new Marker(mo);
        ll1 = null;
        ll2 = null;

        UIEventHandler clickHandle = new UIEventHandler() {
            @Override
            public void handle(JSObject jso) {

                map.clearMarkers();
                int zoom = map.getZoom();
                map.setZoom(zoom+1);
                map.setZoom(zoom);

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


        map.addUIEventHandler(UIEventType.rightclick, clickHandle);
    }

}
