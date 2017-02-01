package main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Menu;
import net.aksingh.owmjapis.HourlyForecast;
import net.aksingh.owmjapis.OpenWeatherMap;

/**
 * PROJECT: seniordesign
 * AUTHOR: aaron  1/30/2017.
 * DATE: 1/30/2017
 *
 * DESCRIPTION:
 *
 *
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

    @FXML // fx:id="viewmenu"
    private Menu viewmenu; // Value injected by FXMLLoader

    @FXML // fx:id="temperatureForecast"
    private LineChart<?, ?> temperatureForecast; // Value injected by FXMLLoader

    @FXML
    void showWeatherChart(ActionEvent event) {


        OpenWeatherMap owm;
        HourlyForecast hf = null;
        HourlyForecast.Forecast forecast;

        //api key for OpenWeatherMap
        owm = new OpenWeatherMap("2e25d5e81b7d7dae88af4f1033d3510f");
        owm.setUnits(OpenWeatherMap.Units.METRIC);
        try {
            hf = owm.hourlyForecastByCityName("Kalamazoo");
        } catch (IOException e) {
            e.printStackTrace();
        }

        XYChart.Series series = new XYChart.Series();
        series.setName("temperature");

        //add forecast data to data series
        for (int i = 1; i <= hf.getForecastCount(); i++) {
            forecast = hf.getForecastInstance(i - 1);

            series.getData().add(new XYChart.Data(forecast.getDateTimeText(), forecast.getMainInstance().getTemperature()));

        }

        temperatureForecast.getData().addAll(series);
        temperatureForecast.setVisible(!temperatureForecast.isVisible());

    }

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert viewmenu != null : "fx:id=\"viewmenu\" was not injected: check your FXML file 'main.fxml'.";
        assert temperatureForecast != null : "fx:id=\"temperatureForecast\" was not injected: check your FXML file 'main.fxml'.";

    }


}

