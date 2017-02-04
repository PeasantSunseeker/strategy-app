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
public class WeatherCurrent {

    float latitude;
    float longitude;
    float cloudsPercentage;
    float windSpeed;
    float windDirection;
    String sunrise;
    String sunset;

    public WeatherCurrent(float latitude, float longitude, float cloudsPercentage,
                          float windSpeed, float windDirection, String sunrise, String sunset) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.cloudsPercentage = cloudsPercentage;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.sunrise = sunrise;
        this.sunset = sunset;

        //if debugging
        printOut();
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

    public void printOut(){
        System.out.println(String.format("%f,%f,%f,%f,%f,%s,%s,\n",getLatitude(),getLongitude(),getCloudsPercentage()
        ,getWindSpeed(),getWindDirection(),getSunrise(),getSunset()));
    }
}
