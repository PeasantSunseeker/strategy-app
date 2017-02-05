package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.*;
import utilities.MasterData;
import utilities.Position;

import java.util.ArrayList;
import java.util.List;

public class Data {

    public static ObservableList<MasterData> getHourlyData() {
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

        //List<String> data = new ArrayList<>();
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
			myData.setStartTime(time);
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
	
	public static ObservableList<MasterData> getData(){
    	Position[] positions = Position.loadPositions("leg-1-10_items");
		
		double weight = 2700; // Newtons
		double totalPower;
		double start = 9;
		double end = 17;
		double totalCharge = 0;
		int index;
		double totalDistance = 0;
		double previous;
		
		List<MasterData> rowData = new ArrayList<MasterData>();

		System.out.format("%5s | %5s | %6s | %6s | %5s | %5s | %4s | %6s | %5s | %5s | %5s | %8s | %5s | %5s\n",
				"Distance", "Angle", "Speed", "Grav", "Kin", "Aero", "Roll", "Total", "Start", "Stop",
				"Solar", "Battery", "Batt Chg", "Tot Chg");

		for (index = 1; index < positions.length; index++) {
			Position pos = positions[index];
			Position prev = positions[index - 1];
			double distance = Position.getDistance(prev, pos);
			float currentVelocity = 80;//pos.getVelocity();
			float previousVelocity = 80;//prev.getVelocity();
			
			previous = start;
//			double averageGrade = (pos.getGrade() + prev.getGrade()) / 2;
			double averageVelocity = (currentVelocity + previousVelocity) / 2;

//			double roadAngle = Gravitational.getRoadAngle(averageGrade);
			double roadAngle = Position.calculateAngle(prev, pos);
			double gravPower = Gravitational.gravityPower(averageVelocity, weight, roadAngle);
			double kineticPower = Gravitational.kineticPower(currentVelocity, previousVelocity, distance, weight);
			double deltaTime = (distance) / averageVelocity;
			
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
			totalDistance += distance;
			
			String segmentStartTime = String.format("%02.0f:%02.0f", Math.floor(previous), previous%1*60);
			String segmentStopTime = String.format("%02.0f:%02.0f", Math.floor(previous + deltaTime), (previous + deltaTime)%1*60);
			
			System.out.format("%8.2f | %5.1f | %6.1f | %6.0f | %5.0f | %5.0f | %4.0f | %6.0f | %s | %s | %5.0f | %8.1f | %8.2f | %7.2f\n",
					totalDistance, roadAngle, averageVelocity, gravPower, kineticPower, aeroPower, rollingPower, totalPower, segmentStartTime, segmentStopTime,
					solarPower, batteryPower, batteryCharge, totalCharge);
			
			MasterData myData = new MasterData();
			myData.setStartTime(previous);
			myData.setEndTime(previous + deltaTime);
			myData.setBatteryCharge(batteryCharge);
			myData.setTotalCharge(totalCharge);
			myData.setDistance(totalDistance);
			myData.setVelocity(averageVelocity);
			myData.setRoadAngle(roadAngle);
			rowData.add(myData);
		}
		ObservableList<MasterData> returnData = FXCollections.observableArrayList(rowData);
		
		return returnData;
	}
}
