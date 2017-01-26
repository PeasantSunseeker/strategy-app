package weather;

import net.aksingh.owmjapis.HourlyForecast;
import net.aksingh.owmjapis.OpenWeatherMap;

import java.io.IOException;

/**
 * PROJECT: seniordesign
 * AUTHOR: aaron
 * DATE: 1/22/17
 * <p>
 * DESCRIPTION:
 */
public class WeatherForecastData {

    private OpenWeatherMap owm;
    private HourlyForecast hf;


    public WeatherForecastData(double latitude, double longitude) throws IOException {
        owm = new OpenWeatherMap(ApiKey.getApiKey());
        owm.setUnits(OpenWeatherMap.Units.METRIC);

        hf = owm.hourlyForecastByCoordinates((float) latitude, (float) longitude);

    }

    public void printOut() {
        HourlyForecast.Forecast forecast;
        System.out.println("Cloud Cover Forecast for " + hf.getCityInstance().getCityName());

        for (int i = 0; i < hf.getForecastCount(); i++) {

            forecast = hf.getForecastInstance(i);

            System.out.println(forecast.getDateTimeText() + "   " + forecast.getCloudsInstance().getPercentageOfClouds() + "%");
            System.out.println("Wind speed: " + forecast.getWindInstance().getWindSpeed());

        }

    }

}
