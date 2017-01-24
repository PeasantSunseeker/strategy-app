package utilities;

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
 */
public class Position {

    private float latitude;
    private float longitude;
    private float elevation;
    private float direction; //degrees clockwise from due north
    private float angle; //angle of the road 0 is flat, 40 is a steep uphill, -40 is a steep downhill
    
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


    public Position(float latitude, float longitude, float elevation) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
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
}
