package weather;

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
public class AveragedWeather {
	
	private float avgCloudPercentage;
	private float avgWindDegrees;
	private float avgWindSpeed;
	
	
	public AveragedWeather(float avgCloudPercentage, float avgWindDegrees, float avgWindSpeed) {
		
		this.avgCloudPercentage = avgCloudPercentage;
		this.avgWindDegrees = avgWindDegrees;
		this.avgWindSpeed = avgWindSpeed;
		
	}
	
	
	//TODO: weighted average depending on what's closer
	public Double calculateAverage(Float a, Float b) {
		return (a + b) / 2.0;
		
	}
	
	
	public float getAvgCloudPercentage() {
		return avgCloudPercentage;
	}
	
	public float getAvgWindDegrees() {
		return avgWindDegrees;
	}
	
	public float getAvgWindSpeed() {
		return avgWindSpeed;
	}
	
	public void printOut() {
		System.out.format("Average Cloud Pct: %f \nAverage Wind Degrees: %f \nAverage Wind Speed: %f kph \n",
				avgCloudPercentage, avgWindDegrees, avgWindSpeed);
	}
}
