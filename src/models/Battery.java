package models;

public class Battery {
	static double batteryC = 8078;
	static double n = 1.08; //Peukert Number
	
	public static double getCapacity(double powerDraw){
		return batteryC / Math.pow( powerDraw, (n - 1) );
	}
}
