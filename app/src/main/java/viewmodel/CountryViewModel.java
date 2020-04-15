package viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import api.ApiEndPoint;
import api.ApiService;
import model.CountryModel;

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

public class CountryViewModel extends ViewModel {

    private MutableLiveData<CountryModel> mutableLiveData = new MutableLiveData<>();

    public void setCountryData() {
        Retrofit retrofit = ApiService.getRetrofitService();
        ApiEndPoint apiEndpoint = retrofit.create(ApiEndPoint.class);
        Call<DataModel> call = apiEndpoint.getSummaryInd();
        call.enqueue(new Callback<DataModel>() {
            @Override
            public void onResponse(Call<DataModel> call, Response<DataModel> response) {
                Log.e("========= ", " Respone Done ==============");
                List<Statewise> statewise = response.body().getStatewise();
                //information  Position
                int i=0;
                    String state = statewise.get(i).getState();
                    String lastUpdate = statewise.get(i).getLastupdatedtime();
                    Integer confirmed = Integer.parseInt(statewise.get(i).getConfirmed());
                    Integer deaths = Integer.parseInt(statewise.get(i).getDeaths());
                    Integer recovered = Integer.parseInt(statewise.get(i).getRecovered());
                    Integer active = Integer.parseInt(statewise.get(i).getActive());
                    mutableLiveData.setValue(new CountryModel(lastUpdate,confirmed,recovered,deaths,active));

            }

            @Override
            public void onFailure(Call<DataModel> call, Throwable t) {

            }
        });
    }

    public LiveData<CountryModel> getCountryData() {
        return mutableLiveData;
    }
}
