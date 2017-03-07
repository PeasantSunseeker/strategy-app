package weather;

import java.time.ZonedDateTime;
import java.util.ArrayList;

/**
 * PROJECT: seniordesign
 * AUTHOR: aaron  2/4/2017.
 * DATE: 2/4/2017
 *
 * DESCRIPTION:
 *
 *
 * INPUTS:
 *
 *
 * OUTPUTS:
 */
public class WeatherForecast {
	
	private float latitude;
	private float longitude;
	
	private ArrayList<Float> cloudPercentages;
	private ArrayList<Float> windSpeeds;
	private ArrayList<Float> windDegrees;
	private ArrayList<ZonedDateTime> times;
	
	
	public WeatherForecast(float latitude, float longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public float getLatitude() {
		return latitude;
	}
	
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	
	public float getLongitude() {
		return longitude;
	}
	
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	
	public ArrayList<Float> getCloudPercentages() {
		return cloudPercentages;
	}
	
	public void setCloudPercentages(ArrayList<Float> cloudPercentages) {
		this.cloudPercentages = cloudPercentages;
	}
	
	public ArrayList<Float> getWindSpeeds() {
		return windSpeeds;
	}
	
	public void setWindSpeeds(ArrayList<Float> windSpeeds) {
		this.windSpeeds = windSpeeds;
	}
	
	public ArrayList<Float> getWindDegrees() {
		return windDegrees;
	}
	
	public void setWindDegrees(ArrayList<Float> windDegrees) {
		this.windDegrees = windDegrees;
	}
	
	public ArrayList<ZonedDateTime> getTimes() {
		return times;
	}
	
	public void setTimes(ArrayList<ZonedDateTime> times) {
		this.times = times;
	}
	
	/**
	 * For debugging
	 */
	public void printOut() {
		System.out.println("Printing...");
		System.out.println(String.format("Lat: %f , Long: %f\n", latitude, longitude));
		
		for (int i = 0; i < cloudPercentages.size(); i++) {
			System.out.println(String.format("%f,%f,%f,%s\n", cloudPercentages.get(i),
					windDegrees.get(i), windSpeeds.get(i), times.get(i)));
		}

	}
}
