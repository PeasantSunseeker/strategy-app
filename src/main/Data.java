package main;

import models.*;

import java.util.ArrayList;
import java.util.List;

public class Data {

    public static String[][] getData() {
        double velocity = 80; // km/h
        double weight = 2700; // Newtons

        double aeroPower = Aerodynamic.aerodynamicPower(velocity);
//		System.out.format("Aero Power: %.0f\n", aeroPower);

        double rollingPower = Rolling.rollingPower(velocity, weight);
//		rollingPower = Math.round(rollingPower);
//		System.out.format("Rolling Power: %.0f\n", rollingPower);

        double totalPower = (aeroPower + rollingPower) / Motor.getEfficiency() + Parasitic.getPowerLossDriving();
//		totalPower = Math.round(totalPower);
//		System.out.format("Total Power: %.0f\n", totalPower);

        double start = 9;
        double end = 17;
        double periodSize = 0.5;
        double totalCharge = 0;
        double distance = 0;

        List<String[]> data = new ArrayList<>();
        int listCount = -1;
        int columns = 11;
    
//        System.out.format("%5s | %5s | %6s | %3s | %5s | %6s | %5s | %5s | %4s | %5s | %3s\n", "Time", "Angle", "Solar", "Aero", "Roll", "Total", "Battery", "Batt Cap", "Batt Chg", "Tot Chg", "Distance");
        
        for (double time = start; time < end; time += periodSize) {
            data.add(new String[columns]);
            listCount++;

            double middleTime = time + (periodSize / 2);
            double sunAngle = Solar.getAngle(middleTime);
            double solarPower = Solar.solarPower(middleTime);
            double batteryPower = totalPower - solarPower;
            double batteryCap = Battery.getCapacity(batteryPower);
            double batteryCharge = batteryPower * periodSize / batteryCap * 100;
            batteryCharge *= -1;
            totalCharge += batteryCharge;
            distance += velocity * periodSize;

            //TODO Make data list dynamic
            data.get(listCount)[0] = String.format("%2.0f:%2.0f", middleTime, middleTime%1*60);
            data.get(listCount)[1] = String.format("%5.2f", sunAngle);
            data.get(listCount)[2] = String.format("%6.1f", solarPower);
            data.get(listCount)[3] = String.format("%4.0f", aeroPower);
            data.get(listCount)[4] = String.format("%5.1f", rollingPower);
            data.get(listCount)[5] = String.format("%5.1f", totalPower);
            data.get(listCount)[6] = String.format("%7.1f", batteryPower);
            data.get(listCount)[7] = String.format("%8.2f", batteryCap);
            data.get(listCount)[8] = String.format("%8.2f", batteryCharge);
            data.get(listCount)[9] = String.format("%7.2f", totalCharge);
            data.get(listCount)[10] = String.format("%6.0f", distance);

//            System.out.format("%5.2f | %5.2f | %6.1f | %4.0f | %5.1f | %5.1f | %7.1f | %8.2f | %8.2f | %7.2f | %6.0fkm\n",
//                    middleTime, sunAngle, solarPower, aeroPower, rollingPower, totalPower, batteryPower, batteryCap, batteryCharge, totalCharge, distance);
        }

        String[][] returnData = new String[data.size()][columns];

        for (int i = 0; i < data.size(); i++) {
            returnData[i] = data.get(i);
        }

        return returnData;
    }
	
	public static void main(String[] args){
    	double distance[] = { 0,  1,  2,  3,  4,  5,  6,  7,   8,    9};
    	double grade[] =    { 0,  2,  2,  2,  2, -4, -4, -4,  -4,   -4};
    	double velocity[] = {60, 60, 60, 60, 60, 60, 60, 60,  60,   60};
    	
//		double velocity = 80; // km/h
		double weight = 2700; // Newtons

		double totalPower;
				
		double start = 12;
		double end = 17;
		double totalCharge = 0;
		int index;
//		double distance = 0;
		double previous;
		

		System.out.format("%5s | %5s | %6s | %6s | %5s | %5s | %4s | %6s | %5s | %5s | %5s | %8s | %5s | %5s\n",
				"Distance", "Angle", "Speed", "Grav", "Kin", "Aero", "Roll", "Total", "Start", "Stop",
				"Solar", "Battery", "Batt Chg", "Tot Chg");

		for (index = 1; index < distance.length; index++) {
			previous = start;
			double averageGrade = (grade[index] + grade[index - 1]) / 2;
			double averageVelocity = (velocity[index] + velocity[index - 1]) / 2;
			
//			double deltaDistance = distance[index] - distance[index - 1];
			double roadAngle = Gravitational.getRoadAngle(averageGrade);
			double gravPower = Gravitational.gravityPower(averageVelocity, weight, roadAngle);
			double kineticPower = Gravitational.kineticPower(velocity[index], velocity[index-1], distance[index], distance[index-1], weight);
			double deltaTime = (distance[index] - distance[index - 1]) / averageVelocity;
			
			double middleTime = previous + (deltaTime / 2);
			double sunAngle = Solar.getAngle(middleTime);
			double solarPower = Solar.solarPower(middleTime);
			double aeroPower = Aerodynamic.aerodynamicPower(averageVelocity);
			double rollingPower = Rolling.rollingPower(averageVelocity, weight);
			totalPower = (gravPower + kineticPower + aeroPower + rollingPower) / Motor.getEfficiency() + Parasitic.getPowerLossDriving();
			double batteryPower = totalPower - solarPower;
			double batteryCap = Battery.getCapacity(batteryPower);
			double batteryCharge = batteryPower * deltaTime / batteryCap * 100;
			batteryCharge *= -1;
			totalCharge += batteryCharge;
			start += deltaTime;

			System.out.format("%8.2f | %5.1f | %6.1f | %6.0f | %5.0f | %5.0f | %4.0f | %6.0f | %5.2f | %5.2f | %5.0f | %8.1f | %8.2f | %7.2f\n",
					distance[index], roadAngle, averageVelocity, gravPower, kineticPower, aeroPower, rollingPower, totalPower, previous, (previous + deltaTime),
					solarPower, batteryPower, batteryCharge, totalCharge);
		}
	}
}
