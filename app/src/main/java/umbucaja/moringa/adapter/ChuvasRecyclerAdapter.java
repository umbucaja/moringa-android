package umbucaja.moringa.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import umbucaja.moringa.MoringaActivity;
import umbucaja.moringa.R;
import umbucaja.moringa.entity.MeasurementStation;
import umbucaja.moringa.entity.RainFallMeasurement;
import umbucaja.moringa.fragments.ChuvasEstacaoFragment;

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
        MeasurementStation station = stations.get(position);
        holder.tvNomeEstacao.setText(station.getName());
        List<RainFallMeasurement> measurements = station.getRainFallMeasurements();
        holder.station = stations.get(position);
        if(measurements != null && measurements.size() > 0){
            Collections.sort(measurements, new Comparator<RainFallMeasurement>() {
                @Override
                public int compare(RainFallMeasurement rfm1, RainFallMeasurement rfm2) {
                    return rfm1.getDate().compareTo(rfm2.getDate());
                }
            });
            RainFallMeasurement lastMeasurement = measurements.get(measurements.size()-1);

            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String date = formatter.format(lastMeasurement.getDate());
            Float value = lastMeasurement.getValue();
            //TODO: modificar para usar UNIT
            String unit = lastMeasurement.getUnit();

            holder.tvMilimetragem.setText(String.format("%.1f%s",value,"mm").replace(".", ","));
            holder.tvData.setText(date);


            checkValuesAndSetImage(value, holder);

        }
    }

    //TODO: precisamos das imagens e dos intervalos para terminar a implementacao!
    private void checkValuesAndSetImage(Float value, ChuvasHolder holder) {
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

    @Override
    public int getItemCount() {
        return stations.size();
    }

    public static class ChuvasHolder extends RecyclerView.ViewHolder{

        public ImageView ivSituacao;
        public TextView tvMilimetragem;
        public TextView tvNomeEstacao;
        public TextView tvData;
        public MeasurementStation station;

        public ChuvasHolder(final View itemView) {
            super(itemView);
            ivSituacao = (ImageView) itemView.findViewById(R.id.image_view_chuvas);
            tvMilimetragem = (TextView) itemView.findViewById(R.id.tv_chuvas_milimetragem);
            tvNomeEstacao = (TextView) itemView.findViewById(R.id.tv_chuvas_station_name);
            tvData = (TextView) itemView.findViewById(R.id.tv_chuvas_last_measurement_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    System.out.println("STATION _ ID : "+station.getId());
                    ChuvasEstacaoFragment fragment = ChuvasEstacaoFragment.newInstance(station);
                    MoringaActivity activity = (MoringaActivity)itemView.getContext();

                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentView, fragment).addToBackStack(null).commit();
                }
            });
        }
    }
}
