package umbucaja.moringa.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.LineChart;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import umbucaja.moringa.MoringaActivity;
import umbucaja.moringa.R;
import umbucaja.moringa.entity.WaterSource;
import umbucaja.moringa.entity.WaterSourceMeasurement;
import umbucaja.moringa.service.Server;
import umbucaja.moringa.util.ImageColor;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WaterSourceFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WaterSourceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WaterSourceFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String WATER_SOURCE_ID = "water_source_id";
    private static final String WATER_SOURCE_NAME = "water_source_name";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private long waterSourceId;
    private String waterSourceName;
    private String mParam2;

    private ProgressBar progressBarWaterSource;
    private TextView tvCurrentWaterSourcePercentage;
    private TextView tvPercentage;
    private TextView tvWaterSourceLastMeasurementDate;
    private TextView tvActualWaterSourceVolume;
    private TextView tvCurrentCapacity;
    private LineChart chartWaterSource;
    private View mainView;
    private static WaterSource waterSource;


    private OnFragmentInteractionListener mListener;

    public WaterSourceFragment() {
        // Required empty public constructor
    }


    public static WaterSourceFragment newInstance(WaterSource waterSource1) {
        WaterSourceFragment fragment = new WaterSourceFragment();
        Bundle args = new Bundle();
        waterSource = waterSource1;

       // args.putLong(WATER_SOURCE_ID, waterSourceId);
        //args.putString(WATER_SOURCE_NAME, waterSourceName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(false);
        ((MoringaActivity)getActivity()).collapsingToolbar.setTitle(waterSource.getName());
        ((MoringaActivity)getActivity()).appBarLayout.setExpanded(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            waterSourceId = getArguments().getLong(WATER_SOURCE_ID);
            waterSourceName =  getArguments().getString(WATER_SOURCE_NAME);
        }
        setHasOptionsMenu(true);
    }

    public void setUp(View view){
        tvCurrentWaterSourcePercentage = (TextView) view.findViewById(R.id.tv_current_water_source_percentage);
        tvPercentage = (TextView) view.findViewById(R.id.tv_percentage);
        tvWaterSourceLastMeasurementDate = (TextView) view.findViewById(R.id.tv_water_source_current_last_measurement_date);
        tvActualWaterSourceVolume = (TextView) view.findViewById(R.id.tv_actual_volume_million_m3);
        tvCurrentCapacity =  (TextView) view.findViewById(R.id.tv_current_capacity);
        progressBarWaterSource = (ProgressBar)  view.findViewById(R.id.progress_bar_watersource);


        float capacity = waterSource.getCapacity();
        List<WaterSourceMeasurement> wsms = waterSource.getReservoirMeasurements();

        float percentage = 0;
        float actualVolume = 0;

        String date = "Sem Medição";
        this.tvCurrentCapacity.setText(String.format("%.1f",capacity/(1000000)).replace(".", ","));
        // holder.currentWaterSource = waterSource;
        if(wsms!=null){
            if(wsms.size()>0){
                actualVolume = wsms.get(wsms.size()-1).getValue();
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                date = formatter.format(wsms.get(wsms.size()-1).getDate());

            }
        }

        if(waterSource != null)
            Server.getInstance(getContext()).getMeasurementsFromWaterSource(view, waterSource);

        LineChart chartWaterSource = (LineChart) view.findViewById(R.id.chartWaterSource);
        chartWaterSource.setNoDataText("Não há registros!");

        percentage = (actualVolume*100)/capacity;
        tvActualWaterSourceVolume.setText(String.format("%.1f",actualVolume/1000000).replace(".", ","));
        if(percentage > 0f) tvCurrentWaterSourcePercentage.setText(String.format("%.1f",percentage).replace(".", ","));
        else{
            tvCurrentWaterSourcePercentage.setText("--");
            tvPercentage.setText("");
            percentage = -1;
        }
        tvWaterSourceLastMeasurementDate.setText(date);
        progressBarWaterSource.setProgress((int)percentage);


        final ImageView imageView = ((MoringaActivity)getActivity()).imageViewLogoTop;
        Glide.with(this).load(R.drawable.logo_top).centerCrop().into(imageView);
        Bitmap icon = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.logo_top);


        //final Animation fadeIn = AnimationUtils.loadAnimation((MoringaActivity)getActivity(), R.anim.fade_in);
       // fadeIn.setDuration(500);
        //imageView.startAnimation(fadeIn);


