package google;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import org.json.JSONArray;
import org.json.JSONObject;
import ui.controllers.MainController;
import utilities.Flag;
import utilities.Position;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static ui.controllers.MainController.positions;

/**
 * PROJECT: seniorDesign
 * AUTHOR: broderick
 * DATE: 2/4/17
 * <p>
 * DESCRIPTION:
 */
public class Elevation {
	
	public static void main(String[] args) {
		String fileName = "leg-1-10_items";
		positions = Position.loadPositions(fileName);
		retrieve(fileName);
	}
	
	public static void retrieve(String fileName) {
		if (positions.length >= 512) {
			//TODO make multiple calls to handle high amounts of positions
			System.out.println("Too many positions");
			return;
		}
		List locations = new ArrayList();
		
		for (int i = 0; i < positions.length; i++) {
			Position pos = positions[i];
			if (pos.getElevationFlag() != Flag.RETRIEVED) {
				locations.add(String.join(",", String.valueOf(pos.getLatitude()), String.valueOf(pos.getLongitude())));
			}
		}
		
		if (locations.size() == 0) {
			System.out.println("All positions have cached elevation data");
			return;
		}
		System.out.println("Retrieving " + locations.size() + " elevations");
		String locationList = String.join("|", locations);
		
		try {
			GetRequest request = Unirest.get("https://maps.googleapis.com/maps/api/elevation/json?key={key}&locations=")
					.header("accept", "application/json")
					.routeParam("key", "AIzaSyC5V0ODEgmk8KaeG48_WWoeUK5D8rh6WDU");
			
			request = Unirest.get(request.getUrl() + URLEncoder.encode(locationList, "UTF-8"));
			
			if (request.getUrl().length() > 8192) {
				//TODO Fix too long of a request
				System.out.println("Request too long");
				return;
			}

//			System.out.println(request.getUrl());
			HttpResponse<JsonNode> jsonResponse = request.asJson();
			
			JSONObject body = jsonResponse.getBody().getObject();
			System.out.println(body);
			String googleStatus = body.getString("status");
			
			if (jsonResponse.getStatus() == 200) {
				if (googleStatus.compareTo("OK") == 0) {
					System.out.println("Google Request: " + googleStatus);
					
					JSONArray results = body.getJSONArray("results");
					if (results.length() != locations.size()) {
						System.out.println("Not enough results");
						return;
					}
					int matchCount = 0;
					int positionCounter = 0;
					for (int i = 0; i < results.length(); i++) {
						
						JSONObject item = results.getJSONObject(i);
						JSONObject location = item.getJSONObject("location");
						double latitude = location.getDouble("lat");
						double longitude = location.getDouble("lng");
						double elevation = item.getDouble("elevation");
//						System.out.println(pos.toString());
//						System.out.println(String.format("%f - %f - %f", latitude, longitude, elevation));
						
						for (int j = positionCounter; j < positions.length; j++) {
							Position pos = positions[j];
//							System.out.println("Looking");
							if (equalTolerance(pos.getLatitude(), latitude) && equalTolerance(pos.getLongitude(), longitude)) {
								pos.setElevation((float) elevation);
								pos.setElevationFlag(Flag.RETRIEVED);
//								System.out.println("Match");
								matchCount++;
								positionCounter = j + 1;
								break;
							}
						}
					}
					
					if (matchCount == results.length()) {
						System.out.println("All results matched");
						Position.savePositions(positions, fileName);
					} else {
						System.out.println("Position mismatch");
						System.out.println("Locations: " + locations.size() + " results");
					}
					
				} else {
					//TODO google elevation response error
					System.out.println("Google Status: " + googleStatus);
				}
			} else {
				//TODO http request
				System.out.println("jsonResponse: " + jsonResponse);
			}
		} catch (UnirestException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	private static boolean equalTolerance(double a, double b) {
		return Math.abs(b - a) < .00001;
	}
}
