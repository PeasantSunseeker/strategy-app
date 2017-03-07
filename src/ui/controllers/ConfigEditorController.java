package ui.controllers;


import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import config.CarConfig;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * PROJECT: seniordesign
 * AUTHOR: aaron  2/27/2017.
 * DATE: 2/27/2017
 *
 * DESCRIPTION:
 *
 *
 * INPUTS:
 *
 *
 * OUTPUTS:
 */
public class ConfigEditorController {
	
	private Properties currentConfig;
	
	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;
	
	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;
	
	@FXML // fx:id="parasiticChargingLabel"
	private Label parasiticChargingLabel; // Value injected by FXMLLoader
	
	@FXML // fx:id="solarMaxPowerLabel"
	private Label solarMaxPowerLabel; // Value injected by FXMLLoader
	
	@FXML // fx:id="solarChargingLabel"
	private Label solarChargingLabel; // Value injected by FXMLLoader
	
	@FXML // fx:id="motorEfficiencyTextField"
	private TextField motorEfficiencyTextField; // Value injected by FXMLLoader
	
	@FXML // fx:id="solarDrivingTextField"
	private TextField solarDrivingTextField; // Value injected by FXMLLoader
	
	@FXML // fx:id="rollingLabel"
	private Label rollingLabel; // Value injected by FXMLLoader
	
	@FXML // fx:id="batteryCapacityLabel"
	private Label batteryCapacityLabel; // Value injected by FXMLLoader
	
	@FXML // fx:id="batteryCapacityTextField"
	private TextField batteryCapacityTextField; // Value injected by FXMLLoader
	
	@FXML // fx:id="parasiticChargingTextField"
	private TextField parasiticChargingTextField; // Value injected by FXMLLoader
	
	@FXML // fx:id="dragAreaLabel"
	private Label dragAreaLabel; // Value injected by FXMLLoader
	
	@FXML // fx:id="peukertLabel"
	private Label peukertLabel; // Value injected by FXMLLoader
	
	@FXML // fx:id="solarChargingTextField"
	private TextField solarChargingTextField; // Value injected by FXMLLoader
	
	@FXML // fx:id="motorEfficiencyLabel"
	private Label motorEfficiencyLabel; // Value injected by FXMLLoader
	
	@FXML // fx:id="parasiticDrivingLabel"
	private Label parasiticDrivingLabel; // Value injected by FXMLLoader
	
	@FXML // fx:id="solarDrivingLabel"
	private Label solarDrivingLabel; // Value injected by FXMLLoader
	
	@FXML // fx:id="solarMaxPowerTextField"
	private TextField solarMaxPowerTextField; // Value injected by FXMLLoader
	
	@FXML // fx:id="rollingTextField"
	private TextField rollingTextField; // Value injected by FXMLLoader
	
	@FXML // fx:id="saveButton"
	private Button saveButton; // Value injected by FXMLLoader
	
	@FXML // fx:id="peukertTextField"
	private TextField peukertTextField; // Value injected by FXMLLoader
	
	@FXML // fx:id="dragAreaTextField"
	private TextField dragAreaTextField; // Value injected by FXMLLoader
	