/*
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
               // Animation fadeOut = AnimationUtils.loadAnimation((MoringaActivity)getActivity(), R.anim.fade_out);
                //imageView.startAnimation(fadeOut);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        */
        System.out.print("PERCENTAGE: "+percentage);
        if(percentage<35){
           Glide.with(this).load(R.drawable.menos_35_v2).centerCrop().into(imageView);
           //Picasso.with(getContext())
                //   .load(R.drawable.menos_35_v2).centerCrop()
                //   .into(imageView);

           icon = BitmapFactory.decodeResource(this.getResources(),
                   R.drawable.menos_35_v2);
       }else if(percentage>=35 && percentage <70){
           Glide.with(this).load(R.drawable.entre_35_69_v2).centerCrop().into(imageView);
           //Picasso.with(getContext())
                  // .load(R.drawable.entre_35_69_v2).centerCrop()
                  // .into(imageView);
           icon = BitmapFactory.decodeResource(this.getResources(),
                   R.drawable.entre_35_69_v2);
       }else{
           Glide.with(this).load(R.drawable.mais70_v2).centerCrop().into(imageView);
           //Picasso.with(getContext())
                  // .load(R.drawable.mais70_v2).centerCrop()
                  // .into(imageView);
           icon = BitmapFactory.decodeResource(this.getResources(),
                   R.drawable.mais70_v2);
       }
        if (Build.VERSION.SDK_INT >= 21) {

            ((MoringaActivity)getActivity()).getWindow().setNavigationBarColor(ImageColor.getDominantColor(icon));
            //getWindow().setStatusBarColor(ImageColor.getDominantColor(icon));
            ((MoringaActivity)getActivity()).collapsingToolbar.setStatusBarScrimColor(ImageColor.getDominantColor(icon));
            ((MoringaActivity)getActivity()).collapsingToolbar.setContentScrimColor(ImageColor.getDominantColor(icon));
            ((MoringaActivity)getActivity()).collapsingToolbar.setStatusBarScrimColor(Color.parseColor("#00000000"));
            ((MoringaActivity)getActivity()).collapsingToolbar.setContentScrimColor(Color.parseColor("#66000000"));
        }
        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_water_source, container, false);
       // mainView = view;
        //LineChart lineChart = (LineChart) view.findViewById(R.id.chartWaterSource);
        this.setUp(view);
        System.out.println("WSID: "+waterSourceId);
      //  Server.getInstance(getContext()).getWaterAllSourcesFromCity2(this, (int)waterSourceId);



        //WaterSource waterSource = waterSources.get(0);
       // view.findViewById(R.id.)




        ((MoringaActivity)getActivity()).collapsingToolbar.setStatusBarScrimColor(Color.parseColor("#00000000"));
        ((MoringaActivity)getActivity()).collapsingToolbar.setContentScrimColor(Color.parseColor("#66000000"));
        ((MoringaActivity)getActivity()).collapsingToolbar.setTitle(waterSource.getName());


        return view;
    }
    private ArrayList<String> getMonths() {

        ArrayList<String> m = new ArrayList<String>();
        m.add("Jan");
        m.add("Feb");
        m.add("Mar");
        m.add("Apr");
        m.add("May");
        m.add("Jun");
        m.add("Jul");
        m.add("Aug");
        m.add("Sep");
        m.add("Okt");
        m.add("Nov");
        m.add("Dec");

        return m;
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public LineChart getChartWaterSource() {
        return chartWaterSource;
    }

    public void setChartWaterSource(LineChart chartWaterSource) {
        this.chartWaterSource = chartWaterSource;
    }

    public TextView getTvCurrentCapacity() {
        return tvCurrentCapacity;
    }

    public void setTvCurrentCapacity(TextView tvCurrentCapacity) {
        this.tvCurrentCapacity = tvCurrentCapacity;
    }

    public TextView getTvActualWaterSourceVolume() {
        return tvActualWaterSourceVolume;
    }

    public void setTvActualWaterSourceVolume(TextView tvActualWaterSourceVolume) {
        this.tvActualWaterSourceVolume = tvActualWaterSourceVolume;
    }

    public TextView getTvWaterSourceLastMeasurementDate() {
        return tvWaterSourceLastMeasurementDate;
    }

    public void setTvWaterSourceLastMeasurementDate(TextView tvWaterSourceLastMeasurementDate) {
        this.tvWaterSourceLastMeasurementDate = tvWaterSourceLastMeasurementDate;
    }

    public TextView getTvCurrentWaterSourcePercentage() {
        return tvCurrentWaterSourcePercentage;
    }

    public void setTvCurrentWaterSourcePercentage(TextView tvCurrentWaterSourcePercentage) {
        this.tvCurrentWaterSourcePercentage = tvCurrentWaterSourcePercentage;
    }

    public ProgressBar getProgressBarWaterSource() {
        return progressBarWaterSource;
    }

    public void setProgressBarWaterSource(ProgressBar progressBarWaterSource) {
        this.progressBarWaterSource = progressBarWaterSource;
    }


    public View getMainView() {
        return mainView;
    }
}
