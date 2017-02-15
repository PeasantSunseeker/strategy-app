package ui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import google.Elevation;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import utilities.Position;

/**
 * PROJECT: seniordesign
 * AUTHOR: aaron  2/13/2017.
 * DATE: 2/13/2017
 *
 * DESCRIPTION:
 *
 *
 * INPUTS:
 *
 *
 * OUTPUTS:
 */
public class ElevationChartController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="elevationLineChart"
    private LineChart<?, ?> elevationLineChart; // Value injected by FXMLLoader

    @FXML // fx:id="locationAxis"
    private CategoryAxis locationAxis; // Value injected by FXMLLoader

    @FXML // fx:id="elevationAxis"
    private NumberAxis elevationAxis; // Value injected by FXMLLoader

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert elevationLineChart != null : "fx:id=\"elevationLineChart\" was not injected: check your FXML file 'elevationchart.fxml'.";

        elevationLineChart.setTitle("Elevation");
        elevationAxis.setLabel("Elevation (m)");
        XYChart.Series series = new XYChart.Series();

        //get the data
        Position[] pos = Position.loadPositions("leg-1-10_items");


        for (int i = 0; i < pos.length; i++) {
            System.out.println(pos[i].getElevation());
            series.getData().add(new XYChart.Data(String.valueOf(i), pos[i].getElevation()));
        }

        elevationLineChart.getData().add(series);

    }
}
