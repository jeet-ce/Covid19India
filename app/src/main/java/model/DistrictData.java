package model;

import com.google.gson.annotations.SerializedName;
/**
 * Created by Jeet Patel on 12/04/2020.
 */
public class DistrictData {

    @SerializedName("district")
    private String district;
    @SerializedName("confirmed")
    private Integer confirmed;
    @SerializedName("lastupdatedtime")
    private String lastupdatedtime;

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Integer getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Integer confirmed) {
        this.confirmed = confirmed;
    }

    public String getLastupdatedtime() {
        return lastupdatedtime;
    }

    public void setLastupdatedtime(String lastupdatedtime) {
        this.lastupdatedtime = lastupdatedtime;
    }

}
