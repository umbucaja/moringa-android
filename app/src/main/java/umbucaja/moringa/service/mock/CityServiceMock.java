package umbucaja.moringa.service.mock;

import java.util.Collection;
import java.util.HashSet;

import umbucaja.moringa.entity.City;
import umbucaja.moringa.service.CityService;

/**
 * Created by Andre on 17/06/2016.
 */
public class CityServiceMock implements CityService {

    private static CityServiceMock cityServiceMock;

    private CityServiceMock() {
    }

    public static CityServiceMock getInstance() {
        if (cityServiceMock == null) {
            cityServiceMock = new CityServiceMock();
        }
        return cityServiceMock;
    }

    @Override
    public Collection<City> listCities() {
        Collection<City> cities =  new HashSet<>();
        cities.add(new City(1,"João Pessoa","PB"));
        cities.add(new City(2,"Campina Grande","PB"));
        cities.add(new City(3,"Santa Rita","PB"));
        cities.add(new City(4,"Cajazeiras","PB"));
        cities.add(new City(5,"Catolé do Rocha","PB"));
        return cities;
    }
}
