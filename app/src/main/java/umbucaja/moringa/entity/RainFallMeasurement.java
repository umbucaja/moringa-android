package umbucaja.moringa.entity;

import java.util.Date;

/**
 * Created by Andre on 17/06/2016.
 */
public class RainFallMeasurement {
    private long id;
    private Date date;
    private float value;
    private String unit;

    public RainFallMeasurement(long id, Date date, float value, String unit) {
        this.id = id;
        this.date = date;
        this.value = value;
        this.unit = unit;
    }

    public long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public float getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }
}
