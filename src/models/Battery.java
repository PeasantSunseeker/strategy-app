package models;

import config.CarConfig;

public class Battery {
	static double batteryC = CarConfig.getBatteryCapacity();
//	static double peukert = CarConfig.getPeukert();
	static double batterCapacity = CarConfig.getBatteryCapacity();
	
	public static double getCapacity(double powerDraw) {
//		return batteryC / Math.pow(powerDraw, (peukert - 1));
		return batterCapacity;
	}
}
