package ui;

import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import net.aksingh.owmjapis.HourlyForecast;
import net.aksingh.owmjapis.OpenWeatherMap;
import weather.ApiKey;

import java.io.IOException;

/**
 * PROJECT: seniordesign
 * AUTHOR: aaron  1/25/2017.
 * DATE: 1/25/2017
 *
 * DESCRIPTION:
 *
 *
 * INPUTS:
 *
 *
 * OUTPUTS:
 */
public class TemperatureChart {
	
	private String title;
	private String xAxisLabel;
	private String yAxisLabel;
	
	private OpenWeatherMap owm;
	private HourlyForecast hf;
	private HourlyForecast.Forecast forecast;
	
	
	public TemperatureChart(String title, String xAxisLabel, String yAxisLabel) {
		this.title = title;
		this.xAxisLabel = xAxisLabel;
		this.yAxisLabel = yAxisLabel;
		
	}
	
	public Stage fillTemperatureChart(Stage weatherStage) {
		
		weatherStage = new Stage();
		
		getForecast();
		
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		
		xAxis.setLabel(xAxisLabel);
		yAxis.setLabel(yAxisLabel);
		
		final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
		lineChart.setTitle(title);
		
		//here is the data that will fill in the chart
		XYChart.Series series = new XYChart.Series();
		series.setName("data");
		
		//fill the data series with date/value pairs
		for (int i = 1; i <= hf.getForecastCount(); i++) {
			forecast = hf.getForecastInstance(i - 1);
			series.getData().add(new XYChart.Data(forecast.getDateTimeText(), forecast.getMainInstance().getTemperature()));
		}
		
		
		Scene scene = new Scene(lineChart, 1024, 768);
		lineChart.getData().add(series);
		
		xAxis.setAnimated(false);
		yAxis.setAnimated(false);
		weatherStage.setScene(scene);
		return weatherStage;
	}
	
	public void getForecast() {
		
		//api key for OpenWeatherMap
		owm = new OpenWeatherMap(ApiKey.getApiKey());
		owm.setUnits(OpenWeatherMap.Units.METRIC);
		try {
			hf = owm.hourlyForecastByCityName("Kalamazoo");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
