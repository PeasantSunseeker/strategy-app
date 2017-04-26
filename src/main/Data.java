package main;

import config.CarConfig;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.*;
import utilities.GPS;
import utilities.MasterData;
import utilities.Position;
import weather.AveragedWeather;
import weather.WeatherCaching;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static ui.controllers.MainController.positions;
import static ui.controllers.MainController.zoneId;

public class Data {
	static boolean debug = false;
	static List<MasterData> rowData = new ArrayList<MasterData>();
	
	public static void main(String[] args) {
		CarConfig.loadCarConfig("config.properties");
//		getData(20);
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
	
	public static List<MasterData> optimizeRun(double endingEnergy) {
//		System.out.println("optimizeRun");
//		System.out.println(endingEnergy);
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
		int totalCounter = 0;
		
		if (rowData.size() == 0) {
			for (index = 0; index < positions.length - 1; index++) {
				MasterData myData = new MasterData();
				rowData.add(myData);
			}
		}
		
		while (!equalTolerance(finalEnergy, endingEnergy, 5) && speedModified && totalCounter < 150) {
			totalCounter++;
			//Reset run calculations
			speedModified = false;
//			totalBatteryCharge = 100;
			startTime = 9;
//			if (GPS.positionIndex == 1) {
//				totalBatteryCharge = 90;
//			} else {
//				totalBatteryCharge = Double.parseDouble(rowData.get(GPS.positionIndex - 1).getActualTotalCharge().getValue());
//			}
			totalBatteryCharge = 90;
			totalDistance = 0;
//			rowData = new ArrayList<MasterData>();
			
			System.out.format("Speed Guess: %f\n", speedGuess);
			if (debug) {
//				System.out.format("Speed Guess: %f\n", speedGuess);
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
				
				double tempTime = middleTime;
				ZonedDateTime time = ZonedDateTime.now(zoneId);
				int hours = (int) tempTime;
				tempTime = tempTime - hours;
				int minutes = (int) (tempTime * 60);
				tempTime = tempTime - (minutes / 60.0);
				int seconds = (int) (tempTime * 3600);
				time = time.withHour(hours).withMinute(minutes).withSecond(seconds);
				AveragedWeather averagedWeather = WeatherCaching.weatherSearch(time, pos.getLatitude(), pos.getLongitude());
				
				
				double sunAngle = Solar.getAngle(dayOfYear, middleTime, pos.getLatitude());

//				double solarPower = Solar.solarPower(dayOfYear, middleTime, pos.getLatitude(), 0);
				double solarPower = Solar.solarPower(dayOfYear, middleTime, pos.getLatitude(), averagedWeather.getAvgCloudPercentage());

//				double aeroPower = Aerodynamic.aerodynamicPower(averageVelocity);
				double aeroPower = Aerodynamic.aerodynamicPowerWind(averageVelocity, pos.getHeading(), averagedWeather.getAvgWindSpeed(), averagedWeather.getAvgWindDegrees());
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
				
				if (debug) {
					System.out.format("%8.2f | %5.1f | %6.1f | %6.0f | %5.0f | %5.0f | %4.0f | %6.0f | %s | %s | %5.0f | %8.1f | %8.1f | %11.2f | %7.2f\n",
							totalDistance, roadAngle, averageVelocity, gravPower, kineticPower, aeroPower, rollingPower, totalPower, segmentStartTime, segmentStopTime,
							solarPower, batteryPower, batteryCapacity, batteryCharge, totalBatteryCharge);
				}
				
				// Insert segment data into array
//				MasterData myData = new MasterData();
				MasterData myData = rowData.get(index - 1);
				myData.setStartTime(previousTime);
				myData.setEndTime(previousTime + deltaTime);
				myData.setBatteryCharge(batteryCharge);
				myData.setTotalCharge(totalBatteryCharge);
				myData.setDistance(totalDistance);
				myData.setVelocity(averageVelocity);
				myData.setRoadAngle(roadAngle);
				myData.setElevation(pos.getElevation());
//				myData.setActualBatteryCharge(batteryCharge * 1.15);
//				myData.setActualTotalCharge(totalBatteryCharge * 1.15);
				myData.setPosition(pos);
//				rowData.add(myData);
			}
			MasterData firstItem = rowData.get(0);
			firstItem.setActualTotalCharge(Double.parseDouble(firstItem.getTotalCharge().getValue()));
			
			finalEnergy = totalBatteryCharge;
			if (finalEnergy < endingEnergy) {
				speedGuess -= 1;
			} else {
				speedGuess += 1;
			}
			
			if (debug) {
				System.out.println("Final Energy: " + finalEnergy);
			}

//			if(Double.isNaN(finalEnergy)){
//				finalEnergy = 100;
//			}
//			speedGuess = (float) (speedGuess * ((finalEnergy - endingEnergy) / endingEnergy));
//			return rowData;
		}
		return rowData;
	}
	
	public static ObservableList<MasterData> getData(int endingEnergy) {
		List<MasterData> rowData = optimizeRun(endingEnergy);
		
		ObservableList<MasterData> returnData = FXCollections.observableArrayList(rowData);
		
		return returnData;
	}
	
	private static boolean equalTolerance(double a, double b, double tolerance) {
		return Math.abs(b - a) < tolerance;
	}
}
