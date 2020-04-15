package model;

import android.app.Activity;

/**
 * Created by Jeet Patel on 10/04/2020.
 */
import com.google.gson.annotations.SerializedName;


public class CountryModel {
    @SerializedName("lastUpdate")
    private String lastUpdate;
    @SerializedName("confirmed")
    private Integer Confirmed;
    @SerializedName("recovered")
    private Integer Recovered;
    @SerializedName("deaths")
    private Integer Deaths;
    private Integer Active;


    public CountryModel(String lastUpdate, Integer confirmed, Integer recovered, Integer deaths,Integer active) {
        this.lastUpdate = lastUpdate;
        Confirmed = confirmed;
        Recovered = recovered;
        Deaths = deaths;
        Active = active;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Integer getConfirmed() {
        return Confirmed;
    }

    public void setConfirmed(Integer confirmed) {
        Confirmed = confirmed;
    }

    public Integer getRecovered() {
        return Recovered;
    }

    public void setRecovered(Integer recovered) {
        Recovered = recovered;
    }

    public Integer getDeaths() {
        return Deaths;
    }

    public void setDeaths(Integer deaths) {
        Deaths = deaths;
    }


    public Integer getActive() {
        return Active;
    }

    public void setActive(Integer active) {
        Active = active;
    }
}
