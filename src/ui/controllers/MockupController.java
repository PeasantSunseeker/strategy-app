package ui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;

/**
 * PROJECT: seniordesign
 * AUTHOR: aaron  2/22/2017.
 * DATE: 2/22/2017
 *
 * DESCRIPTION:
 *
 *
 * INPUTS:
 *
 *
 * OUTPUTS:
 */
public class MockupController {
	
	private StringProperty clouds;
	private StringProperty shade;
	private StringProperty speedLimit;
	
	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;
	
	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;
	
	@FXML // fx:id="currentSpeedLimit"
	private Label currentSpeedLimit; // Value injected by FXMLLoader
	
	@FXML // fx:id="tablePane"
	private StackPane tablePane; // Value injected by FXMLLoader
	
	@FXML // fx:id="cloudPctOverride"
	private TextField cloudPctOverride; // Value injected by FXMLLoader
	
	@FXML // fx:id="currentCloudPct"
	private Label currentCloudPct; // Value injected by FXMLLoader
	
	@FXML // fx:id="shadePctOverride"
	private TextField shadePctOverride; // Value injected by FXMLLoader
	
	@FXML // fx:id="currentShadePct"
	private Label currentShadePct; // Value injected by FXMLLoader
	
	@FXML // fx:id="table"
	private TableView<?> table; // Value injected by FXMLLoader
	
	@FXML // fx:id="speedLimitOverride"
	private TextField speedLimitOverride; // Value injected by FXMLLoader
	
	@FXML
	void overrideClouds(ActionEvent event) {
		
		
	}
	
	@FXML
	void overrideShade(ActionEvent event) {
		
	}
	
	@FXML
	void overrideSpeedLimit(ActionEvent event) {
		
	}
	
	@FXML
		// This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert currentSpeedLimit != null : "fx:id=\"currentSpeedLimit\" was not injected: check your FXML file 'mockup.fxml'.";
		assert tablePane != null : "fx:id=\"tablePane\" was not injected: check your FXML file 'mockup.fxml'.";
		assert cloudPctOverride != null : "fx:id=\"cloudPctOverride\" was not injected: check your FXML file 'mockup.fxml'.";
		assert currentCloudPct != null : "fx:id=\"currentCloudPct\" was not injected: check your FXML file 'mockup.fxml'.";
		assert shadePctOverride != null : "fx:id=\"shadePctOverride\" was not injected: check your FXML file 'mockup.fxml'.";
		assert currentShadePct != null : "fx:id=\"currentShadePct\" was not injected: check your FXML file 'mockup.fxml'.";
		assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'mockup.fxml'.";
		assert speedLimitOverride != null : "fx:id=\"speedLimitOverride\" was not injected: check your FXML file 'mockup.fxml'.";
		
		
		cloudPctOverride.setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				
				System.out.println("override cloud percentage");
				
				if (validatePercentage(cloudPctOverride.textProperty())) {
					clouds = cloudPctOverride.textProperty();
					currentCloudPct.textProperty().setValue(clouds.getValue() + "%");
					
				}
				
				cloudPctOverride.clear();
				
				
			} else {
				
			}
		});
		
		shadePctOverride.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				
				System.out.println("override shade percentage");
				
				if (validatePercentage(shadePctOverride.textProperty())) {
					shade = shadePctOverride.textProperty();
					currentShadePct.textProperty().setValue(shade.getValue() + "%");
					
					
				} else {
					System.out.println("Not a valid number");
				}
				
				
				shadePctOverride.clear();
			} else {
				
			}
			
			
		});
		
		speedLimitOverride.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				
				System.out.println("override speed limit");
				
				if (validatePercentage(speedLimitOverride.textProperty())) {
					speedLimit = speedLimitOverride.textProperty();
					currentSpeedLimit.textProperty().setValue(speedLimit.getValue());
					
				} else {
					System.out.println("Not a valid number");
				}
				
				speedLimitOverride.clear();
				
				
			} else {
				
			}
			
			
		});
	}
	
	
	/**
	 * @param text
	 * @return true if text is integer, false if not
	 */
	private boolean validatePercentage(StringProperty text) {
		String s = text.getValue().trim();
		System.out.println("-> " + s + " <-");
		return (s.matches("[0-9]*") && Integer.valueOf(s) >= 0 && Integer.valueOf(s) <= 100);
	}
	
	private boolean validateSpeedLimit(StringProperty text) {
		String s = text.getValue().trim();
		System.out.println("-> " + s + " <-");
		return (s.matches("[0-9]*") && Integer.valueOf(s) >= 0);
	}
}
