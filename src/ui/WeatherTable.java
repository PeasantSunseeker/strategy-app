package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.HourlyForecast;
import net.aksingh.owmjapis.OpenWeatherMap;
import weather.ApiKey;

import java.io.IOException;

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
public class WeatherTable {
	
	private final String DEGREES = "\u00b0";
	
	private String title;
	private float temperature;
	
	private OpenWeatherMap owm;
	private CurrentWeather cw;
	private HourlyForecast.Forecast forecast;
	
	
	public WeatherTable(String title) {
		this.title = title;
	}
	
	public Stage getCurrentConditions(Stage stage, String cityName) {
		
		FXMLLoader fxmlLoader = new FXMLLoader();
		Parent root1 = null;
		
		stage = new Stage();
		
		
		try {
			root1 = FXMLLoader.load(getClass().getResource("weathertable.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		//do the UI stuff

        /*
		Label cityLabel = new Label();
        cityLabel.setText(cityName);

        Label temperatureLabel = new Label();
        temperatureLabel.setText("Temperature: " + String.valueOf(temperature) + DEGREES + "C");
        */
		
		stage.setScene(new Scene(root1));
		return stage;
	}
	
	public String getTemperature() {
		return String.valueOf(temperature);
	}
	
}
