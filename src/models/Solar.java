package models;

public class Solar {
	static double sunrise = 6.6167;
	static double dayLength = 12.6167;
	static double noonAngle = 22;
	static double maxPower = 1300;
	static double charging = 0.3;
	static double driving = 1.3;
	
	public static double solarPower(double time){
		return maxPower * Math.pow(Math.cos(Math.toRadians(getAngle(time))), driving);
	}
	
	public static double getAngle(double time){
		return 90 - (90 - noonAngle) * Math.sin(Math.toRadians((180 * (time - sunrise)) / dayLength));
	}
}
