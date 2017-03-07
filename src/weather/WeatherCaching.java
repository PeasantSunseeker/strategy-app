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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

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
 *
 * WeatherCurrent:
 *
 * Latitude,Longitude,CloudPct,WindSpeed,WindDir,Sunrise,Sunset,LastUpdated,TimeRetrieved
 *
 *
 * WeatherForecast:
 *
 *
 *
 *
 *
 *
 *
 * All times are stored in UTC as ZonedDateTimes.
 */
public class WeatherCaching {
	
	private final static String OWM_DATE_PATTERN = "EEE MMM dd HH:mm:ss zzz yyyy";
	
	private static Position[] positions;
	private static ArrayList<WeatherForecast> forecasts;
	
	private static ZonedDateTime now = ZonedDateTime.now();
	
	
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
				//forecasts.get(i).printOut();
			}
		} else {
			//System.out.println("Error loading forecasts");
		}
		
		
		positions = Position.loadPositions("leg-1-10_items");
		
		
		forecasts = loadForecast("weather-forecast-10_locations");
		
		if (refreshForecasts() == true) {
			System.out.println("Weather forecasts either missing or outdated. Getting new forecasts...");
			saveForecast("weather-forecast-10_locations");
		}else{
			System.out.println("No need to refresh forecasts");
		}
		
		//try and find a forecast in the file, 2017-02-21	00:00
		LocalDateTime search = LocalDateTime.of(2017, Month.MARCH, 8, 0, 0);
