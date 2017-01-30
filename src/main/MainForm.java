package main;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import ui.DataTable;
import ui.WeatherLineChart;
import utilities.MasterData;

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
                WeatherLineChart weatherLineChart = new WeatherLineChart("Temperature Forecast", "Date",
                        "Temperature (C)");
                currentStage = weatherLineChart.fillWeatherStage(currentStage);
                currentStage.show();
            }
        });


        //added by aaron b.
        VBox root = new VBox();
        root.getChildren().addAll(tableButton, weatherButton);
        //end add by aaron b.


        Scene scene = new Scene(root, 300, 250);


        primaryStage.setTitle("Solar Car Performance Application");
        primaryStage.setScene(scene);
        primaryStage.show();
		
        if(arguments.length > 0 && arguments[0].equals("brodie")) {
			tableButton.fire();
		}
    }

    public static void main(String[] args) {
        arguments = args;
        launch(args);
    }
}
