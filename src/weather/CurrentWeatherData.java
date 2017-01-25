package weather;

import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;

import java.util.Date;


/**
 * PROJECT: seniordesign
 * AUTHOR: aaron
 * DATE: 1/22/17
 * <p>
 * DESCRIPTION:
 */
public class CurrentWeatherData {


    private String cityName;
    private Date currentTime;
    private double latitude;
    private double longitude;
    private Date sunrise;
    private Date sunset;
    private double temperature;
    private double humidity;
    private double pressure;

    private OpenWeatherMap owm;
    private CurrentWeather cwd;

    /**
     *
     */
    public CurrentWeatherData(double latitude, double longitude) {

        owm = new OpenWeatherMap(ApiKey.getApiKey());
        owm.setUnits(OpenWeatherMap.Units.METRIC);

        //have to cast this back to float? Caller side complains if the method params are specified as floats
        cwd = owm.currentWeatherByCoordinates((float) latitude, (float) longitude);

        //TODO: Add error checking for when data is not available

        cityName = cwd.getCityName();
        currentTime = cwd.getDateTime();
        sunrise = cwd.getSysInstance().getSunriseTime();
        sunset = cwd.getSysInstance().getSunsetTime();
        temperature = cwd.getMainInstance().getTemperature();
        humidity = cwd.getMainInstance().getHumidity();
        pressure = cwd.getMainInstance().getPressure();

    }

    /**
     * Print out data to console
     */
    public void printOut() {

        System.out.println(cityName);
        System.out.println("Last updated " + currentTime);
        System.out.println("=============================");
        System.out.println("Sunrise: " + sunrise.toString());
        System.out.println("Sunset: " + sunset.toString());
        System.out.printf("Temperature: %.3f C\n", temperature);
        System.out.println("Humidity: " + humidity + "%");
        System.out.printf("Pressure: %.3f hPa\n", pressure);

    }

    /**
     * Save data to csv file
     */
    public void saveToFile() {

    }


}
