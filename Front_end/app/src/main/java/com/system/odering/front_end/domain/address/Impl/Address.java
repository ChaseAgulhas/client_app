package com.system.odering.front_end.domain.address.Impl;

import com.system.odering.front_end.domain.address.IAddress;

import java.io.Serializable;

/**
 * Created by cfebruary on 2016/09/25.
 */
public class Address implements Serializable, IAddress {

    private Long id;
    private String streetNumber;
    private String streetName;
    private String suburb;
    private String city;
    private String postCode;

    private Address(Builder builder)
    {
        this.id = builder.id;
        this.streetNumber = builder.streetNumber;
        this.streetName = builder.streetName;
        this.suburb = builder.suburb;
        this.city = builder.city;
        this.postCode = builder.postCode;
    }

    public Long getId(){return id;}

    public String getStreetNumber() {
        return streetNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getSuburb() {
        return suburb;
    }

    public String getCity() {
        return city;
    }

    public String getPostCode() {
        return postCode;
    }

    public String toString()
    {
        return "Street number: " + getStreetNumber()
                +"\nStreet name: " + getStreetName()
                +"\nSuburb: " + getSuburb()
                +"\nCity: " + getCity()
                +"\nPostcode: " + getPostCode();
    }

    public static class Builder
    {
        private Long id;
        private String streetNumber;
        private String streetName;
        private String suburb;
        private String city;
        private String postCode;

        public Builder(){}

        public Builder id(Long id){
            this.id = id;
            return this;
        }

        public Builder streetNumber(String streetNumber)
        {
            this.streetNumber = streetNumber;
            return this;
        }

        public Builder streetName(String streetName)
        {
            this.streetName = streetName;
            return this;
        }

        public Builder suburb(String suburb)
        {
            this.suburb = suburb;
            return this;
        }

        public Builder city(String city)
        {
            this.city = city;
            return this;
        }

        public Builder postCode(String postCode)
        {
            this.postCode = postCode;
            return this;
        }

        public Builder copy(Address address)
        {
            this.id = address.getId();
            this.streetNumber = address.getStreetNumber();
            this.streetName = address.getStreetName();
            this.suburb = address.getSuburb();
            this.city = address.getCity();
            this.postCode = address.getPostCode();

            return this;
        }

        public Address build()
        {
            return new Address(this);
        }
    }
}
