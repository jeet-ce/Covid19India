package model;
/**
 * Created by Jeet Patel on 12/04/2020.
 */
public class StateDataModel {
    private String district;
    private String confirmed;

    public StateDataModel(String district, String confirmed) {
        this.district = district;
        this.confirmed = confirmed;
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
