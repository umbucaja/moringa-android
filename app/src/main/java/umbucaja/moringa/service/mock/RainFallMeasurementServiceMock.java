package umbucaja.moringa.service.mock;

import android.location.Location;

import java.util.Collection;
import java.util.Date;

import umbucaja.moringa.entity.City;
import umbucaja.moringa.entity.MeasurementStation;
import umbucaja.moringa.entity.RainFallMeasurement;
import umbucaja.moringa.service.RainFallMeasurementService;

/**
 * Created by Andre on 17/06/2016.
 */
public class RainFallMeasurementServiceMock implements RainFallMeasurementService {
    @Override
    public Collection<RainFallMeasurement> getMeasurements(City city) {
        return null;
    }

    @Override
    public Collection<RainFallMeasurement> getMeasurements(City city, Location location) {
        return null;
    }

    @Override
    public Collection<RainFallMeasurement> getMeasurements(MeasurementStation measurementStation) {
        return null;
    }

    @Override
    public Collection<RainFallMeasurement> getMeasurements(City city, Date date) {
        return null;
    }

    @Override
    public RainFallMeasurement getMeasurement(MeasurementStation measurementStation, Date date) {
        return null;
    }

    @Override
    public RainFallMeasurement getMeasurement(City city, Date date, Location location) {
        return null;
    }
}
