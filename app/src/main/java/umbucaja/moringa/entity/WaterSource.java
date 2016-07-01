package umbucaja.moringa.entity;

import java.util.List;

/**
 * Created by Andre on 17/06/2016.
 */
public class WaterSource {
    private long id;
    private String name;
    private float capacity;
    private String measurementUnit;
    private String type;

    private List<WaterSourceMeasurement> waterSourceMeasurements;

    public void setId(long id) {
        this.id = id;
    }


    public WaterSource(long id, String name, float capacity, String measurementUnit, String type) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.measurementUnit = measurementUnit;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getCapacity() {
        return capacity;
    }

    public String getMeasurementUnit() {
        return measurementUnit;
    }

    public String getType() {
        return type;
    }

    public List<WaterSourceMeasurement> getReservoirMeasurements() {
        return waterSourceMeasurements;
    }

    @Override
    public String toString() {
        return "WaterSource{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", capacity=" + capacity +
                ", measurementUnit='" + measurementUnit + '\'' +
                ", type='" + type + '\'' +
                ", waterSourceMeasurements=" + waterSourceMeasurements +
                '}';
    }
}
