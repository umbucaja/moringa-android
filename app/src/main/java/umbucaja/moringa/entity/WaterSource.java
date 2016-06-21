package umbucaja.moringa.entity;

import java.util.Collection;

/**
 * Created by Andre on 17/06/2016.
 */
public class WaterSource {
    private long id;
    private String name;
    private float capacity;
    private String measurementUnit;
    private String type;
    private String percentage;
    private String actualVolume;
    private String dateLastVolume;
    private Collection<WaterSourceMeasurement> reservoirMeasurements;


    public WaterSource(long id, String name, float capacity, String measurementUnit, String type, String percentage, String actualVolume, String dateLastVolume) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.measurementUnit = measurementUnit;
        this.type = type;
        this.percentage = percentage;
        this.actualVolume = actualVolume;
        this.dateLastVolume = dateLastVolume;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getActualVolume() {
        return actualVolume;
    }

    public void setActualVolume(String actualVolume) {
        this.actualVolume = actualVolume;
    }

    public String getDateLastVolume() {
        return dateLastVolume;
    }

    public void setDateLastVolume(String dateLastVolume) {
        this.dateLastVolume = dateLastVolume;
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

    public Collection<WaterSourceMeasurement> getReservoirMeasurements() {
        return reservoirMeasurements;
    }
}
