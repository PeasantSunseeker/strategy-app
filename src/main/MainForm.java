package main;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.DataTable;
import ui.WeatherLineChart;

import java.io.IOException;

/**
 * Created by Broderick on 12/30/2016.
 */
public class MainForm extends Application {

    private TableView table = new TableView();
    private static String[] arguments;

    //added by aaron b.
    private Stage currentStage;

    @Override
    public void start(Stage primaryStage) {


        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("main.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setTitle("Solar Car Performance Application");
        primaryStage.setScene(new Scene(root, 1024, 768));
        primaryStage.show();

        /*
        Button tableButton;
        Button weatherButton;

        currentStage = new Stage();


        tableButton = new Button();
        tableButton.setText("Show data table");

        //data table is built in ui.DataTable
        tableButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DataTable dt = new DataTable();
                currentStage = dt.fillDataTable();
                currentStage.show();
            }
        });


        //weather chart is built in ui.WeatherLineChart
        weatherButton = new Button();
        weatherButton.setText("Show temperature forecast");
        weatherButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                WeatherLineChart temperatureForecastChart = new WeatherLineChart("Temperature Forecast", "Date",
                        "Temperature (C)");
                currentStage = temperatureForecastChart.fillTemperatureChart(currentStage);
                currentStage.show();
            }
        });

        VBox root = new VBox();
        root.getChildren().addAll(tableButton, weatherButton);


        Scene scene = new Scene(root, 300, 250);


        primaryStage.setTitle("Solar Car Performance Application");
        primaryStage.setScene(scene);
        primaryStage.show();

        if (arguments.length > 0 && arguments[0].equals("brodie")) {
            tableButton.fire();
        }
        */
    }

    public static void main(String[] args) {
        arguments = args;
        launch(args);
    }
}
