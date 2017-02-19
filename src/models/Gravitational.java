package models;

public class Gravitational {
	static double unitConversion = 0.278;
	
	public static double gravityPower(double velocity, double weight, double roadAngleDegrees) {
		return unitConversion * weight * velocity * Math.sin(Math.toRadians(roadAngleDegrees));
	}
	
	public static double kineticPower(double velocityCurrent, double velocityPrevious, double distance, double weight) {
		double enumerator = (Math.pow(velocityCurrent, 2) - Math.pow(velocityPrevious, 2)) * (velocityCurrent + velocityPrevious);
		double denominator = distance;
		return (5.46e-7) * (enumerator / denominator) * weight;
	}
	
	public static double getRoadAngle(double gradePercent) {
		return Math.toDegrees(Math.atan(gradePercent / 100));
	}
}
