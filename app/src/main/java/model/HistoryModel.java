package model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jeet Patel on 11/04/2020.
 */
public class HistoryModel {

    private String state;
    private String lastUpdate;
    private String confirmed;
    private String deaths;
    private String recovered;
    private String tconfirmed;
    private String tdeaths;
    private String trecovered;
    private String active;


    public HistoryModel(String state, String lastUpdate, String confirmed, String deaths, String recovered, String tconfirmed, String tdeaths, String trecovered, String active) {
        this.state = state;
        this.lastUpdate = lastUpdate;
        this.confirmed = confirmed;
        this.deaths = deaths;
        this.recovered = recovered;
        this.tconfirmed = tconfirmed;
        this.tdeaths = tdeaths;
        this.trecovered = trecovered;
        this.active = active;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getTconfirmed() {
        return tconfirmed;
    }

    public void setTconfirmed(String tconfirmed) {
        this.tconfirmed = tconfirmed;
    }

    public String getTdeaths() {
        return tdeaths;
    }

    public void setTdeaths(String tdeaths) {
        this.tdeaths = tdeaths;
    }

    public String getTrecovered() {
        return trecovered;
    }

    public void setTrecovered(String trecovered) {
        this.trecovered = trecovered;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
