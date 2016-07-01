package umbucaja.moringa.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import umbucaja.moringa.entity.RainFallMeasurement;

/**
 * Created by jordaoesa on 30/06/2016.
 */
public class ChuvasMedicaoArrayAdapter extends ArrayAdapter{

    private Context context;
    private int resourceId;
    private List<RainFallMeasurement> measurements;

    public ChuvasMedicaoArrayAdapter(Context context, int resource, List<RainFallMeasurement> measurements) {
        super(context, resource);
        this.context = context;
        this.resourceId = resource;
        this.measurements = measurements;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View element = convertView;
        ViewHolder holder = null;
        if(element == null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            element = inflater.inflate(resourceId, parent, false);
            holder = new ViewHolder();
            holder.tvValue.setText(measurements.get(position).getValue()+"");
            holder.tvDayOfWeek.setText("oi?");
            String date = new SimpleDateFormat("dd/MM/yyyy").format(measurements.get(position).getDate());
            holder.tvDate.setText(date);

            checkValuesAndSetImage(measurements.get(position).getValue(), holder);
            element.setTag(holder);
        }else{
            holder = (ViewHolder) element.getTag();
        }

        return element;
    }

    //TODO: precisamos das imagens e dos intervalos para terminar a implementacao!
    private void checkValuesAndSetImage(Float value, ViewHolder holder) {
        /*if(value <= 10)
            holder.ivSituacao.setImageDrawable();
        else if(value <= 50)
            holder.ivSituacao.setImageDrawable();
        else
            holder.ivSituacao.setImageDrawable();*/
    }

    public class ViewHolder{
        public TextView tvValue;
        public TextView tvDayOfWeek;
        public TextView tvDate;
        public ImageView image;
    }
}
