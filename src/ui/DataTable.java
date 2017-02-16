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
 * <p>
 * DESCRIPTION:
 * <p>
 * <p>
 * INPUTS:
 * <p>
 * <p>
 * OUTPUTS:
 */
public class DataTable {
	
	public Stage fillDataTable() {
		Stage dataTableStage = new Stage();
		
		TableView table = new TableView();
		
		table.setEditable(false);
		
		ObservableList<MasterData> data = Data.getData();
		
		TableColumn<MasterData, String> startTimeColumn = new TableColumn("Start Time");
		startTimeColumn.setCellValueFactory(temp -> temp.getValue().getStartTime());
		
		TableColumn<MasterData, String> endTimeColumn = new TableColumn("End Time");
		endTimeColumn.setCellValueFactory(temp -> temp.getValue().getEndTime());
		
		TableColumn<MasterData, String> distanceColumn = new TableColumn("Distance (km)");
		distanceColumn.setCellValueFactory(temp -> temp.getValue().getDistance());
		
		TableColumn<MasterData, String> velocityColumn = new TableColumn("Speed (km/h)");
		velocityColumn.setCellValueFactory(temp -> temp.getValue().getVelocity());
		
		TableColumn<MasterData, String> roadAngleColumn = new TableColumn("Road Angle");
		roadAngleColumn.setCellValueFactory(temp -> temp.getValue().getRoadAngle());
		
		TableColumn<MasterData, String> elevationColumn = new TableColumn("Elevation");
		elevationColumn.setCellValueFactory(temp -> temp.getValue().getElevation());
		
		TableColumn<MasterData, String> batteryChargeColumn = new TableColumn("Bat Charge");
		batteryChargeColumn.setCellValueFactory(temp -> temp.getValue().getBatteryCharge());
		
		TableColumn<MasterData, String> totalChargeColumn = new TableColumn("Total Charge Used");
		totalChargeColumn.setCellValueFactory(temp -> temp.getValue().getTotalCharge());
		
		table.setItems(data);
		table.getColumns().addAll(startTimeColumn, endTimeColumn, velocityColumn, distanceColumn, elevationColumn, roadAngleColumn, batteryChargeColumn, totalChargeColumn);
		
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		
//		dataTableStage.setX(bounds.getMinX());
//		dataTableStage.setY(bounds.getMinY());
//		dataTableStage.setWidth(bounds.getWidth());
//		dataTableStage.setHeight(bounds.getHeight());
		
		Scene scene = new Scene(table, 1024, 768);
		dataTableStage.setScene(scene);
		return dataTableStage;
	}
	
}
