package config;

import java.io.*;
import java.util.Properties;

/**
 * PROJECT: seniordesign
 * AUTHOR: aaron  2/8/2017.
 * DATE: 2/8/2017
 *
 * DESCRIPTION:
 *
 *
 * INPUTS: values for each part of car config
 *
 *
 * OUTPUTS: config.properties file containing the car config values
 */
public class CarConfig {
	
	/********** Aerodynamic **********/
	private static double dragArea;
	
	/********** Battery **********/
	static double batteryC;
	private static double peukert;
	
	/********** Motor **********/
	private static double motorEfficiency;
	
	/********** Parasitic **********/
	private static double parasiticCharging;
	private static double parasiticDriving;
	
	/********** Rolling **********/
	private static double rollingCoefficient;
	
	/********** Solar **********/
	private static double solarMaxPower;
	private static double solarCharging;
	private static double solarDriving;
	
	/********** Misc **********/
	private static double carWeight;
	
	public CarConfig(double dragArea, double batteryC, double peukert, double motorEfficiency,
					 double parasiticCharging, double parasiticDriving, double rollingCoefficient,
					 double solarMaxPower, double solarCharging, double solarDriving, double carWeight) {
		
		this.dragArea = dragArea;
		
		this.batteryC = batteryC;
		this.peukert = peukert;
		this.motorEfficiency = motorEfficiency;
		this.parasiticCharging = parasiticCharging;
		this.parasiticDriving = parasiticDriving;
		this.rollingCoefficient = rollingCoefficient;
		this.solarMaxPower = solarMaxPower;
		this.solarCharging = solarCharging;
		this.solarDriving = solarDriving;
		this.carWeight = carWeight;
	}
	
	public static void main(String[] args) {
		CarConfig c = new CarConfig(0.12, 8078, 1.08, 0.94,
				10, 30, 0.0055, 1300, 0.3, 1.3, 2700);
		c.saveCarConfig();
		c.loadCarConfig();
		c.printConfig();
	}
	
	public static void saveCarConfig() {
		
		Properties properties = new Properties();
		File configFile = new File("config.properties");
		FileOutputStream output = null;
		try {
			output = new FileOutputStream(configFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		try {
			
			properties.setProperty("dragArea", Double.toString(dragArea));
			properties.setProperty("peukert", Double.toString(peukert));
			properties.setProperty("motorEfficiency", Double.toString(motorEfficiency));
			properties.setProperty("parasiticCharging", Double.toString(parasiticCharging));
			properties.setProperty("parasiticDriving", Double.toString(parasiticDriving));
			properties.setProperty("rollingCoefficient", Double.toString(rollingCoefficient));
			properties.setProperty("solarMaxPower", Double.toString(solarMaxPower));
			properties.setProperty("solarCharging", Double.toString(solarCharging));
			properties.setProperty("solarDriving", Double.toString(solarDriving));
			properties.setProperty("batteryC", Double.toString(batteryC));
			properties.setProperty("carWeight", Double.toString(carWeight));
			
			properties.store(output, null);
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
	}
	
	public static Properties loadCarConfig() {
		Properties prop = new Properties();
		InputStream input = null;
		
		try {
			
			input = new FileInputStream("config.properties");
			
			// load properties file
			prop.load(input);
			
			// get the property value and put it in memory
			dragArea = Double.parseDouble(prop.getProperty("dragArea"));
			peukert = Double.parseDouble(prop.getProperty("peukert"));
			motorEfficiency = Double.parseDouble(prop.getProperty("motorEfficiency"));
			parasiticCharging = Double.parseDouble(prop.getProperty("parasiticCharging"));
			parasiticDriving = Double.parseDouble(prop.getProperty("parasiticDriving"));
			rollingCoefficient = Double.parseDouble(prop.getProperty("rollingCoefficient"));
			solarMaxPower = Double.parseDouble(prop.getProperty("solarMaxPower"));
			solarCharging = Double.parseDouble(prop.getProperty("solarCharging"));
			solarDriving = Double.parseDouble(prop.getProperty("solarDriving"));
			batteryC = Double.parseDouble(prop.getProperty("batteryC"));
			carWeight = Double.parseDouble(prop.getProperty("carWeight"));
			
			
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			
		}
		
		return prop;
		
		
	}
	
	
	//region Getters and Setters
	public static double getDragArea() {
		return dragArea;
	}
	
	public static void setDragArea(double dragArea) {
		CarConfig.dragArea = dragArea;
	}
	
	public static double getPeukert() {
		return peukert;
	}
	
	public static void setPeukert(double peukert) {
		CarConfig.peukert = peukert;
	}
	
	public static double getMotorEfficiency() {
		return motorEfficiency;
	}
	
	public static void setMotorEfficiency(double motorEfficiency) {
		CarConfig.motorEfficiency = motorEfficiency;
	}
	
	public static double getParasiticCharging() {
		return parasiticCharging;
	}
	
	public static void setParasiticCharging(double parasiticCharging) {
		CarConfig.parasiticCharging = parasiticCharging;
	}
	
	public static double getParasiticDriving() {
		return parasiticDriving;
	}
	
	public static void setParasiticDriving(double parasiticDriving) {
		CarConfig.parasiticDriving = parasiticDriving;
	}
	
	public static double getRollingCoefficient() {
		return rollingCoefficient;
	}
	
	public static void setRollingCoefficient(double rollingCoefficient) {
		CarConfig.rollingCoefficient = rollingCoefficient;
	}
	
	public static double getSolarMaxPower() {
		return solarMaxPower;
	}
	
	public static void setSolarMaxPower(double solarMaxPower) {
		CarConfig.solarMaxPower = solarMaxPower;
	}
	
	public static double getSolarCharging() {
		return solarCharging;
	}
	
	public static void setSolarCharging(double solarCharging) {
		CarConfig.solarCharging = solarCharging;
	}
	
	public static double getSolarDriving() {
		return solarDriving;
	}
	
	public static void setSolarDriving(double solarDriving) {
		CarConfig.solarDriving = solarDriving;
	}
	
	public static double getBatteryC() {
		return batteryC;
	}
	
	public static void setBatteryC(double batteryC) {
		CarConfig.batteryC = batteryC;
	}
	
	public static double getCarWeight() {
		return carWeight;
	}
	
	public static void setCarWeight(double carWeight) {
		CarConfig.carWeight = carWeight;
	}
	//endregion
	
	/**
	 * For debugging
	 */
	public static void printConfig() {
		System.out.println("dragArea=" + dragArea);
		System.out.println("peukert=" + peukert);
		System.out.println("motorEfficiency=" + motorEfficiency);
		System.out.println("parasiticCharging=" + parasiticCharging);
		System.out.println("parasiticDriving=" + parasiticDriving);
		System.out.println("rollingCoefficient=" + rollingCoefficient);
		System.out.println("solarMaxPower=" + solarMaxPower);
		System.out.println("solarCharging=" + solarCharging);
		System.out.println("solarDriving=" + solarDriving);
		System.out.println("batteryC=" + batteryC);
		System.out.println("carWeight=" + carWeight);
	}
}
