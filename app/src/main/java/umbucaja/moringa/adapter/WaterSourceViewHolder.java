package umbucaja.moringa.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import umbucaja.moringa.MoringaActivity;
import umbucaja.moringa.R;
import umbucaja.moringa.entity.WaterSource;
import umbucaja.moringa.fragments.WaterSourceFragment;

/**
 * Created by Romeryto on 21/06/2016.
 */
public class WaterSourceViewHolder extends RecyclerView.ViewHolder implements WaterSourceViewHolderInterface, View.OnClickListener {

    public Context context;
    public TextView tvName;
    public TextView tvPercentage;
    public TextView tvDate;
    public ProgressBar progressBarPercentage;
    public WaterSource currentWaterSource;
    public ImageView imageViewMoringa;


    public WaterSourceViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        tvName = (TextView) itemView.findViewById(R.id.tv_water_source_name);
        tvPercentage = (TextView) itemView.findViewById(R.id.tv_water_source_percentage);
        tvDate = (TextView) itemView.findViewById(R.id.tv_water_source_last_measurement_date);
        progressBarPercentage = (ProgressBar) itemView.findViewById(R.id.progress_bar_id);
        imageViewMoringa = (ImageView)  itemView.findViewById(R.id.image_test);
       // imageViewMoringa.setImageResource(R.drawable.moringa_terceira);


        imageViewMoringa.setImageDrawable(itemView.getContext().getResources().getDrawable(R.drawable.moringa_terceira));
        //Glide.with(context).load(R.drawable.moringa_terceira).into(imageViewMoringa);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        WaterSourceFragment fragment = WaterSourceFragment.newInstance(currentWaterSource);
        MoringaActivity activity = (MoringaActivity) itemView.getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentView, fragment).addToBackStack(null).commit();

    }
}
