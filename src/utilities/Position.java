package utilities;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * PROJECT: seniordesign
 * AUTHOR: aaron
 * DATE: 1/22/17
 *
 * DESCRIPTION: Master object for current position.
 *
 * -Latitude
 * -Longitude
 * -Elevation
 * -Direction
 * -Angle
 */
public class Position implements Serializable {

    private float latitude = 0f;
    private float longitude = 0f;
    private float elevation = Float.MAX_VALUE;
    private float direction = 0f; //degrees clockwise from due north
    private float angle = 0f; //angle of the road 0 is flat, 40 is a steep uphill, -40 is a steep downhill
    
    //can change this data type if needed, hasn't been established yet


    public float getDirection() {
        return direction;
    }

    public void setDirection(float direction) {
        this.direction = direction;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }
	
	public float getLatitude() {
		return latitude;
	}
	
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	
	public float getLongitude() {
		return longitude;
	}
	
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	
	public float getElevation() {
		return elevation;
	}
	
	public void setElevation(float elevation) {
		this.elevation = elevation;
	}

    public Position(float latitude, float longitude, float elevation) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
    }
    
    public String toString(){
    	return String.format("%f - %f - %f",latitude,longitude, elevation);
	}
    
    public static void main(String[] args){
		List<Position> positionList = new ArrayList<Position>();
		String line;
		String[] items;
		String fileName = "leg-1";
		
		try {
			FileReader fileReader = new FileReader(fileName+".csv");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			while ((line = bufferedReader.readLine()) != null) {
//				System.out.println(line);
				items = line.split(",");
				positionList.add(new Position(Float.valueOf(items[0]), Float.valueOf(items[1]), Float.valueOf(items[2])));
			}
			
			fileReader.close();
			bufferedReader.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		Position[] positions = positionList.toArray(new Position[positionList.size()]);
	
		int count = 10;
		Position[] positionsSmall = new Position[count];
		for(int i = 0; i < count; i++){
			positionsSmall[i] = positions[i * (positions.length / count)];
		}
		for (Position pos: positionsSmall) {
			System.out.println(pos);
		}
	
		savePositions(positions,fileName+"-complete");
		savePositions(positionsSmall,fileName+"-10_items");
	
		positionsSmall = loadPositions(fileName+"-10_items");
		
//		if(savePositions(positions,"leg1.ser")) {
//			positions = null;
//			positions = loadPositions("leg1.ser");
//			if(positions != null){
				System.out.println(positionsSmall.length);
//			}
//		}
	}
	
	public static boolean savePositions(Position[] positions, String fileName){
    	fileName = fileName + ".csv";
		boolean exists = new File(fileName).isFile();
		if(exists){
//			System.out.println("File exists! - Manually delete to update");
//			return false;
		}
		try {
//			FileOutputStream fos = new FileOutputStream(name);
//			ObjectOutputStream oos = new ObjectOutputStream(fos);
//
//			oos.writeObject(positions);
//
//			oos.close();
//			fos.close();
			
			FileWriter fileWriter = new FileWriter(fileName);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			bufferedWriter.write(String.format("%s\n", positions.length));
			
			for(int i = 0; i < positions.length; i++){
				Position pos = positions[i];
				
				String line = String.format("%s,%s,%s\n", pos.latitude, pos.longitude, pos.elevation);
				bufferedWriter.write(line);
			}
			
			bufferedWriter.close();
			fileWriter.close();
			
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public static Position[] loadPositions(String fileName){
		fileName = fileName + ".csv";
		Position[] positions;
//		System.out.println(fileName);
		try {
//			FileInputStream fileInputStream = new FileInputStream(fileName);
//			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
//
//			positions = (Position[]) objectInputStream.readObject();
//
//			objectInputStream.close();
//			fileInputStream.close();
			
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String read = bufferedReader.readLine();
//			System.out.println(read);
			int length = Integer.valueOf(read);
//			System.out.println(length);
			positions = new Position[length];
			
			for(int i = 0; i < length; i++){
				String line = bufferedReader.readLine();
				
				String[] items = line.split(",");
				positions[i] = new Position(Float.valueOf(items[0]), Float.valueOf(items[1]), Float.valueOf(items[2]));
			}
			
			bufferedReader.close();
			fileReader.close();
			
			return positions;
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
