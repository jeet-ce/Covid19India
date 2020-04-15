package model;
/**
 * Created by Jeet Patel on 12/04/2020.
 */
import com.google.gson.annotations.SerializedName;

public class StateDistrictData {
    @SerializedName("district")
    private String district;
    @SerializedName("confirmed")
    private Integer confirmedinstate;

    public Integer getConfirmedinstate() {
        return confirmedinstate;
    }

    public void setConfirmedinstate(Integer confirmedinstate) {
        this.confirmedinstate = confirmedinstate;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

}
