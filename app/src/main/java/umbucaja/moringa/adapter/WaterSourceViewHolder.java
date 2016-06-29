package umbucaja.moringa.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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


    public WaterSourceViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        tvName = (TextView) itemView.findViewById(R.id.tv_water_source_name);
        tvPercentage = (TextView) itemView.findViewById(R.id.tv_water_source_percentage);
        tvDate = (TextView) itemView.findViewById(R.id.tv_water_source_last_measurement_date);
        progressBarPercentage = (ProgressBar) itemView.findViewById(R.id.progress_bar_id);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Toast.makeText(view.getContext(), currentWaterSource.getName(), Toast.LENGTH_LONG).show();
        Class fragmentClass = WaterSourceFragment.class;
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //FragmentManager fragmentManager = view.getSupportFragmentManager();
       // fragmentManager.beginTransaction().replace(R.id.fragmentView, fragment).commit();
    }
}
