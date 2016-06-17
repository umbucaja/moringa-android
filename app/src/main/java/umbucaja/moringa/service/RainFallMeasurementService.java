package umbucaja.moringa.service;

import android.location.Location;

import java.util.Calendar;
import java.util.Collection;

import umbucaja.moringa.entity.City;
import umbucaja.moringa.entity.MeasurementStation;
import umbucaja.moringa.entity.RainFallMeasurement;

/**
 * Created by Andre on 17/06/2016.
 */
public interface RainFallMeasurementService {

    Collection<RainFallMeasurement> getMeasurements(City city);

    Collection<RainFallMeasurement> getMeasurements(City city, Location location);

    Collection<RainFallMeasurement> getMeasurements(MeasurementStation measurementStation);

    Collection<RainFallMeasurement> getMeasurements(City city, Calendar date);

    RainFallMeasurement getMeasurement(MeasurementStation measurementStation, Calendar date);

    RainFallMeasurement getMeasurement(City city, Calendar date, Location location);
}
