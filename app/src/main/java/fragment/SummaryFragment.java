package fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.covid19india.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import adapter.ConnectivityReceiver;
import adapter.MyApp;
import model.SummaryModel;
import viewmodel.SummaryViewModel;

/**
 * Created by Jeet Patel on 10/04/2020.
 */

public class SummaryFragment extends Fragment implements ConnectivityReceiver.ConnectivityReceiverListner{
    private ProgressDialog mProgressApp;
    private static View myView;
    private static Dialog dialog;

    public SummaryFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_summary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myView=view;

        //Initialize Dialog.............
        dialog=new Dialog(getContext());
        checkInternetConnection();
    }

    private void checkInternetConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();

        if(isConnected){
            dialog.hide();

            mProgressApp = new ProgressDialog(getActivity());
            mProgressApp.setTitle("Please wait");
            mProgressApp.setCancelable(false);
            mProgressApp.setMessage("Now displaying data ...");
            mProgressApp.show();

            PieChart pieChart = myView.findViewById(R.id.worldSummaryPie);
            SummaryViewModel viewModel = new ViewModelProvider(this,
                    new ViewModelProvider.NewInstanceFactory()).get(SummaryViewModel.class);
            viewModel.setSummaryWorldData();
            viewModel.getSummaryWorldData().observe(this, new Observer<SummaryModel>() {
                @Override
                public void onChanged(SummaryModel ringkasanModel) {
                    mProgressApp.dismiss();
                    List<PieEntry> entries = new ArrayList<>();
                    entries.add(new PieEntry(ringkasanModel.getConfirmed().getValue(), getResources().getString(R.string.confirmed)));
                    entries.add(new PieEntry(ringkasanModel.getRecovered().getValue(), getResources().getString(R.string.recovered)));
                    entries.add(new PieEntry(ringkasanModel.getDeaths().getValue(), getResources().getString(R.string.deaths)));

                    PieDataSet pieDataSet = new PieDataSet(entries, getResources().getString(R.string.from_corona));
                    pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                    pieDataSet.setValueTextColor(Color.WHITE);
                    pieDataSet.setValueTextSize(14);

                    Description description = new Description();
                    description.setText(getResources().getString(R.string.last_update) + " : " + ringkasanModel.getLastUpdate());
                    description.setTextColor(Color.BLACK);
                    description.setTextSize(12);

                    Legend legend = pieChart.getLegend();
                    legend.setTextColor(Color.BLACK);
                    legend.setTextSize(12);
                    legend.setForm(Legend.LegendForm.SQUARE);

                    PieData pieData = new PieData(pieDataSet);
                    pieChart.setVisibility(View.VISIBLE);
                    pieChart.animateXY(2000, 2000);
                    pieChart.setDescription(description);
                    pieChart.setHoleColor(Color.TRANSPARENT);
                    pieChart.setHoleRadius(60);
                    pieChart.setRotationAngle(320);
                    pieChart.setData(pieData);
                }
            });
        }else {
            Toast.makeText(getContext(),"Try to connect",Toast.LENGTH_SHORT).show();
            //Set Content View
            dialog.setContentView(R.layout.alert_dialog);

            //Set out side touch off
            dialog.setCanceledOnTouchOutside(false);

            //Set Dialog Width and Height
            dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);

            //Set Transparent background
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            //set Animation
            dialog.getWindow().getAttributes().windowAnimations= android.R.style.Animation_Dialog;

            //Initialize Dialog variable and Button click event

            Button btTryAgain=dialog.findViewById(R.id.bt_try_again);

            btTryAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Call checkInternetConnection method
                    checkInternetConnection();
                }
            });

            //Show Dialog
            dialog.show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        //Register Connection Status Listner............

        MyApp.getInstance().setConnrctivityListner(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        checkInternetConnection();
    }
}
