package ui;

import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import main.Data;
import utilities.MasterData;

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
public class DataTable {

    public Stage fillDataTable() {
        Stage dataTableStage = new Stage();

        TableView table = new TableView();

        table.setEditable(false);
	
		ObservableList<MasterData> data = Data.getData();
	
		TableColumn<MasterData,String> timeColumn = new TableColumn("Time");
		timeColumn.setCellValueFactory(temp -> temp.getValue().getMiddleTime());
	
		TableColumn<MasterData,String> batteryChargeColumn = new TableColumn("Bat Charge");
		batteryChargeColumn.setCellValueFactory(temp -> temp.getValue().getBatteryCharge());
	
		TableColumn<MasterData,String> totalChargeColumn = new TableColumn("Total Charge Used");
		totalChargeColumn.setCellValueFactory(temp -> temp.getValue().getTotalCharge());
	
		TableColumn<MasterData,String> distanceColumn = new TableColumn("Distance Driven");
		distanceColumn.setCellValueFactory(temp -> temp.getValue().getDistance());
		
        table.setItems(data);
        table.getColumns().addAll(timeColumn,batteryChargeColumn,totalChargeColumn,distanceColumn);

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        dataTableStage.setX(bounds.getMinX());
        dataTableStage.setY(bounds.getMinY());
        dataTableStage.setWidth(bounds.getWidth());
        dataTableStage.setHeight(bounds.getHeight());

        Scene scene = new Scene(table, 1024, 768);
        dataTableStage.setScene(scene);
        return dataTableStage;
    }

}
