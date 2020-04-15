package com.example.covid19india;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import adapter.ConnectivityReceiver;
import adapter.HistoryListAdapter;
import adapter.MyApp;
import adapter.StateListAdapter;
import api.ApiEndPoint;
import api.ApiService;
import model.DataModel;
import model.HistoryModel;
import model.StateDataModel;
import model.StateDetailsData;
import model.StateDistrictData;
import model.SummaryModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import viewmodel.HistoryViewModel;
import viewmodel.SummaryViewModel;

/**
 * Created by Jeet Patel on 12/04/2020.
 */
public class StateDetails extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListner{
    private static final String TAG="StateDetails ";
    private ProgressDialog mProgressApp;
    private TextView setStatename,tvToday;
    private String stateName,confirm,deth,recover,lastupdate,today,active;
    private StateListAdapter adapter;
    private static Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_details);

        //Initialize Dialog.............
        dialog=new Dialog(this);
        checkInternetConnection();

    }

    private void checkInternetConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();

        if(isConnected){
            dialog.hide();

            mProgressApp = new ProgressDialog(this);
            mProgressApp.setTitle("Please wait");
            mProgressApp.setCancelable(false);
            mProgressApp.setMessage("Now displaying data ...");
            mProgressApp.show();

            tvToday = findViewById(R.id.tvDate);
            Date dateNow = Calendar.getInstance().getTime();
            today = (String) DateFormat.format("EEEE", dateNow);
            getToday();
            setStatename = findViewById(R.id.setstatename);

            Intent it=getIntent();
            stateName=it.getStringExtra("state");
            confirm=it.getStringExtra("confirmed");
            deth=it.getStringExtra("deaths");
            recover=it.getStringExtra("recovered");
            lastupdate=it.getStringExtra("lastUpdate");
            active=it.getStringExtra("active");

            if(Integer.parseInt(confirm)==0){
                Toast.makeText(this,"No Data Found",Toast.LENGTH_LONG).show();
                mProgressApp.dismiss();
            }

            setStatename.setText("Summary of "+stateName);

            PieChart pieChart = findViewById(R.id.stateSummaryPie);

            List<PieEntry> pieEntries = new ArrayList<>();
            pieEntries.add(new PieEntry(Float.parseFloat(confirm), getResources().getString(R.string.confirmed)));
            pieEntries.add(new PieEntry(Float.parseFloat(recover), getResources().getString(R.string.recovered)));
            pieEntries.add(new PieEntry(Float.parseFloat(deth), getResources().getString(R.string.deaths)));
            pieEntries.add(new PieEntry(Float.parseFloat(active), "Active"));

            PieDataSet pieDataSet = new PieDataSet(pieEntries, getResources().getString(R.string.from_corona));
            pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
            pieDataSet.setValueTextColor(Color.WHITE);
            pieDataSet.setValueTextSize(14);

            Description description = new Description();
            description.setText(getResources().getString(R.string.last_update) + " : " + lastupdate);
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
            pieChart.setHoleRadius(60);
            pieChart.setRotationAngle(130);
            pieChart.setHoleColor(Color.TRANSPARENT);
            pieChart.setData(pieData);
            mProgressApp.dismiss();


            RecyclerView recyclerView = findViewById(R.id.listRecycler);
            adapter = new StateListAdapter(this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
            loadListData();

        }else {
            Toast.makeText(this,"Try to connect",Toast.LENGTH_SHORT).show();
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

    private void getToday() {
        Date date = Calendar.getInstance().getTime();
        String tanggal = (String) DateFormat.format("d MMMM yyyy", date);
        String formatFix = today + ", " + tanggal;
        tvToday.setText(formatFix);
    }

    private void loadListData() {
        mProgressApp.show();
        Log.e(TAG, "loadListData: =============" );
        Retrofit retrofit = ApiService.getRetrofitService();
        ApiEndPoint apiEndpoint = retrofit.create(ApiEndPoint.class);
        Call<List<StateDetailsData>> call = apiEndpoint.getStateData();

        call.enqueue(new Callback<List<StateDetailsData>>() {
            @Override
            public void onResponse(Call<List<StateDetailsData>> call, Response<List<StateDetailsData>> response) {
                Log.e(TAG, "onResponse: Done ==================" );

                for (int i=0;i<response.body().size();i++){
                    if(stateName.compareTo(response.body().get(i).getState()) == 0){
                          ArrayList<StateDataModel> state = new ArrayList<>();
                        Log.e(TAG, "onResponse: In IF==================" );
                          List<StateDistrictData> stateDistrictDataList = response.body().get(i).getDistrictData();
                        for (int j=0;j<stateDistrictDataList.size();j++){
                            Log.e(TAG, "onResponse: In Load District==================" );
                            String districtName =stateDistrictDataList.get(j).getDistrict();
                            Integer confirm=stateDistrictDataList.get(j).getConfirmedinstate();
                            state.add(new StateDataModel(districtName,String.valueOf(confirm)));
                        }
                        Log.e(TAG, "onResponse: To set ADAPTER====" );
                        adapter.setHistoryModels(state);
                        adapter.notifyDataSetChanged();
                        mProgressApp.dismiss();
                        break;
                   }
                }
                mProgressApp.dismiss();
            }

            @Override
            public void onFailure(Call<List<StateDetailsData>> call, Throwable t) {
                Log.e(TAG, "onFailure: Failed===================" );
            }
        });

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
