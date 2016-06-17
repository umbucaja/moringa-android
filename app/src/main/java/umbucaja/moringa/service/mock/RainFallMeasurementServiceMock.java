package umbucaja.moringa.service.mock;

import android.location.Location;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;

import umbucaja.moringa.entity.City;
import umbucaja.moringa.entity.MeasurementStation;
import umbucaja.moringa.entity.RainFallMeasurement;
import umbucaja.moringa.service.CityService;
import umbucaja.moringa.service.RainFallMeasurementService;

/**
 * Created by Andre on 17/06/2016.
 */
public class RainFallMeasurementServiceMock implements RainFallMeasurementService {

    CityService cityService = CityServiceMock.getInstance();

    private static RainFallMeasurementServiceMock rainFallMeasurementServiceMock;

    private RainFallMeasurementServiceMock() {
    }

    public static RainFallMeasurementServiceMock getInstance() {
        if (rainFallMeasurementServiceMock == null) {
            rainFallMeasurementServiceMock = new RainFallMeasurementServiceMock();
        }
        return rainFallMeasurementServiceMock;
    }


    @Override
    public Collection<RainFallMeasurement> getMeasurements(City city) {
        Collection<RainFallMeasurement> measurements = new ArrayList<>();
        for (City c : cityService.listCities()) {
            if (c.equals(city)) {
                mockStations(c);
                for (MeasurementStation ms : c.getMeasurementStations()) {
                    measurements.addAll(ms.getRainFallMeasurements());
                }
                break;
            }
        }
        return measurements;
    }

    @Override
    public Collection<RainFallMeasurement> getMeasurements(City city, Location location) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Collection<RainFallMeasurement> getMeasurements(MeasurementStation measurementStation) {
        mockRainFallMeasurements(measurementStation);
        return measurementStation.getRainFallMeasurements();
    }

    @Override
    public Collection<RainFallMeasurement> getMeasurements(City city, Calendar date) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public RainFallMeasurement getMeasurement(MeasurementStation measurementStation, Calendar date) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public RainFallMeasurement getMeasurement(City city, Calendar date, Location location) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /*
    Mock Data
     */

    private Locale locale = new Locale("pt", "BR");

    private static int stationsMocked = 0;
    private static int measurementsMocked = 0;

    private void mockStations(City city) {
        if (city.getMeasurementStations().isEmpty()) {
            Collection<MeasurementStation> stations = new HashSet<>();

            int min = 1, max = 4;

            int qtty = min+((int)Math.floor(Math.random()*max));
            for(int i = 1; i <= qtty; i++) {
                float latitude = (float) Math.random()*7.0f;
                float longitude = (float) Math.random()*35.0f;
                MeasurementStation station = new MeasurementStation(i,String.format(locale, "Estação %d", ++stationsMocked), latitude, longitude);
                mockRainFallMeasurements(station);
                stations.add(station);
            }
            city.getMeasurementStations().addAll(stations);
        }
    }

    private void mockRainFallMeasurements(MeasurementStation station) {
        if (station.getRainFallMeasurements().isEmpty()) {
            ArrayList<RainFallMeasurement> measurements = new ArrayList<>();

            int min = 1, max = 10;
            int qtty = min+((int)Math.floor(Math.random()*max));
            for (int i = 1; i <= qtty; i++) {
                if (measurements.isEmpty()) {
                    // Add measurement for today
                    Calendar date = Calendar.getInstance();
                    date.setTime(new Date());
                    RainFallMeasurement measurement = new RainFallMeasurement(++measurementsMocked, date, (float) Math.random()*50.0f, "mm");
                    measurements.add(measurement);
                }
                else {
                    // Add measurement for the day before the last measurement
                    RainFallMeasurement lastMeasurement = measurements.get(measurements.size() -1);
                    Calendar lastDate = lastMeasurement.getDate();
                    Calendar date = Calendar.getInstance();
                    long dayMilis = 1000*3600*24;
                    date.setTimeInMillis(lastDate.getTimeInMillis() + dayMilis);
                    RainFallMeasurement measurement = new RainFallMeasurement(++measurementsMocked, date, (float) Math.random()*50.0f, "mm");
                    measurements.add(measurement);
                }
            }
            station.getRainFallMeasurements().addAll(measurements);
        }
    }
}
