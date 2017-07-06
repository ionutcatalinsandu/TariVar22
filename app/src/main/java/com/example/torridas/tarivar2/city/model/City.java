package com.example.torridas.tarivar2.city.model;

/**
 * Created by Torridas on 06-Jul-17.
 */

public class City {
    private String name;
    private int cityID;
    private int countryOfProvienceID;

    public City() {
    }

    public City(String name, int cityID, int countryOfProvienceID) {

        this.name = name;
        this.cityID = cityID;
        this.countryOfProvienceID = countryOfProvienceID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCityID(int cityID) {
        this.cityID = cityID;
    }

    public void setCountryOfProvienceID(int countryOfProvienceID) {
        this.countryOfProvienceID = countryOfProvienceID;
    }

    public String getName() {

        return name;
    }

    public int getCityID() {
        return cityID;
    }

    public int getCountryOfProvienceID() {
        return countryOfProvienceID;
    }
}
