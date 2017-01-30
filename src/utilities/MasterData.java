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
	public SimpleStringProperty middleTime;
	public SimpleStringProperty batteryCharge;
	public SimpleStringProperty totalCharge;
	public SimpleStringProperty distance;
	
	public void setMiddleTime(double middleTime) {
		String formatted = String.format("%2.0f:%2.0f", middleTime, middleTime%1*60);
		this.middleTime.set(formatted);
	}
	
	public void setBatteryCharge(double batteryCharge) {
		String formatted = String.format("%8.2f", batteryCharge);
		this.batteryCharge.set(formatted);
	}
	
	public void setTotalCharge(double totalCharge) {
		String formatted = String.format("%7.2f", totalCharge);
		this.totalCharge.set(formatted);
	}
	
	public void setDistance(double distance) {
		String formatted = String.format("%6.0f", distance);
		this.distance.set(formatted);
	}
	
	public StringProperty getMiddleTime() {
		return middleTime;
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
	
	public MasterData(){
		this.middleTime = new SimpleStringProperty("");
		this.batteryCharge = new SimpleStringProperty("");
		this.totalCharge = new SimpleStringProperty("");
		this.distance = new SimpleStringProperty("");
	}
}
