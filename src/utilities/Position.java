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
		String filename = "leg-1";
		
		try {
			FileReader fis = new FileReader(filename+".csv");
			BufferedReader ois = new BufferedReader(fis);
			
			while ((line = ois.readLine()) != null) {
//				System.out.println(line);
				items = line.split(",");
				positionList.add(new Position(Float.valueOf(items[0]), Float.valueOf(items[1]), Float.valueOf(items[2])));
			}
			
			fis.close();
			ois.close();
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
	
		savePositions(positions,filename+"-complete.ser");
		savePositions(positionsSmall,filename+"-10_items.ser");
		
//		if(savePositions(positions,"leg1.ser")) {
//			positions = null;
//			positions = loadPositions("leg1.ser");
//			if(positions != null){
//				System.out.println(positions.length);
//			}
//		}
	}
	
	public static boolean savePositions(Position[] positions, String name){
		boolean exists = new File(name).isFile();
		if(exists){
			System.out.println("File exists! - Manually delete to update");
			return false;
		}
		try {
			FileOutputStream fos = new FileOutputStream(name);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			oos.writeObject(positions);
			
			oos.close();
			fos.close();
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public static Position[] loadPositions(String name){
		Position[] positions;
		try {
			FileInputStream fis = new FileInputStream(name);
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			positions = (Position[]) ois.readObject();
			
			ois.close();
			fis.close();
			return positions;
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
