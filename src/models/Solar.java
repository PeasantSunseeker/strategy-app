package models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Solar {
	static double sunrise = 6.6167;
	static double dayLength = 12.6167;
//	static double noonAngle = 22;
	static double maxPower = 1300;
	static double charging = 0.3;
	static double driving = 1.3;
	
	public static double solarPower(int dayOfYear, double time, double latitude) {
		return maxPower * Math.pow(Math.cos(Math.toRadians(getAngle(dayOfYear, time, latitude))), driving);
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
}
