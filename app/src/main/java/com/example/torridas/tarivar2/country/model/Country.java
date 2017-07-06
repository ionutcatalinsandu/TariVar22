package com.example.torridas.tarivar2.country.model;

/**
 * Created by Torridas on 06-Jul-17.
 */

public class Country {
    private int countryID;
    private String name;

    public Country() {
    }

    public Country(int countryID, String name) {
        this.countryID = countryID;
        this.name = name;
    }

    public int getCountryID() {
        return countryID;
    }

    public String getName() {
        return name;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public void setName(String name) {
        this.name = name;
    }
}
