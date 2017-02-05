package utilities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * PROJECT: seniorDesign
 * AUTHOR: broderick
 * DATE: 1/23/17
 * <p>
 * DESCRIPTION:
 */
public class MasterData {
	public SimpleStringProperty startTime;
	public SimpleStringProperty endTime;
	public SimpleStringProperty batteryCharge;
	public SimpleStringProperty totalCharge;
	public SimpleStringProperty distance;
	public SimpleStringProperty velocity;
	public SimpleStringProperty roadAngle;
	
	public void setStartTime(double startTime) {
		String formatted = String.format("%02.0f:%02.0f", Math.floor(startTime), startTime%1*60);
		this.startTime.set(formatted);
	}
	
	public void setEndTime(double endTime) {
		String formatted = String.format("%02.0f:%02.0f", Math.floor(endTime), endTime%1*60);
		this.endTime.set(formatted);
	}
	
	public void setBatteryCharge(double batteryCharge) {
		String formatted = String.format("%4.2f", batteryCharge);
		this.batteryCharge.set(formatted);
	}
	
	public void setTotalCharge(double totalCharge) {
		String formatted = String.format("%4.2f", totalCharge);
		this.totalCharge.set(formatted);
	}
	
	public void setDistance(double distance) {
		String formatted = String.format("%4.0f", distance);
		this.distance.set(formatted);
	}
	
	public void setVelocity(double velocity) {
		String formatted = String.format("%3.1f", velocity);
		this.velocity.set(formatted);
	}
	
	public void setRoadAngle(double roadAngle) {
		String formatted = String.format("%2.0f%%", roadAngle);
		this.roadAngle.set(formatted);
	}
	
	public StringProperty getStartTime() {
		return startTime;
	}
	
	public StringProperty getEndTime() {
		return endTime;
	}
	
	public StringProperty getBatteryCharge() {
		return batteryCharge;
	}
	
	public StringProperty getTotalCharge() {
		return totalCharge;
	}
	
	public StringProperty getDistance() {
		return distance;
	}
	
	public StringProperty getVelocity() {
		return velocity;
	}
	
	public StringProperty getRoadAngle() {
		return roadAngle;
	}
	
	public MasterData(){
		this.startTime = new SimpleStringProperty("");
		this.endTime = new SimpleStringProperty("");
		this.batteryCharge = new SimpleStringProperty("");
		this.totalCharge = new SimpleStringProperty("");
		this.distance = new SimpleStringProperty("");
		this.velocity = new SimpleStringProperty("");
		this.roadAngle = new SimpleStringProperty("");
	}
}
