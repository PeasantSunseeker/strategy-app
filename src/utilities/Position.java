package utilities;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import utilities.Flag;

/**
 * PROJECT: seniordesign
 * AUTHOR: aaron
 * DATE: 1/22/17
 * <p>
 * DESCRIPTION: Master object for current position.
 * <p>
 * -Latitude
 * -Longitude
 * -Elevation
 * -Direction
 * -Angle
 */
public class Position {


    private float latitude = 0f;
    private float longitude = 0f;
    private float elevation = 0f;
    private float direction = 0f; //degrees clockwise from due north
    private float angle = 0f; //angle of the road 0 is flat, 40 is a steep uphill, -40 is a steep downhill
    private float velocity = 80f;

    private Flag elevationFlag;
    private Flag velocityFlag;

    //region Accessors
    public Flag getVelocityFlag() {
        return velocityFlag;
    }

    public void setVelocityFlag(Flag velocityFlag) {
        this.velocityFlag = velocityFlag;
    }

    public Flag getElevationFlag() {
        return elevationFlag;
    }

    public void setElevationFlag(Flag elevationFlag) {
        this.elevationFlag = elevationFlag;
    }

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

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }
    //endregion

    public Position(float latitude, float longitude, float elevation) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
        this.elevationFlag = Flag.EMPTY;
        this.velocityFlag = Flag.EMPTY;
    }

    public String toString() {
        return String.format("%f - %f - %f", latitude, longitude, elevation);
    }

    public static void main(String[] args) {
        List<Position> positionList = new ArrayList<Position>();
        String line;
        String[] items;
        String fileName = "leg-1";

        try {
            FileReader fileReader = new FileReader(fileName + ".csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
//				System.out.println(line);
                items = line.split(",");
                positionList.add(new Position(Float.valueOf(items[1]), Float.valueOf(items[0]), Float.valueOf(items[2])));
            }

            fileReader.close();
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Position[] positions = positionList.toArray(new Position[positionList.size()]);

        int count = 10;
        Position[] positionsSmall = new Position[count];
        for (int i = 0; i < count; i++) {
            positionsSmall[i] = positions[i * (positions.length / count)];
            positionsSmall[i].elevation = 0;
        }
        for (Position pos : positionsSmall) {
            System.out.println(pos);
        }

        savePositions(positions, fileName + "-complete");
        savePositions(positionsSmall, fileName + "-10_items");

        positionsSmall = loadPositions(fileName + "-10_items");

        System.out.println(positionsSmall.length);
        System.out.println(positionsSmall[0].velocity);
        System.out.println(Position.getDistance(positionsSmall[0], positionsSmall[9]));
    }

    public static double getDistance(Position a, Position b) {
        float radius = 6371.009f;
        double deltaLatitude = b.latitude - a.latitude;
        double deltaLongitude = b.longitude - a.longitude;
        double meanLatitude = (b.latitude + a.latitude) / 2;

        deltaLatitude = Math.toRadians(deltaLatitude);
        deltaLongitude = Math.toRadians(deltaLongitude);
        meanLatitude = Math.toRadians(meanLatitude);

        return radius * Math.sqrt(Math.pow(deltaLatitude, 2) + Math.pow((Math.cos(meanLatitude) * deltaLongitude), 2));
    }

    public static double calculateAngle(Position a, Position b) {
        double distance = getDistance(a, b);
        double deltaElevation = (b.elevation - a.elevation) / 1000; //Convert meters to kilometers
        return Math.toDegrees(Math.atan(deltaElevation / distance));
    }

    public static boolean savePositions(Position[] positions, String fileName) {
        fileName = fileName + ".csv";
        boolean exists = new File(fileName).isFile();
        if (exists) {
//			System.out.println("File exists! - Manually delete to update");
//			return false;
        }
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(String.format("%s\n", positions.length));

            for (int i = 0; i < positions.length; i++) {
                Position pos = positions[i];

                String line = String.format("%s,%s,%s,%s,%s,%s,%s,%s\n", pos.latitude, pos.longitude, pos.elevation, pos.direction, pos.angle, pos.velocity, pos.getElevationFlag(), pos.getVelocityFlag());
                bufferedWriter.write(line);
            }


            bufferedWriter.close();
            fileWriter.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Position[] loadPositions(String fileName) {
        fileName = fileName + ".csv";
        Position[] positions;
//		System.out.println(fileName);
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String read = bufferedReader.readLine();
//			System.out.println(read);
            int length = Integer.valueOf(read);
//			System.out.println(length);
            positions = new Position[length];

            for (int i = 0; i < length; i++) {
                String line = bufferedReader.readLine();

                String[] items = line.split(",");
                positions[i] = new Position(Float.valueOf(items[0]), Float.valueOf(items[1]), Float.valueOf(items[2]));
                positions[i].direction = Float.valueOf(items[3]);
                positions[i].angle = Float.valueOf(items[4]);
                positions[i].velocity = Float.valueOf(items[5]);
                positions[i].elevationFlag = Flag.valueOf(items[6]);
                positions[i].velocityFlag = Flag.valueOf(items[7]);
            }

            bufferedReader.close();
            fileReader.close();

            return positions;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
