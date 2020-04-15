package viewmodel;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import adapter.HistoryListAdapter;
import api.ApiEndPoint;
import api.ApiService;
import model.DataModel;
import model.HistoryModel;

import model.Statewise;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Jeet Patel on 11/04/2020.
 */

public class HistoryViewModel extends ViewModel {

    private MutableLiveData<ArrayList<HistoryModel>> mutableLiveData = new MutableLiveData<>();

    public void setTodayData() {
        Log.e("===========", "setTodayData: " );
        Retrofit retrofit = ApiService.getRetrofitService();
        ApiEndPoint apiEndpoint = retrofit.create(ApiEndPoint.class);

        Call<DataModel> call = apiEndpoint.getHistoryList();
        call.enqueue(new Callback<DataModel>() {
            @Override
            public void onResponse(Call<DataModel> call, Response<DataModel> response) {
                Log.e("========= ", " Respone Done ==============");
                List<Statewise> statewise = response.body().getStatewise();
                ArrayList<HistoryModel> shistory = new ArrayList<>();
                for(int i=1;i<statewise.size();i++){
                    String state= statewise.get(i).getState();
                    String lastUpdate = statewise.get(i).getLastupdatedtime();
                    String confirmed = statewise.get(i).getConfirmed();
                    String deaths = statewise.get(i).getDeaths();
                    String recovered = statewise.get(i).getRecovered();
                    String tconfirmed = statewise.get(i).getDeltaconfirmed();
                    String tdeaths = statewise.get(i).getDeltadeaths();
                    String trecovered = statewise.get(i).getDeltarecovered();
                    String active = statewise.get(i).getActive();
                    shistory.add(new HistoryModel(state,lastUpdate,confirmed,deaths,recovered,tconfirmed,tdeaths,trecovered,active));
                }
                mutableLiveData.setValue((ArrayList<HistoryModel>) shistory);
            }

            @Override
            public void onFailure(Call<DataModel> call, Throwable t) {
                Log.e("========= ", " Respone Failed ==============");
            }
        });
    }

    private String getFormattedDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
        return simpleDateFormat.format(today());
    }

    // fetch today
    private Date today() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,0);
        return calendar.getTime();
    }

    public LiveData<ArrayList<HistoryModel>> getTodayListData() {
        return mutableLiveData;
    }

}
