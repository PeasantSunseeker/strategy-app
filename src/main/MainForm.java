package main;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import utilities.MasterData;

/**
 * Created by Broderick on 12/30/2016.
 */
public class MainForm  extends Application {
	
	private TableView table = new TableView();
	
    @Override
    public void start(Stage primaryStage) {
	
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
        
        primaryStage.setTitle("Solar Car Performance Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
