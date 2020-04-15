package model;

import com.google.gson.annotations.SerializedName;
/**
 * Created by Jeet Patel on 12/04/2020.
 */
import java.util.List;

public class StateDetailsData {
    @SerializedName("state")
    private String state;
    @SerializedName("districtData")
    private List<StateDistrictData> districtData = null;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<StateDistrictData> getDistrictData() {
        return districtData;
    }

    public void setDistrictData(List<StateDistrictData> districtData) {
        this.districtData = districtData;
    }
}
