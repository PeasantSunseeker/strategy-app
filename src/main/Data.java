package main;

import config.CarConfig;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.*;
import utilities.MasterData;
import utilities.Position;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Data {
	static boolean debug = false;
	
	public static void main(String[] args) {
		CarConfig.loadCarConfig();
		getData(20);
	}
	
	public static ObservableList<MasterData> getHourlyData() {
		double velocity = 80; // km/h
		double weight = CarConfig.getCarWeight(); // Newtons
		
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
		
		int dayOfYear = LocalDate.now().getDayOfYear();
		
		for (double time = start; time < end; time += periodSize) {
			listCount++;
			
			double middleTime = time + (periodSize / 2);
			double sunAngle = Solar.getAngle(dayOfYear, middleTime, 37);
			//TODO Update cloud cover
			double solarPower = Solar.solarPower(dayOfYear, middleTime, 37, 0);
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
	
	public static List<MasterData> optimizeRun(Position[] positions, double endingEnergy){
		double weight = CarConfig.getCarWeight(); // Newtons
		double totalPower; // Segment power usage
		double startTime; // Start time
		double endTime = 17; // End time
		double totalBatteryCharge; // Total battery charge %
		int index;
		double totalDistance; // Total run distance
		double previousTime;
		int dayOfYear = LocalDate.now().getDayOfYear();
		double batteryCapacity;
		float speedGuess = 90;
		float currentVelocity, previousVelocity;
		double finalEnergy = 100;
		boolean speedModified = true;
		
		List<MasterData> rowData = new ArrayList<MasterData>();
		
		while(!equalTolerance(finalEnergy, endingEnergy, 2) && speedModified) {
			//Reset run calculations
			speedModified = false;
			totalBatteryCharge = 100;
			startTime = 9;
			totalDistance = 0;
			rowData = new ArrayList<MasterData>();
			
			if(debug) {
				System.out.format("Speed Guess: %f\n", speedGuess);
				System.out.format("%5s | %5s | %6s | %6s | %5s | %5s | %4s | %6s | %5s | %5s | %5s | %8s | %5s | %5s | %5s\n",
						"Distance", "Angle", "Speed", "Grav", "Kin", "Aero", "Roll", "Total", "Start", "Stop",
						"Solar", "Batt Pow", "Batt Cap", "Batt Change", "Tot Chg");
			}
			
			// Loop through positions
			for (index = 1; index < positions.length; index++) {
				Position pos = positions[index];
				Position prev = positions[index - 1];
				double distance = Position.getDistance(prev, pos);
				
				currentVelocity = pos.getVelocity();
				if (currentVelocity > speedGuess) {
					currentVelocity = speedGuess;
					speedModified = true;
				}
				previousVelocity = prev.getVelocity();
				if (previousVelocity > speedGuess) {
					previousVelocity = speedGuess;
					speedModified = true;
				}
				
				previousTime = startTime;
//			double averageGrade = (pos.getGrade() + prev.getGrade()) / 2;
				double averageVelocity = (currentVelocity + previousVelocity) / 2;

//			double roadAngle = Gravitational.getRoadAngle(averageGrade);
				double roadAngle = prev.getAngle();//Position.calculateAngle(prev, pos);
				double gravPower = Gravitational.gravityPower(averageVelocity, weight, roadAngle);
				double kineticPower = Gravitational.kineticPower(currentVelocity, previousVelocity, distance, weight);
				double deltaTime = (distance) / averageVelocity;
				
				double middleTime = previousTime + (deltaTime / 2);
				double sunAngle = Solar.getAngle(dayOfYear, middleTime, pos.getLatitude());
				//TODO Brodie: update solar power to use cloud cover when available
				double solarPower = Solar.solarPower(dayOfYear, middleTime, pos.getLatitude(), 0);
				//TODO Brodie: update aerodynamic power to use wind when available
				double aeroPower = Aerodynamic.aerodynamicPower(averageVelocity);
				double rollingPower = Rolling.rollingPower(averageVelocity, weight);
				totalPower = (gravPower + kineticPower + aeroPower + rollingPower) / Motor.getEfficiency() + Parasitic.getPowerLossDriving();
				double batteryPower = totalPower - solarPower;
				
				if (batteryPower < 0) {
					// TODO Brodie: insert charge efficiency
					batteryCapacity = Battery.getCapacity(batteryPower * -1);
				} else {
					batteryCapacity = Battery.getCapacity(batteryPower);
				}
				
				double batteryCharge = batteryPower * deltaTime / batteryCapacity * 100;
				batteryCharge *= -1;
				totalBatteryCharge += batteryCharge;
				startTime += deltaTime;
				totalDistance += distance;
				
				String segmentStartTime = String.format("%02.0f:%02.0f", Math.floor(previousTime), previousTime % 1 * 60);
				String segmentStopTime = String.format("%02.0f:%02.0f", Math.floor(previousTime + deltaTime), (previousTime + deltaTime) % 1 * 60);
				
				if(debug) {
					System.out.format("%8.2f | %5.1f | %6.1f | %6.0f | %5.0f | %5.0f | %4.0f | %6.0f | %s | %s | %5.0f | %8.1f | %8.1f | %11.2f | %7.2f\n",
							totalDistance, roadAngle, averageVelocity, gravPower, kineticPower, aeroPower, rollingPower, totalPower, segmentStartTime, segmentStopTime,
							solarPower, batteryPower, batteryCapacity, batteryCharge, totalBatteryCharge);
				}
				
				// Insert segment data into array
				MasterData myData = new MasterData();
				myData.setStartTime(previousTime);
				myData.setEndTime(previousTime + deltaTime);
				myData.setBatteryCharge(batteryCharge);
				myData.setTotalCharge(totalBatteryCharge);
				myData.setDistance(totalDistance);
				myData.setVelocity(averageVelocity);
				myData.setRoadAngle(roadAngle);
				myData.setElevation(pos.getElevation());
				rowData.add(myData);
			}
			finalEnergy = totalBatteryCharge;
			if(finalEnergy < endingEnergy){
				speedGuess -= 1;
			}
			else{
				speedGuess += 1;
			}
//			if(Double.isNaN(finalEnergy)){
//				finalEnergy = 100;
//			}
//			speedGuess = (float) (speedGuess * ((finalEnergy - endingEnergy) / endingEnergy));
		}
		return rowData;
	}
	
	public static ObservableList<MasterData> getData(int endingEnergy) {
		Position[] positions = Position.loadPositions("leg-1-10_items");
		
		List<MasterData> rowData = optimizeRun(positions, endingEnergy);
		
		ObservableList<MasterData> returnData = FXCollections.observableArrayList(rowData);
		
		return returnData;
	}
	
	private static boolean equalTolerance(double a, double b, double tolerance) {
		return Math.abs(b - a) < tolerance;
	}
}