	@FXML // fx:id="parasiticDrivingTextField"
	private TextField parasiticDrivingTextField; // Value injected by FXMLLoader
	
	
	@FXML
	void saveCarConfig(ActionEvent event) {
		//TODO Aaron: update left side labels when user presses "save" button
		if(validateInt(dragAreaTextField.textProperty())) {
			dragAreaLabel.textProperty().setValue(dragAreaTextField.textProperty().getValue());
			currentConfig.setProperty("dragArea", dragAreaTextField.textProperty().getValue());
		}
		
		if(validateInt(peukertTextField.textProperty())) {
			peukertLabel.textProperty().setValue(peukertTextField.textProperty().getValue());
		}
		
		if(validateInt(motorEfficiencyTextField.textProperty())) {
			motorEfficiencyLabel.textProperty().setValue(motorEfficiencyTextField.textProperty().getValue());
		}
		
		if(validateInt(parasiticChargingTextField.textProperty())) {
			parasiticChargingLabel.textProperty().setValue(parasiticChargingTextField.textProperty().getValue());
		}
		
		if(validateInt(parasiticDrivingTextField.textProperty())) {
			parasiticDrivingLabel.textProperty().setValue(parasiticDrivingTextField.textProperty().getValue());
		}
		
		if(validateInt(rollingTextField.textProperty())) {
			rollingLabel.textProperty().setValue(rollingTextField.textProperty().getValue());
		}
		
		if(validateInt(solarMaxPowerTextField.textProperty())) {
			solarMaxPowerLabel.textProperty().setValue(solarMaxPowerTextField.textProperty().getValue());
		}
		
		if(validateInt(solarChargingTextField.textProperty())) {
			solarChargingLabel.textProperty().setValue(solarChargingTextField.textProperty().getValue());
		}
		
		if(validateInt(solarDrivingTextField.textProperty())) {
			solarDrivingLabel.textProperty().setValue(solarDrivingTextField.textProperty().getValue());
		}
		
		CarConfig.saveCarConfig();
	}
	
	@FXML
		// This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert solarChargingTextField != null : "fx:id=\"solarChargingTextField\" was not injected: check your FXML file 'configEditor.fxml'.";
		assert solarDrivingTextField != null : "fx:id=\"solarDrivingTextField\" was not injected: check your FXML file 'configEditor.fxml'.";
		assert solarMaxPowerTextField != null : "fx:id=\"solarMaxPowerTextField\" was not injected: check your FXML file 'configEditor.fxml'.";
		assert batteryCapacityTextField != null : "fx:id=\"batteryCapacityTextField\" was not injected: check your FXML file 'configEditor.fxml'.";
		assert rollingTextField != null : "fx:id=\"rollingTextField\" was not injected: check your FXML file 'configEditor.fxml'.";
		assert parasiticChargingTextField != null : "fx:id=\"parasiticChargingTextField\" was not injected: check your FXML file 'configEditor.fxml'.";
		assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'configEditor.fxml'.";
		assert peukertTextField != null : "fx:id=\"peukertTextField\" was not injected: check your FXML file 'configEditor.fxml'.";
		assert dragAreaTextField != null : "fx:id=\"dragAreaTextField\" was not injected: check your FXML file 'configEditor.fxml'.";
		assert parasiticDrivingTextField != null : "fx:id=\"parasiticDrivingTextField\" was not injected: check your FXML file 'configEditor.fxml'.";
		
		//load current config and display on left side
		currentConfig = new Properties();
		currentConfig = CarConfig.loadCarConfig();
		
		//region initialize left side values to current config values
		
		dragAreaLabel.textProperty().setValue(currentConfig.getProperty("dragArea"));
		//batteryCapacityLabel.textProperty().setValue(currentConfig.getProperty("batteryC"));
		peukertLabel.textProperty().setValue(currentConfig.getProperty("peukert"));
		motorEfficiencyLabel.textProperty().setValue(currentConfig.getProperty("motorEfficiency"));
		parasiticChargingLabel.textProperty().setValue(currentConfig.getProperty("parasiticCharging"));
		parasiticDrivingLabel.textProperty().setValue(currentConfig.getProperty("parasiticDriving"));
		rollingLabel.textProperty().setValue(currentConfig.getProperty("rollingCoefficient"));
		solarMaxPowerLabel.textProperty().setValue(currentConfig.getProperty("batteryMaxPower"));
		solarChargingLabel.textProperty().setValue(currentConfig.getProperty("batteryCharging"));
		solarDrivingLabel.textProperty().setValue(currentConfig.getProperty("batteryDriving"));
		
		
		//endregion
		
		
		//region TextField handlers
		
		
		//endregion
	}
	
	private void updateLabels() {
		
	}
	
	/**
	 * @param text
	 * @return true if text is integer, false if not
	 */
	private boolean validateInt(StringProperty text) {
		
		
		String s = text.getValue().trim();
		System.out.println("-> " + s + " <-");
		return (s.matches("[0-9]+"));
		
		
	}
}
