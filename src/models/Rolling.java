package models;

import config.CarConfig;

public class Rolling {
	static double rollingCoefficient = CarConfig.getRollingCoefficient();
	static double unitConversion = 0.278;
	
	public static double rollingPower(double velocity, double weight) {
		double rollingPower = unitConversion * rollingCoefficient * (1 + (velocity / 161)) * weight * velocity;
		return rollingPower;
	}
}
