package umbucaja.moringa.service.mock;

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

/**
 * Created by Andre on 17/06/2016.
 */
public class CityServiceMock implements CityService {

    private static Collection<City> cities;
    private static CityServiceMock cityServiceMock;

    private CityServiceMock() {
        cities = mockCities();
    }

    public static CityServiceMock getInstance() {
        if (cityServiceMock == null) {
            cityServiceMock = new CityServiceMock();
        }
        return cityServiceMock;
    }

    @Override
    public Collection<City> listCities() {
        return cities;
    }

    /*
    Mock Data
     */

    private Collection<City> mockCities() {
        Collection<City> cities =  new HashSet<>();

        City joaoPessoa =       new City(1,"João Pessoa","PB");
        City campinaGrande =    new City(2,"Campina Grande","PB");
        City santaRita =        new City(3,"Santa Rita","PB");
        City cajazeiras =       new City(4,"Cajazeiras","PB");
        City catoleDoRocha =    new City(5,"Catolé do Rocha","PB");

        cities.add(joaoPessoa);
        cities.add(campinaGrande);
        cities.add(santaRita);
        cities.add(cajazeiras);
        cities.add(catoleDoRocha);
        return cities;
    }
}
