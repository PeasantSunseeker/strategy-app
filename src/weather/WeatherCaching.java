package weather;

import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.HourlyForecast;
import net.aksingh.owmjapis.OpenWeatherMap;
import utilities.Flag;
import utilities.Position;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
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
public class WeatherCaching {

    private static Position[] positions;
    private static ArrayList<WeatherForecast> forecasts;

    /**
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

        saveForecast("weather-forecast-10_locations");


        forecasts = loadForecast("weather-forecast-10_locations");

        if (forecasts != null) {
            for (int i = 0; i < forecasts.size(); i++) {
                forecasts.get(i).printOut();
            }
        } else {
            System.out.println("Error loading forecasts");
        }


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

                bufferedWriter.write(String.format("%f,%f,%f,%f,%f,%s,%s,%s\n", latitude, longitude, cloudsPercentage, windSpeed, windDirection,
                        sunrise, sunset, Flag.RETRIEVED));

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
                        items[6]
                );

                weatherCurrents[i].setFlag(Flag.valueOf(items[7]));

            }

            bufferedReader.close();
            fileReader.close();

            return weatherCurrents;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean saveForecast(String outFileName) {

        int count = 0;
        outFileName += ".csv";
        OpenWeatherMap owm = new OpenWeatherMap(ApiKey.getApiKey());
        HourlyForecast hf;



        //read coordinates from csv file
        try {

            FileWriter fw = new FileWriter(outFileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fw);

            float latitude;
            float longitude;
            float cloudsPercentage;
            float windSpeed;
            float windDirection;
            String forecastTime;

            for (int i = 0; i < positions.length; i++) {

                latitude = positions[i].getLatitude();
                longitude = positions[i].getLongitude();
                System.out.println(latitude + " " + longitude);
                bufferedWriter.write(String.format("%d,%f,%f\n", (i + 1), latitude, longitude));
                hf = owm.hourlyForecastByCoordinates(latitude, longitude);

                for (int j = 0; j < hf.getForecastCount(); j++) {

                    cloudsPercentage = hf.getForecastInstance(j).getCloudsInstance().getPercentageOfClouds();
                    windSpeed = hf.getForecastInstance(j).getWindInstance().getWindSpeed();
                    windDirection = hf.getForecastInstance(j).getWindInstance().getWindDegree();
                    forecastTime = hf.getForecastInstance(j).getDateTimeText();


                    bufferedWriter.write(String.format("%f,%f,%f,%s,%s\n", cloudsPercentage, windSpeed, windDirection,
                            forecastTime, Flag.RETRIEVED));

                    count++;
                }


                System.out.println(hf.getCityInstance().getCityName());
            }

            bufferedWriter.close();
            fw.close();
        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
        return true;
    }


    public static ArrayList<WeatherForecast> loadForecast(String fileName) {


        fileName = fileName + ".csv";
        ArrayList<WeatherForecast> weatherForecasts = new ArrayList<WeatherForecast>();

        String line;
        String[] items;
        int curIndex = 0;

        float currentLat;

        try {

            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {

                WeatherForecast wf = null;

                items = line.split(",");
                if (items.length == 3) {
                    System.out.println("ID line");
                    //a line with a position
                    curIndex = Integer.parseInt(items[0]) - 1;
                    weatherForecasts.add(curIndex, new WeatherForecast(Float.valueOf(items[1]), Float.valueOf(items[2])));

                    weatherForecasts.get(curIndex).setCloudPercentages(new ArrayList<Float>());
                    weatherForecasts.get(curIndex).setWindSpeeds(new ArrayList<Float>());
                    weatherForecasts.get(curIndex).setWindDegrees(new ArrayList<Float>());
                    weatherForecasts.get(curIndex).setTime(new ArrayList<String>());
                } else {

                    //a line with data

                    assert (wf != null);

                    weatherForecasts.get(curIndex).getCloudPercentages().add(Float.valueOf(items[0]));
                    weatherForecasts.get(curIndex).getWindSpeeds().add(Float.valueOf(items[1]));
                    weatherForecasts.get(curIndex).getWindDegrees().add(Float.valueOf(items[2]));
                    weatherForecasts.get(curIndex).getTime().add(items[3]);


                }

                assert (wf != null);


            }


            bufferedReader.close();
            fileReader.close();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return weatherForecasts;

    }


    //TODO: workflow on current vs future


    //Wanted: February 09 2017, 8:00am
    //if(wanted < current + 6hrs)
        //call current
        //get earliest forecast we can (9:00am?)
        //average current + forecast
    //else if(wanted < current + 3 days)
        //call forecast()
        //get the closest two forecasts (6:00am and 9:00am)
        //average them
    //else


    //forecast()
        //if you have internet
             //if currentDate > list(0) + 1day  //our forecasts are more than 1 day old
                //get new forecasts
    //

}



