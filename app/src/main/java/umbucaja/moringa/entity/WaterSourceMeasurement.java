package umbucaja.moringa.entity;

import java.util.Date;

/**
 * Created by Andre on 17/06/2016.
 */
public class WaterSourceMeasurement {

    private long id;
    private float value;
    Date date;

    public WaterSourceMeasurement(long id, float value, Date date) {
        this.id = id;
        this.value = value;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
