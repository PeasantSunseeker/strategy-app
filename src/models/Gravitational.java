package models;

public class Gravitational {
	static double unitConversion = 0.278;
	
	public static double gravityPower(double velocity, double weight, double roadAngle){
		return unitConversion * weight * velocity * Math.sin(roadAngle);
	}
}
