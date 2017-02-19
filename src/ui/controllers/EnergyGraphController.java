package ui.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import main.Data;
import utilities.MasterData;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * PROJECT: seniordesign
 * AUTHOR: aaron  2/15/2017.
 * DATE: 2/15/2017
 *
 * DESCRIPTION:
 *
 *
 * INPUTS:
 *
 *
 * OUTPUTS:
 */


public class EnergyGraphController {
	
	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;
	
	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;
	
	@FXML // fx:id="yAxis"
	private NumberAxis yAxis; // Value injected by FXMLLoader
	
	@FXML // fx:id="xAxis"
	private CategoryAxis xAxis; // Value injected by FXMLLoader
	
	@FXML // fx:id="energyGraph"
	private LineChart<?, ?> energyGraph; // Value injected by FXMLLoader
	
	@FXML
		// This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert yAxis != null : "fx:id=\"yAxis\" was not injected: check your FXML file 'energygraph.fxml'.";
		assert xAxis != null : "fx:id=\"xAxis\" was not injected: check your FXML file 'energygraph.fxml'.";
		assert energyGraph != null : "fx:id=\"energyGraph\" was not injected: check your FXML file 'energygraph.fxml'.";
		
		ObservableList<MasterData> data = Data.getData();
		
		
		//the data
		XYChart.Series series = new XYChart.Series();
		xAxis.setLabel("Time");
		yAxis.setLabel("Battery (%)");
		
		
		for (int i = 0; i < data.size(); i++) {
			series.getData().add(new XYChart.Data(data.get(i).getStartTime().getValue(), Float.valueOf(data.get(i).getTotalCharge().getValue())));
		}
		
		energyGraph.getData().add(series);
		
		
	}
}
