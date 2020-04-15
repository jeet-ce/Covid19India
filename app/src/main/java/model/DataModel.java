package model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Jeet Patel on 11/04/2020.
 */

public class DataModel {
    @SerializedName("statewise")
    private List<Statewise> statewise = null;

    public List<Statewise> getStatewise() {
        return statewise;
    }

    public void setStatewise(List<Statewise> statewise) {
        this.statewise = statewise;
    }
}
