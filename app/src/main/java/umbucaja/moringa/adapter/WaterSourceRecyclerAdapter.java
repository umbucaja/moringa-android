package umbucaja.moringa.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import umbucaja.moringa.R;
import umbucaja.moringa.entity.WaterSource;

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
        holder.tvName.setText(waterSource.getType()+" "+waterSource.getName());
        holder.tvPercentage.setText(waterSource.getPercentage());
        holder.tvDate.setText(waterSource.getDateLastVolume());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return waterSources.size();
    }
}