//		AveragedWeather a = findForecastAtTime(search);
//		a.printOut();
		
	}
	
	/**
	 * @param outFileName name of the .csv file to save to
	 * @return false if there was an error
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
			String lastUpdated;
			ZonedDateTime retrievedAt;
			
			bufferedWriter.write(String.format("%d\n", positions.length));
			for (int i = 0; i < positions.length; i++) {
				
				latitude = positions[i].getLatitude();
				longitude = positions[i].getLongitude();
				
				
				cw = owm.currentWeatherByCoordinates(latitude, longitude);
				cloudsPercentage = cw.getCloudsInstance().getPercentageOfClouds();
				windSpeed = cw.getWindInstance().getWindSpeed();
				windDirection = cw.getWindInstance().getWindDegree();
				
				
				sunrise = cw.getSysInstance().getSunriseTime().toString();
				sunset = cw.getSysInstance().getSunsetTime().toString();
				lastUpdated = cw.getDateTime().toString();
				
				//convert times incoming from OpenWeatherMap to UTC ZonedDateTime, and get current time in UTC as ZonedDateTime
				
				ZonedDateTime rise;
				ZonedDateTime set;
				ZonedDateTime updated;
				
				rise = toZonedDateTime(sunrise);
				set = toZonedDateTime(sunset);
				updated = toZonedDateTime(lastUpdated);
				
				
				retrievedAt = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("UTC"));
				//System.out.println("Retrieved at" + retrievedAt.toString());
				
				bufferedWriter.write(String.format("%f,%f,%f,%f,%f,%s,%s,%s,%s,%s\n", latitude, longitude, cloudsPercentage, windSpeed, windDirection,
						rise, set, updated, retrievedAt, Flag.RETRIEVED));
				
				
				//System.out.println(latitude + " " + longitude);
				//System.out.println(cw.getCityName().toString());
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
	 * @param fileName the .csv file to load from
	 * @return array of WeatherCurrent objects. Will be null if there was an error
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
						ZonedDateTime.parse(items[5]),
						ZonedDateTime.parse(items[6]),
						ZonedDateTime.parse(items[7]),
						ZonedDateTime.parse(items[8])
				
				);
				
				weatherCurrents[i].setFlag(Flag.valueOf(items[9]));
				//weatherCurrents[i].printOut();
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
			
			FileWriter fw = new FileWriter(outFileName, false);
			BufferedWriter bufferedWriter = new BufferedWriter(fw);
			
			float latitude;
			float longitude;
			float cloudsPercentage;
			float windSpeed;
			float windDirection;
			ZonedDateTime retrieved = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("UTC"));
			
			for (int i = 0; i < positions.length; i++) {
				
				latitude = positions[i].getLatitude();
				longitude = positions[i].getLongitude();
				System.out.println(latitude + " " + longitude);
				bufferedWriter.write(String.format("%d,%f,%f,%s\n", (i + 1), latitude, longitude, retrieved));
				hf = owm.hourlyForecastByCoordinates(latitude, longitude);
				
				for (int j = 0; j < hf.getForecastCount(); j++) {
					
					cloudsPercentage = hf.getForecastInstance(j).getCloudsInstance().getPercentageOfClouds();
					windSpeed = hf.getForecastInstance(j).getWindInstance().getWindSpeed();
					windDirection = hf.getForecastInstance(j).getWindInstance().getWindDegree();
					//forecastTime = hf.getForecastInstance(j).getDateTimeText();
					
					Date forecastDate = hf.getForecastInstance(j).getDateTime();
					int offset = hf.getForecastInstance(j).getDateTime().getTimezoneOffset();
					//System.out.println("Timezoneoffset is "+hf.getForecastInstance(j).getDateTime().getTimezoneOffset());
					
					//TODO Aaron: convert forecast time to UTC ZonedDateTime and write to file
					ZonedDateTime out = forecastDate.toInstant().atZone(ZoneId.of("UTC")).plusMinutes(offset);
					
					bufferedWriter.write(String.format("%f,%f,%f,%s,%s\n", cloudsPercentage, windSpeed, windDirection,
							out, Flag.RETRIEVED));
					
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
				if (items.length == 4) {
					//System.out.println("ID line");
					//a line with a position
					curIndex = Integer.parseInt(items[0]) - 1;
					weatherForecasts.add(curIndex, new WeatherForecast(Float.valueOf(items[1]), Float.valueOf(items[2])));
					
					weatherForecasts.get(curIndex).setCloudPercentages(new ArrayList<Float>());
					weatherForecasts.get(curIndex).setWindSpeeds(new ArrayList<Float>());
					weatherForecasts.get(curIndex).setWindDegrees(new ArrayList<Float>());
					weatherForecasts.get(curIndex).setTime(new ArrayList<ZonedDateTime>());
				} else {
					
					//a line with data
					
					assert (wf != null);
					
					weatherForecasts.get(curIndex).getCloudPercentages().add(Float.valueOf(items[0]));
					weatherForecasts.get(curIndex).getWindSpeeds().add(Float.valueOf(items[1]));
					weatherForecasts.get(curIndex).getWindDegrees().add(Float.valueOf(items[2]));
					weatherForecasts.get(curIndex).getTime().add(ZonedDateTime.parse(items[3]));
					
					
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
	
	
	/**
	 * @param wanted
	 * @return an AveragedWeather, it will be null if something went wrong
	 */
	public static AveragedWeather findForecastAtTime(LocalDateTime wanted) {
		//TODO: workflow on current vs future
		
		//Wanted: February 09 2017, 8:00am
		//if(wanted < current + 6hrs)            wanted within the next 6 hours
		//call current
		//get earliest forecast we can (9:00am?)
		//average current + forecast
		//else if(wanted < current + 3 days)
		//call forecast()
		//get the closest two forecasts (6:00am and 9:00am)
		//average them
		//else
		
		
		System.out.println("Looking for forecast at " + wanted);
		
		boolean notFound = true;
		
		return null;
	}
	
	
	/**
	 * @return true- if the first forecast in the file is missing or old
	 */
	public static boolean refreshForecasts() {
		
	
		ZonedDateTime firstForecast;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd kk:mm:ss");
		
		
		try {
			firstForecast = forecasts.get(0).getTime().get(0);
		} catch (Exception e) {
			System.out.println(e);
			return true;
		}
		
		System.out.println("Time of first forecast is " + firstForecast);
		if (now.isAfter(firstForecast.plusDays(1))) {
			//get new forecasts
			return true;
		}
		
		
		return false;
	}
	
	
	/**
	 * @param input
	 * @return the input time as a zoned datetime
	 */
	private static ZonedDateTime toZonedDateTime(String input) {
		
		
		DateFormat sdf = new SimpleDateFormat(OWM_DATE_PATTERN);
		try {
			Date date = sdf.parse(input);
			ZonedDateTime zdt = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of("UTC"));
			//System.out.println(zdt.toString());
			return zdt;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
		//ZonedDateTime zoned = ZonedDateTime.
		
		
		return null;
	}
	
}
//forecast()
//if you have internet
//if currentDate > list(0) + 1day  //our forecasts are more than 1 day old
//get new forecasts
//
	




