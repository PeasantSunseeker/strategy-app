package ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;
import weather.ApiKey;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * PROJECT: seniordesign
 * AUTHOR: aaron  2/1/2017.
 * DATE: 2/1/2017
 *
 * DESCRIPTION:
 *
 *
 * INPUTS:
 *
 *
 * OUTPUTS:
 */
public class WeatherTableController {

    private final String DEGREES = "\u00b0";

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label temperatureLabel;

    @FXML
    private Label cityLabel;

    @FXML
    void initialize() {
        assert temperatureLabel != null : "fx:id=\"temperatureLabel\" was not injected: check your FXML file 'weathertable.fxml'.";
        assert cityLabel != null : "fx:id=\"cityLabel\" was not injected: check your FXML file 'weathertable.fxml'.";

        float temperature;

        //get the forecast
        OpenWeatherMap owm = new OpenWeatherMap(ApiKey.getApiKey());
        CurrentWeather cw = null;
        owm.setUnits(OpenWeatherMap.Units.METRIC);
        try {
            cw = owm.currentWeatherByCityName("Kalamazoo");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //put the data in the labels

        cityLabel.setText("Kalamazoo");

        temperature = cw.getMainInstance().getTemperature();
        //System.out.println("Current temp: " + temperature + DEGREES + "C");
        temperatureLabel.setText("Current temp: " + temperature + DEGREES + "C");

    }
}
