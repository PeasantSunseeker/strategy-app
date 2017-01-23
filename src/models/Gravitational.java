package models;

public class Gravitational {
	static double unitConversion = 0.278;
	
	public static double gravityPower(double velocity, double weight, double roadAngle){
		return unitConversion * weight * velocity * Math.sin(roadAngle);
	}
	
	public static double kineticPower(double velocityCurrent, double velocityPrevious, double distanceCurrent, double distancePrevious, double weight){
		double enumerator = ( Math.pow(velocityCurrent, 2) - Math.pow(velocityPrevious, 2) ) * ( velocityCurrent + velocityPrevious );
		double denominator = distanceCurrent - distancePrevious;
		return (5.46e-7) * (enumerator / denominator) * weight;
	}
	
	public static double getRoadAngle(double grade){
		return Math.atan(grade/100);
	}
}
