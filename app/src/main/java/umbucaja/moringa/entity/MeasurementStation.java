package umbucaja.moringa.entity;

import java.util.List;

/**
 * Created by Andre on 17/06/2016.
 */
public class MeasurementStation {

    private long id;
    private String name;
    private float latitude;
    private float longitude;
    private List<RainFallMeasurement> rainFallMeasurements;

    public MeasurementStation(long id, String name, float latitude, float longitude) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public List<RainFallMeasurement> getRainFallMeasurements() {
        return rainFallMeasurements;
    }

    public void setRainFallMeasurements(List<RainFallMeasurement> rainFallMeasurements) {
        this.rainFallMeasurements = rainFallMeasurements;
    }
}
