package model;
/**
 * Created by Jeet Patel on 12/04/2020.
 */
public class StateDataModel {
    private String district;
    private String confirmed;
    private String todayConfirm;

    public StateDataModel(String district, String confirmed, String todayConfirm) {
        this.district = district;
        this.confirmed = confirmed;
        this.todayConfirm = todayConfirm;
    }

    public String getTodayConfirm() {
        return todayConfirm;
    }

    public void setTodayConfirm(String todayConfirm) {
        this.todayConfirm = todayConfirm;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }
}
