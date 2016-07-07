package umbucaja.moringa.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import umbucaja.moringa.R;
import umbucaja.moringa.entity.WaterSource;
import umbucaja.moringa.entity.WaterSourceMeasurement;

/**
 * Created by Romeryto on 21/06/2016.
 */
public class WaterSourceRecyclerAdapter extends RecyclerView.Adapter<WaterSourceViewHolder>{

    private List<WaterSource> waterSources;
    private Context context;

    public WaterSourceRecyclerAdapter(Context context, List<WaterSource> waterSources){
        this.context = context;
        this.waterSources = waterSources;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public WaterSourceViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_water_source, parent, false);

        WaterSourceViewHolder viewHolder = new WaterSourceViewHolder(context, view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WaterSourceViewHolder holder, int position) {
        WaterSource waterSource = waterSources.get(position);
       // Toast.makeText(holder.context, waterSource.getName(), Toast.LENGTH_LONG).show();
        holder.tvName.setText(waterSource.getType()+" "+waterSource.getName());
        float capacity = waterSource.getCapacity();
        List<WaterSourceMeasurement> wsms = waterSource.getReservoirMeasurements();
        float percentage = 0;

        float actualVolume = 0;
        String date = "Sem Medição";
        holder.currentWaterSource = waterSource;
        if(wsms!=null){
            if(wsms.size()>0){
                actualVolume = wsms.get(wsms.size()-1).getValue();
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                date = formatter.format(wsms.get(wsms.size()-1).getDate());

            }else{

            }
        }
        percentage = (actualVolume*100)/capacity;
        if(percentage > 0f) holder.tvPercentage.setText(String.format("%.1f%s",percentage,"%").replace(".", ","));
        else holder.tvPercentage.setText("--");
        holder.tvDate.setText(date);
        holder.progressBarPercentage.setProgress((int)percentage);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return waterSources.size();
    }



}
