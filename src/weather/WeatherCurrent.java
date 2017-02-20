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
	
	float latitude;
	float longitude;
	float cloudsPercentage;
	float windSpeed;
	float windDirection;
	String sunrise;
	String sunset;
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
						  float windSpeed, float windDirection, String sunrise, String sunset) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.cloudsPercentage = cloudsPercentage;
		this.windSpeed = windSpeed;
		this.windDirection = windDirection;
		this.sunrise = sunrise;
		this.sunset = sunset;
		this.flag = Flag.EMPTY;
		//if debugging
		//printOut();
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
	
	public String getSunrise() {
		return sunrise;
	}
	
	public String getSunset() {
		return sunset;
	}
	
	public Flag getFlag() {
		return flag;
	}
	
	public void setFlag(Flag flag) {
		this.flag = flag;
	}
	
	/**
	 * Use if debugging
	 */
	public void printOut() {
		System.out.println(String.format("%f,%f,%f,%f,%f,%s,%s,%s\n", getLatitude(), getLongitude(), getCloudsPercentage()
				, getWindSpeed(), getWindDirection(), getSunrise(), getSunset(), getFlag()));
		
		System.out.println("=== datetime stuff ===");
		
		LocalDateTime rightNow = LocalDateTime.now();
		
		//sunrise/sunset time stamp pattern
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEE MMM dd kk:mm:ss zzz uuuu");
		LocalDateTime sunrise = LocalDateTime.parse(getSunrise(), dtf);
		LocalDateTime sunset = LocalDateTime.parse(getSunset(), dtf);
		
		System.out.println("Original sunrise in EST is " + sunrise);
		System.out.println("Two days from now is " + sunrise.plusDays(2));
		
		//TODO: find a way to do this by specifying time zone instead of just subtracting
		System.out.println("Sunrise time converted to PST is " + sunrise.minusHours(3));
	}
	
}
