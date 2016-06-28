package umbucaja.moringa.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import umbucaja.moringa.R;
import umbucaja.moringa.entity.MeasurementStation;

/**
 * Created by jordaoesa on 28/06/2016.
 */
public class ChuvasRecyclerAdapter extends RecyclerView.Adapter<ChuvasRecyclerAdapter.ChuvasHolder> {


    private List<MeasurementStation> stations;

    public ChuvasRecyclerAdapter(List<MeasurementStation> stations){
        this.stations = stations;
    }

    @Override
    public ChuvasHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_chuvas, parent, false);
        return new ChuvasHolder(v);
    }

    @Override
    public void onBindViewHolder(ChuvasHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return stations.size();
    }

    public static class ChuvasHolder extends RecyclerView.ViewHolder{
        public ChuvasHolder(View itemView) {
            super(itemView);
        }
    }
}
