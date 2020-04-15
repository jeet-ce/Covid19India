package viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import api.ApiEndPoint;
import api.ApiService;
import model.SummaryModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jeet Patel on 10/04/2020.
 */
public class SummaryViewModel extends ViewModel {

    private MutableLiveData<SummaryModel> mutableLiveData = new MutableLiveData<>();

    public void setSummaryWorldData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://covid19.mathdro.id")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiEndPoint apiEndpoint = retrofit.create(ApiEndPoint.class);
        Call<SummaryModel> call = apiEndpoint.getSummaryWorld();
        call.enqueue(new Callback<SummaryModel>() {
            @Override
            public void onResponse(Call<SummaryModel> call, Response<SummaryModel> response) {
                mutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<SummaryModel> call, Throwable t) {

            }
        });

    }

    public LiveData<SummaryModel> getSummaryWorldData() {
        return mutableLiveData;
    }
}
