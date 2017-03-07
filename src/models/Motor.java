package models;

import config.CarConfig;

public class Motor {
	static double efficiency = CarConfig.getMotorEfficiency();
	
	public static double getEfficiency() {
		return efficiency;
	}
}
