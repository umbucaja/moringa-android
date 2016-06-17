package umbucaja.moringa.entity;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Andre on 17/06/2016.
 */
public class RainFallMeasurement {
    private long id;
    private Calendar date;
    private float value;
    private String unit;

    public RainFallMeasurement(long id, Calendar date, float value, String unit) {
        this.id = id;
        this.date = date;
        this.value = value;
        this.unit = unit;
    }

    public long getId() {
        return id;
    }

    public Calendar getDate() {
        return date;
    }

    public float getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }
}
