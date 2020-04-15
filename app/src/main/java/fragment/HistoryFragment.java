package fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid19india.R;
import com.example.covid19india.StateDetails;

import java.util.ArrayList;

import adapter.ConnectivityReceiver;
import adapter.HistoryListAdapter;
import adapter.MyApp;
import adapter.StateViewListner;
import model.HistoryModel;
import viewmodel.HistoryViewModel;

/**
 * Created by Jeet Patel on 11/04/2020.
 */

public class HistoryFragment extends Fragment implements StateViewListner,ConnectivityReceiver.ConnectivityReceiverListner{

    private HistoryListAdapter adapter;
    private ProgressDialog mProgressApp;
    private StateViewListner stateViewListner = this;
    private ArrayList<HistoryModel> statedetails;
    private static View myView;
    private static Dialog dialog;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
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

            RecyclerView recyclerView = myView.findViewById(R.id.listRecycler);
            adapter = new HistoryListAdapter(getContext());
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(adapter);
            loadListData();
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

    private void loadListData() {
        Log.e("=========", "loadListData: ==============");
        HistoryViewModel viewModel = new ViewModelProvider(this,
                new ViewModelProvider.NewInstanceFactory()).get(HistoryViewModel.class);
        viewModel.setTodayData();
        mProgressApp.show();
        viewModel.getTodayListData().observe(this, new Observer<ArrayList<HistoryModel>>() {
            @Override
            public void onChanged(ArrayList<HistoryModel> historyModels) {
                statedetails=historyModels;
                Log.e("-========", "onChanged: got data" );
                adapter.setHistoryModels(historyModels,stateViewListner);
                adapter.notifyDataSetChanged();
                mProgressApp.dismiss();
            }
        });
    }

    @Override
    public void onStateItemClick(int position) {
        Intent intent=new Intent(getActivity(), StateDetails.class);
        intent.putExtra("state",statedetails.get(position).getState());
        intent.putExtra("lastUpdate",statedetails.get(position).getLastUpdate());
        intent.putExtra("confirmed",statedetails.get(position).getConfirmed());
        intent.putExtra("deaths",statedetails.get(position).getDeaths());
        intent.putExtra("recovered",statedetails.get(position).getRecovered());
        intent.putExtra("active",statedetails.get(position).getActive());
        startActivity(intent);
    }

    @Override
    public void onStateItemLongClick(int position) {

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

