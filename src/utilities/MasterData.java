package utilities;

import javafx.beans.property.SimpleStringProperty;

/**
 * PROJECT: seniorDesign
 * AUTHOR: broderick
 * DATE: 1/23/17
 * <p>
 * DESCRIPTION:
 */
public class MasterData {
	public SimpleStringProperty middleTime;
	public SimpleStringProperty sunAngle;
	public SimpleStringProperty solarPower;
	public SimpleStringProperty aeroPower;
	public SimpleStringProperty rollingPower;
	public SimpleStringProperty totalPower;
	public SimpleStringProperty batteryPower;
	public SimpleStringProperty batteryCap;
	public SimpleStringProperty batteryCharge;
	public SimpleStringProperty totalCharge;
	public SimpleStringProperty distance;
	
	public String getMiddleTime() {
		return middleTime.get();
	}

	public SimpleStringProperty middleTimeProperty() {
		return middleTime;
	}

	public void setMiddleTime(String middleTime) {
		this.middleTime.set(middleTime);
	}

	public String getSunAngle() {
		return sunAngle.get();
	}

	public SimpleStringProperty sunAngleProperty() {
		return sunAngle;
	}

	public void setSunAngle(String sunAngle) {
		this.sunAngle.set(sunAngle);
	}

	public String getSolarPower() {
		return solarPower.get();
	}

	public SimpleStringProperty solarPowerProperty() {
		return solarPower;
	}

	public void setSolarPower(String solarPower) {
		this.solarPower.set(solarPower);
	}

	public String getAeroPower() {
		return aeroPower.get();
	}

	public SimpleStringProperty aeroPowerProperty() {
		return aeroPower;
	}

	public void setAeroPower(String aeroPower) {
		this.aeroPower.set(aeroPower);
	}

	public String getRollingPower() {
		return rollingPower.get();
	}

	public SimpleStringProperty rollingPowerProperty() {
		return rollingPower;
	}

	public void setRollingPower(String rollingPower) {
		this.rollingPower.set(rollingPower);
	}

	public String getTotalPower() {
		return totalPower.get();
	}

	public SimpleStringProperty totalPowerProperty() {
		return totalPower;
	}

	public void setTotalPower(String totalPower) {
		this.totalPower.set(totalPower);
	}

	public String getBatteryPower() {
		return batteryPower.get();
	}

	public SimpleStringProperty batteryPowerProperty() {
		return batteryPower;
	}

	public void setBatteryPower(String batteryPower) {
		this.batteryPower.set(batteryPower);
	}

	public String getBatteryCap() {
		return batteryCap.get();
	}

	public SimpleStringProperty batteryCapProperty() {
		return batteryCap;
	}

	public void setBatteryCap(String batteryCap) {
		this.batteryCap.set(batteryCap);
	}

	public String getBatteryCharge() {
		return batteryCharge.get();
	}

	public SimpleStringProperty batteryChargeProperty() {
		return batteryCharge;
	}

	public void setBatteryCharge(String batteryCharge) {
		this.batteryCharge.set(batteryCharge);
	}

	public String getTotalCharge() {
		return totalCharge.get();
	}

	public SimpleStringProperty totalChargeProperty() {
		return totalCharge;
	}

	public void setTotalCharge(String totalCharge) {
		this.totalCharge.set(totalCharge);
	}

	public String getDistance() {
		return distance.get();
	}

	public SimpleStringProperty distanceProperty() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance.set(distance);
	}
	
	public MasterData(String middleTime, String sunAngle, String solarPower, String aeroPower, String rollingPower, String totalPower, String batteryPower, String batteryCap, String batteryCharge, String totalCharge, String distance){
		this.middleTime = new SimpleStringProperty(middleTime);
		this.sunAngle = new SimpleStringProperty(sunAngle);
		this.solarPower = new SimpleStringProperty(solarPower);
		this.aeroPower = new SimpleStringProperty(aeroPower);
		this.rollingPower = new SimpleStringProperty(rollingPower);
		this.totalPower = new SimpleStringProperty(totalPower);
		this.batteryPower = new SimpleStringProperty(batteryPower);
		this.batteryCap = new SimpleStringProperty(batteryCap);
		this.batteryCharge = new SimpleStringProperty(batteryCharge);
		this.totalCharge = new SimpleStringProperty(totalCharge);
		this.distance = new SimpleStringProperty(distance);
	}
}
