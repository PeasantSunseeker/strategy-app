package weather;

import java.io.IOException;

/**
 * PROJECT: seniordesign
 * AUTHOR: aaron
 * DATE: 1/22/17
 *
 * DESCRIPTION:
 */
public class Tester {

    public static void main(String[] args) throws IOException{

        CurrentWeatherData cw = new CurrentWeatherData(42.3042240,-85.5869350);
        cw.printOut();

        WeatherForecastData wf = new WeatherForecastData(42.3042240,-85.5869350);
        wf.printOut();

    }
}
