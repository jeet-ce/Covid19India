package model;

import com.google.gson.annotations.SerializedName;

public class DistrictDeltaData {

    @SerializedName("confirmed")
    private Integer confirmed;

    public Integer getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Integer confirmed) {
        this.confirmed = confirmed;
    }
}
