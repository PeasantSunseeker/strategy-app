package models;

import config.CarConfig;

public class Parasitic {
	static double charging = CarConfig.getParasiticCharging();
	static double driving = CarConfig.getParasiticDriving();
	
	public static double getPowerLossCharging() {
		return charging;
	}

	public static double getPowerLossDriving() {
		return driving;
	}
}
