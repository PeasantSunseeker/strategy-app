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
    private float elevation; //can change this data type if needed, hasn't been established yet

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
