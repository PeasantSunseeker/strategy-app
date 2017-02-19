package models;

public class Aerodynamic {
	static double dragArea = 0.12;
	static double unitConversion = 0.0125;
	
	public static double aerodynamicPower(double velocity){
		double aerodynamicPower = unitConversion * Math.pow(velocity, 3) * dragArea;
		return aerodynamicPower;
	}
	
	public static double aerodynamicPowerWind(double velocity, double heading, double windVelocity, double windDirection){
		double deltaHeading = Math.abs(windDirection-heading);
//		System.out.println(deltaHeading);
		double windImpact = Math.cos(Math.toRadians(deltaHeading));
//		System.out.println(windImpact);
//		System.out.println(windImpact * windVelocity);
		velocity += windImpact * windVelocity;
//		System.out.println(velocity);
		return aerodynamicPower(velocity);
	}
	
	public static void main(String[] args){
		double temp = aerodynamicPowerWind(50,180,50, 180);
		System.out.println(temp);
	}
}
