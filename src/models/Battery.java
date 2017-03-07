package models;

import config.CarConfig;

public class Battery {
	static double batteryC = CarConfig.getBatteryC();
	static double peukert = CarConfig.getPeukert();
	
	public static double getCapacity(double powerDraw) {
		return batteryC / Math.pow(powerDraw, (peukert - 1));
	}
}
