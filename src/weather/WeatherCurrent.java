package weather;

import utilities.Flag;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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
public class WeatherCurrent {
	
	private float latitude;
	private float longitude;
	private float cloudsPercentage;
	private float windSpeed;
	private float windDirection;
	
	
	//adding updated utc time fields
	private ZonedDateTime sunrise;
	private ZonedDateTime sunset;
	private ZonedDateTime lastUpdated;
	private ZonedDateTime retrievedAt;
	
	
	private Flag flag;
	
	/**
	 * @param latitude
	 * @param longitude
	 * @param cloudsPercentage
	 * @param windSpeed
	 * @param windDirection
	 * @param sunrise
	 * @param sunset
	 */
	public WeatherCurrent(float latitude, float longitude, float cloudsPercentage,
						  float windSpeed, float windDirection,
						  ZonedDateTime sunrise, ZonedDateTime sunset,
						  ZonedDateTime lastUpdated, ZonedDateTime retrievedAt) {
		
		
		this.latitude = latitude;
		this.longitude = longitude;
		this.cloudsPercentage = cloudsPercentage;
		this.windSpeed = windSpeed;
		this.windDirection = windDirection;
		this.sunrise = sunrise;
		this.sunset = sunset;
		this.lastUpdated = lastUpdated;
		this.retrievedAt = retrievedAt;
		this.flag = Flag.EMPTY;
		//if debugging
		//printOut();
	}
	
	
	/**
	 * Use if debugging
	 */
	public void printOut() {
		System.out.println(String.format("Lat: %f \nLong: %f \nCloudPct: %f \nWindspeed: %f\n" +
						"Windegrees: %f \nSunrise: %s \nSunset: %s \nLast Updated %s \nRetrieved %s \n%s\n", getLatitude(), getLongitude(), getCloudsPercentage()
				, getWindSpeed(), getWindDirection(), getSunrise(), getSunset(), getLastUpdated(), getRetrievedAt(), getFlag()));
		
	}
	
	
	public float getLatitude() {
		return latitude;
	}
	
	public float getLongitude() {
		return longitude;
	}
	
	public float getCloudsPercentage() {
		return cloudsPercentage;
	}
	
	public float getWindSpeed() {
		return windSpeed;
	}
	
	public float getWindDirection() {
		return windDirection;
	}
	
	public ZonedDateTime getSunrise() {
		return sunrise;
	}
	
	public ZonedDateTime getSunset() {
		return sunset;
	}
	
	public ZonedDateTime getLastUpdated() {
		return lastUpdated;
	}
	
	public ZonedDateTime getRetrievedAt() {
		return retrievedAt;
	}
	
	public Flag getFlag() {
		return flag;
	}
	
	public void setFlag(Flag flag) {
		this.flag = flag;
	}
	
}
