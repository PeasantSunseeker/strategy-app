package weather;

import net.aksingh.owmjapis.AbstractWeather;
import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;
import utilities.Position;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

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
public class WeatherCaching {

    private static Position[] positions;

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        //get current weather conditions


        positions = Position.loadPositions("leg-1-10_items");
        if (positions == null) {
            System.out.println("Error loading positions");
        } else {
            saveCurrent("weather-10_locations");
        }

        loadCurrent("weather-10_locations");


    }

    /**
     * @param outFileName
     * @return
     */
    public static boolean saveCurrent(String outFileName) {

        outFileName += ".csv";
        OpenWeatherMap owm = new OpenWeatherMap(ApiKey.getApiKey());
        CurrentWeather cw;


        //read coordinates from csv file
        try {

            FileWriter fw = new FileWriter(outFileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fw);

            float latitude;
            float longitude;
            float cloudsPercentage;
            float windSpeed;
            float windDirection;
            String sunrise;
            String sunset;

            bufferedWriter.write(String.format("%d\n", positions.length));
            for (int i = 0; i < positions.length; i++) {

                latitude = positions[i].getLatitude();
                longitude = positions[i].getLongitude();
                System.out.println(latitude + " " + longitude);

                cw = owm.currentWeatherByCoordinates(latitude, longitude);
                cloudsPercentage = cw.getCloudsInstance().getPercentageOfClouds();
                windSpeed = cw.getWindInstance().getWindSpeed();
                windDirection = cw.getWindInstance().getWindDegree();
                sunrise = cw.getSysInstance().getSunriseTime().toString();
                sunset = cw.getSysInstance().getSunsetTime().toString();

                bufferedWriter.write(String.format("%f,%f,%f,%f,%f,%s,%s\n", latitude, longitude, cloudsPercentage, windSpeed, windDirection,
                        sunrise, sunset));

                System.out.println(cw.getCityName().toString());
            }

            bufferedWriter.close();
            fw.close();
        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     *
     * @param fileName
     * @return
     */
    public static WeatherCurrent[] loadCurrent(String fileName) {
        fileName = fileName + ".csv";
        WeatherCurrent[] weatherCurrents;

        try {


            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String read = bufferedReader.readLine();

            int length = Integer.valueOf(read);

            weatherCurrents = new WeatherCurrent[length];

            for (int i = 0; i < length; i++) {
                String line = bufferedReader.readLine();

                String[] items = line.split(",");
                weatherCurrents[i] = new WeatherCurrent(
                        Float.valueOf(items[0]),
                        Float.valueOf(items[1]),
                        Float.valueOf(items[2]),
                        Float.valueOf(items[3]),
                        Float.valueOf(items[4]),
                        items[5],
                        items[6]);

            }

            bufferedReader.close();
            fileReader.close();

            return weatherCurrents;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}


