package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.*;
import utilities.MasterData;

import java.util.ArrayList;
import java.util.List;

public class Data {

    public static ObservableList<MasterData> getData() {
        double velocity = 80; // km/h
        double weight = 2700; // Newtons

        double aeroPower = Aerodynamic.aerodynamicPower(velocity);
        double rollingPower = Rolling.rollingPower(velocity, weight);
        double totalPower = (aeroPower + rollingPower) / Motor.getEfficiency() + Parasitic.getPowerLossDriving();

        double start = 9;
        double end = 17;
        double periodSize = 0.5;
        double totalCharge = 0;
        double distance = 0;

        List<String> data = new ArrayList<>();
        int listCount = -1;
        int columns = 11;
		
		List<MasterData> rowData = new ArrayList<MasterData>();
    
//        System.out.format("%5s | %5s | %6s | %3s | %5s | %6s | %5s | %5s | %4s | %5s | %3s\n", "Time", "Angle", "Solar", "Aero", "Roll", "Total", "Battery", "Batt Cap", "Batt Chg", "Tot Chg", "Distance");
        
        for (double time = start; time < end; time += periodSize) {
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
			
			MasterData myData = new MasterData();
			myData.setMiddleTime(middleTime);
			myData.setBatteryCharge(batteryCharge);
			myData.setTotalCharge(totalCharge);
			myData.setDistance(distance);
            rowData.add(myData);
            

//            System.out.format("%5.2f | %5.2f | %6.1f | %4.0f | %5.1f | %5.1f | %7.1f | %8.2f | %8.2f | %7.2f | %6.0fkm\n",
//                    middleTime, sunAngle, solarPower, aeroPower, rollingPower, totalPower, batteryPower, batteryCap, batteryCharge, totalCharge, distance);
        }

		ObservableList<MasterData> returnData = FXCollections.observableArrayList(rowData);

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
