package umbucaja.moringa.entity;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Andre on 17/06/2016.
 */
public class City {

    private long id;
    private String name;
    private long population;
    private String state;
    private Collection<MeasurementStation> measurementStations;
    private Collection<WaterSource> reservoirs;

    public City(long id, String name, String state) {
        this.id = id;
        this.name = name;
        this.state = state;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public String getState() {
        return state;
    }

    public Collection<MeasurementStation> getMeasurementStations() {
        return measurementStations;
    }

    public void setMeasurementStations(Collection<MeasurementStation> measurementStations) {
        this.measurementStations = measurementStations;
    }

    public Collection<WaterSource> getReservoirs() {
        return reservoirs;
    }

    public void setReservoirs(Collection<WaterSource> reservoirs) {
        this.reservoirs = reservoirs;
    }

    @Override
    public String toString() {
        return name;
    }
}
