package main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.stage.Stage;
import net.aksingh.owmjapis.HourlyForecast;
import net.aksingh.owmjapis.OpenWeatherMap;
import ui.DataTable;
import ui.WeatherLineChart;
import ui.WeatherTable;

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

    @FXML // fx:id="temperature_forecast_button"
    private Button temperature_forecast_button; // Value injected by FXMLLoader

    @FXML // fx:id="viewmenu"
    private Menu viewmenu; // Value injected by FXMLLoader

    @FXML // fx:id="data_table_button"
    private Button data_table_button; // Value injected by FXMLLoader

    @FXML // fx:id="current_conditions_button"
    private Button current_conditions_button; // Value injected by FXMLLoader

    @FXML
    private Label temperature_label;

    @FXML
    private Label city_label;

    @FXML
    void showTemperatureChart(ActionEvent event) {

        //open temperature forecast chart in a new window

        Stage stage = new Stage();
        WeatherLineChart temperatureForecastChart = new WeatherLineChart("Temperature Forecast", "Date",
                "Temperature (C)");
        stage = temperatureForecastChart.fillTemperatureChart(stage);
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

        WeatherTable weatherTable = new WeatherTable("Current Conditions");
        Stage stage = new Stage();

        stage = weatherTable.getCurrentConditions(stage, "Kalamazoo");
        stage.show();
    }

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert temperature_forecast_button != null : "fx:id=\"temperature_forecast_button\" was not injected: check your FXML file 'main.fxml'.";
        assert viewmenu != null : "fx:id=\"viewmenu\" was not injected: check your FXML file 'main.fxml'.";
        assert data_table_button != null : "fx:id=\"data_table_button\" was not injected: check your FXML file 'main.fxml'.";
        assert current_conditions_button != null : "fx:id=\"current_conditions_button\" was not injected: check your FXML file 'main.fxml'.";

    }


}

