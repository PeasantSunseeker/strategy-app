package ui;

import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
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
