package umbucaja.moringa.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import umbucaja.moringa.R;

/**
 * Created by Romeryto on 21/06/2016.
 */
public class WaterSourceViewHolder extends RecyclerView.ViewHolder {

    public Context context;
    public TextView tvName;
    public TextView tvPercentage;
    public TextView tvDate;


    public WaterSourceViewHolder(Context context, View itemView){
        super(itemView);
        this.context = context;
        tvName = (TextView) itemView.findViewById(R.id.tv_water_source_name);
        tvPercentage = (TextView) itemView.findViewById(R.id.tv_water_source_percentage);
        tvDate = (TextView) itemView.findViewById(R.id.tv_water_source_last_measurement_date);
    }
}
