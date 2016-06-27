package umbucaja.moringa.util;

import android.location.Location;

import umbucaja.moringa.entity.City;

/**
 * Created by jordaoesa on 21/06/2016.
 */
public class GlobalData {
    public static City[] cities = null;
    public static Location location = null;
    public static City currCity = null;

    public static void setCities(City[] cities) { GlobalData.cities = cities; }

    public static void setLocation(Location location){
        GlobalData.location = location;
    }

    public static void setCurrCity(City currCity) {
        GlobalData.currCity = currCity;
    }
}
