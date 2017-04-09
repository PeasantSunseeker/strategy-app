package models;

import config.CarConfig;

public class Solar {
	static double sunrise = 6.6167;
	static double dayLength = 12.6167;
	//	static double noonAngle = 22;
	static double maxPower = CarConfig.getSolarMaxPower();
	static double charging = CarConfig.getSolarCharging();
	static double driving = CarConfig.getSolarDriving();
	
	public static double solarPower(int dayOfYear, double time, double latitude, double cloudCover) {
		return maxPower * Math.pow(Math.cos(Math.toRadians(getAngle(dayOfYear, time, latitude))), driving) * (1 - cloudCover);
	}
	
	public static double getAngle(int dayOfYear, double time, double latitude) {
		double noonAngle = sunNoonAngle(dayOfYear, latitude);
		return 90 - (90 - noonAngle) * Math.sin(Math.toRadians((180 * (time - sunrise)) / dayLength));
	}
	
	public static double sunNoonAngle(int dayOfYear, double latitude) {
//		System.out.println(dayOfYear);
		double noonAngle = latitude - 23.5 * Math.sin(Math.toRadians((180 * (dayOfYear - 82)) / 182.5));
//		System.out.println(noonAngle);
		return noonAngle;
	}
	
	public static void setDayLength(double rise, double set) {
		sunrise = rise;
		dayLength = set - rise;
//		System.out.printf("Sunrise: %2.4f\n", rise);
//		System.out.printf("Sunset: %2.4f\n", set);
//		System.out.printf("Daylength: %2.4f\n", dayLength);
	}
}
