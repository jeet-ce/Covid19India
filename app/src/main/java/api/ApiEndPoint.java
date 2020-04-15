package api;

import com.example.covid19india.StateDetails;

import java.util.List;

import model.DataModel;
import model.StateDetailsData;
import model.SummaryModel;

import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiEndPoint {

    @GET(Api.END_POINT_WORLD_HISTORY)
    Call<DataModel> getHistoryList();

    @GET(Api.END_POINT_SUMMARY_WORLD)
    Call<SummaryModel> getSummaryWorld();

    @GET(Api.END_POINT_IDN)
    Call<DataModel> getSummaryInd();

    @GET(Api.END_POINT_STATE)
    Call<List<StateDetailsData>> getStateData();

}
