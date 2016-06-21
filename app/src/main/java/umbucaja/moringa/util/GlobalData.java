package umbucaja.moringa.util;

import android.location.Location;

/**
 * Created by jordaoesa on 21/06/2016.
 */
public class GlobalData {
    public static String[] cityNames = null;
    public static Location location = null;

    public static void setCityNames(String[] cityNames){
        GlobalData.cityNames = cityNames;
    }

    public static void setLocation(Location location){
        GlobalData.location = location;
    }

}
