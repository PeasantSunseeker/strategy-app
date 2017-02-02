package ui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import net.aksingh.owmjapis.HourlyForecast;
import net.aksingh.owmjapis.OpenWeatherMap;
import weather.ApiKey;

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
public class TemperatureChartController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private LineChart<?, ?> weatherLineChart;

    @FXML
    private NumberAxis temperatureAxis;

    @FXML
    private CategoryAxis dateAxis;

    @FXML
    void initialize() {
        assert weatherLineChart != null : "fx:id=\"weatherLineChart\" was not injected: check your FXML file 'temperaturechart.fxml'.";


        //TODO: is this where I put the data in the line chart?


        weatherLineChart.setTitle("Temperature Forecast");

        OpenWeatherMap owm = new OpenWeatherMap(ApiKey.getApiKey());
        owm.setUnits(OpenWeatherMap.Units.METRIC);
        HourlyForecast hf = null;
        HourlyForecast.Forecast forecast;

        //here is the data that will fill in the chart
        XYChart.Series series = new XYChart.Series();
        //series.setName("data");

        try {
            hf = owm.hourlyForecastByCityName("Kalamazoo");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //fill the data series with date/value pairs
        for (int i = 1; i <= hf.getForecastCount(); i++) {
            forecast = hf.getForecastInstance(i - 1);
            series.getData().add(new XYChart.Data(forecast.getDateTimeText(), forecast.getMainInstance().getTemperature()));
        }

        weatherLineChart.getData().add(series);


    }
}
