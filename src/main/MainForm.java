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




    	/*
        table.setEditable(false);
	
//		TableColumn firstNameCol = new TableColumn("Time");
		String[] titles = {"Time", "Angle", "Solar", "Aero", "Roll", "Total", "Battery", "Batt Cap", "Batt Chg", "Tot Chg", "Distance"};
		String[] columnNames = {"middleTime","sunAngle","solarPower","aeroPower","rollingPower","totalPower","batteryPower","batteryCap","batteryCharge","totalCharge","distance"};
		TableColumn[] columns = new TableColumn[titles.length];
		
		for(int i = 0; i < titles.length; i++){
			columns[i] = new TableColumn(titles[i]);
			columns[i].setCellValueFactory(
					new PropertyValueFactory<MasterData, String>(columnNames[i])
			);
		}

		ObservableList<MasterData> data = Data.getData();
		table.setItems(data);
		table.getColumns().addAll(columns);
        
        StackPane root = new StackPane();
        root.getChildren().add(table);
        
        Scene scene = new Scene(root, 300, 250);
	
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
	
		primaryStage.setX(bounds.getMinX());
		primaryStage.setY(bounds.getMinY());
		primaryStage.setWidth(bounds.getWidth());
		primaryStage.setHeight(bounds.getHeight());

		StackPane root = new StackPane();
		root.getChildren().add(table);


        */


        //added by aaron b.
        VBox root = new VBox();
        root.getChildren().addAll(tableButton, weatherButton);
        //end add by aaron b.


        Scene scene = new Scene(root, 300, 250);


        primaryStage.setTitle("Solar Car Performance Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
