package com.system.odering.front_end.model;

/**
 * Created by JasonMckay on 09-Nov-16.
 */

public class Address {
    private long custId;
    private String userId;
    private String streetNumber;
    private String streetname;
    private String suburb;
    private String city;

    public Address() {
    }

    public Address(String userId, String streetNumber, String streetname, String suburb, String city) {
        this.userId = userId;
        this.streetNumber = streetNumber;
        this.streetname = streetname;
        this.suburb = suburb;
        this.city = city;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getCustId() {
        return custId;
    }

    public void setCustId(long custId) {
        this.custId = custId;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getStreetname() {
        return streetname;
    }

    public void setStreetname(String streetname) {
        this.streetname = streetname;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
