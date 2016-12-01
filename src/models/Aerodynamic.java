package models;

public class Aerodynamic {
	static double dragArea = 0.12;
	static double unitConversion = 0.0125;
	
	public static double aerodynamicPower(double velocity){
		double aerodynamicPower = unitConversion * Math.pow(velocity, 3) * dragArea;
		return aerodynamicPower;
	}
}
