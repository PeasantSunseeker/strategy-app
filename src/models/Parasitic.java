package models;

public class Parasitic {
	static double charging = 10;
	static double driving = 30;
	
	public static double getPowerLossCharging(){
		return charging;
	}
	public static double getPowerLossDriving(){
		return driving;
	}
}
