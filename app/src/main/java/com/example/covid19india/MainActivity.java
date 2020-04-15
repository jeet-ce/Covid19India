package com.example.covid19india;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;
import java.util.Date;

import adapter.ConnectivityReceiver;
import fragment.CountryFragment;
import fragment.HistoryFragment;
import fragment.InfoFragment;
import fragment.SummaryFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    TextView tvToday;
    String today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (savedInstanceState == null) {
            SummaryFragment summaryFragment = new SummaryFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.flMain, summaryFragment)
                    .commit();
        }
        tvToday = findViewById(R.id.tvDate);
        Date dateNow = Calendar.getInstance().getTime();
        today = (String) DateFormat.format("EEEE", dateNow);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        getToday();

    }

/*    private void showSnackbar(boolean isConnected) {
//        String msg;
//        int color;
//
//        if(isConnected){
//            msg="You are Online...!!";
//            color= Color.WHITE;
//        }else {
//            msg="You are Offline...!!";
//            color=Color.RED;
//        }
    } */
    private void getToday() {
        Date date = Calendar.getInstance().getTime();
        String tanggal = (String) DateFormat.format("d MMMM yyyy", date);
        String formatFix = today + ", " + tanggal;
        tvToday.setText(formatFix);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.summaryMenu:
                SummaryFragment summaryFragment = new SummaryFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flMain, summaryFragment)
                        .commit();
                return true;

            case R.id.summaryIdnMenu:
                CountryFragment countryFragment = new CountryFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flMain, countryFragment)
                        .commit();
                return true;

            case R.id.historyMenu:
                HistoryFragment historyFragment = new HistoryFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flMain, historyFragment)
                        .commit();
                return true;
            case R.id.infoMenu:
                InfoFragment infoFragment = new InfoFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flMain, infoFragment)
                        .commit();
                return true;
        }
        return false;
    }
}