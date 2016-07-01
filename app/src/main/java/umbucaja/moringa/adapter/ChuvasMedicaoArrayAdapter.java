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
import java.util.Locale;

import umbucaja.moringa.R;
import umbucaja.moringa.entity.RainFallMeasurement;

/**
 * Created by jordaoesa on 30/06/2016.
 */
public class ChuvasMedicaoArrayAdapter extends ArrayAdapter {

    private Context context;
    private int resourceId;
    private List<RainFallMeasurement> measurements;

    public ChuvasMedicaoArrayAdapter(Context context, int resource, List<RainFallMeasurement> measurements) {
        super(context, resource, measurements);
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
            holder = new ViewHolder(element);
            holder.tvValue.setText(measurements.get(position).getValue()+"");
            holder.tvUnit.setText(measurements.get(position).getUnit());
            String dayOfWeek = new SimpleDateFormat("EE", new Locale("pt", "BR")).format(measurements.get(position).getDate());
            holder.tvDayOfWeek.setText(dayOfWeek);
            String date = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR")).format(measurements.get(position).getDate());
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
        if(value == 0f)
            holder.ivSituacao.setImageResource(R.drawable.sol);
        else if(value <= 10)
            holder.ivSituacao.setImageResource(R.drawable.pouquissima_chuva);
        else if(value <= 25)
            holder.ivSituacao.setImageResource(R.drawable.pouca_chuva);
        else if(value <= 50)
            holder.ivSituacao.setImageResource(R.drawable.muita_chuva);
        else if(value > 50)
            holder.ivSituacao.setImageResource(R.drawable.toro);
    }

    public class ViewHolder{
        public TextView tvValue;
        public TextView tvDayOfWeek;
        public TextView tvDate;
        public TextView tvUnit;
        public ImageView ivSituacao;

        public ViewHolder(View element){
            tvValue = (TextView) element.findViewById(R.id.tv_grid_view_chuvas_item_value);
            tvDayOfWeek = (TextView) element.findViewById(R.id.tv_grid_view_chuvas_item_dayofweek);
            tvDate = (TextView) element.findViewById(R.id.tv_grid_view_chuvas_item_date);
            tvUnit = (TextView) element.findViewById(R.id.tv_grid_view_chuvas_item_unit);
            ivSituacao = (ImageView) element.findViewById(R.id.iv_grid_view_chuvas_item);
        }
    }
}
