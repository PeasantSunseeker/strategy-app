package utilities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;

/**
 * PROJECT: seniorDesign
 * AUTHOR: broderick
 * DATE: 1/23/17
 * <p>
 * DESCRIPTION:
 */
public class MasterData {
	private SimpleStringProperty startTime;
	private SimpleStringProperty endTime;
	private SimpleStringProperty batteryCharge;
	private SimpleStringProperty totalCharge;
	private SimpleStringProperty distance;
	private SimpleStringProperty velocity;
	private SimpleStringProperty roadAngle;
	private SimpleStringProperty elevation;
	private SimpleStringProperty actualBatteryCharge;
	private SimpleStringProperty actualTotalCharge;
	private Position position;
	
	
	public void setStartTime(double startTime) {
		String formatted = String.format("%02.0f:%02.0f", Math.floor(startTime), startTime % 1 * 60);
		this.startTime.set(formatted);
	}
	
	public void setEndTime(double endTime) {
		String formatted = String.format("%02.0f:%02.0f", Math.floor(endTime), endTime % 1 * 60);
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
		String formatted = String.format("%5.02f%%", roadAngle);
		this.roadAngle.set(formatted);
	}
	
	public void setElevation(double elevation) {
		String formatted = String.format("%5.0f", elevation);
		this.elevation.set(formatted);
	}
	
	public void setActualBatteryCharge(double actualBatteryCharge) {
		String formatted = String.format("%4.2f", actualBatteryCharge);
		this.actualBatteryCharge.set(formatted);
	}
	
	public void setActualTotalCharge(double actualTotalCharge) {
		String formatted = String.format("%4.2f", actualTotalCharge);
		this.actualTotalCharge.set(formatted);
	}
	
	public void setPosition(Position position){
		this.position = position;
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
	
	public StringProperty getElevation() {
		return elevation;
	}
	
	public StringProperty getActualBatteryCharge() {
		return actualBatteryCharge;
	}
	
	public StringProperty getActualTotalCharge() {
		return actualTotalCharge;
	}
	
	public Position getPosition(){return position;}
	
	public MasterData(){
		this.startTime = new SimpleStringProperty("");
		this.endTime = new SimpleStringProperty("");
		this.batteryCharge = new SimpleStringProperty("");
		this.totalCharge = new SimpleStringProperty("");
		this.distance = new SimpleStringProperty("");
		this.velocity = new SimpleStringProperty("");
		this.roadAngle = new SimpleStringProperty("");
		this.elevation = new SimpleStringProperty("");
		this.actualBatteryCharge = new SimpleStringProperty("");
		this.actualTotalCharge = new SimpleStringProperty("");
	}
}
