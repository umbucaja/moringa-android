package umbucaja.moringa.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
            String value = measurements.get(position).getValue()+"";
            holder.tvValue.setText(value.replace(".", ","));
            //TODO: unit no banco de dados precisa ser preenchido corretamente
            //holder.tvUnit.setText(measurements.get(position).getUnit());
            String dayOfWeek = new SimpleDateFormat("EE", new Locale("pt", "BR")).format(measurements.get(position).getDate());
            holder.tvDayOfWeek.setText(dayOfWeek);
            String date = new SimpleDateFormat("dd/MM", new Locale("pt", "BR")).format(measurements.get(position).getDate());
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
        if(value == 0f) {
            Picasso.with(holder.ivSituacao.getContext()).load(R.drawable.sol).into(holder.ivSituacao);
        }else if(value <= 10){
            Picasso.with(holder.ivSituacao.getContext()).load(R.drawable.pouquissima_chuva).into(holder.ivSituacao);
        }else if(value <= 25) {
            Picasso.with(holder.ivSituacao.getContext()).load(R.drawable.pouca_chuva).into(holder.ivSituacao);
        }else if(value <= 50){
            Picasso.with(holder.ivSituacao.getContext()).load(R.drawable.muita_chuva).into(holder.ivSituacao);
        }else if(value > 50) {
            Picasso.with(holder.ivSituacao.getContext()).load(R.drawable.toro).into(holder.ivSituacao);
        }
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
