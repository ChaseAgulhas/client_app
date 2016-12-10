package com.system.odering.front_end.factories;

import com.system.odering.front_end.domain.Address;

/**
 * Created by cfebruary on 2016/12/10.
 */
public class AddressFactory {
    public static Address getInstance(String streetNumber, String streetName, String suburb, String city, String postCode)
    {
        Address address = new Address.Builder()
                .streetNumber(streetNumber)
                .streetName(streetName)
                .suburb(suburb)
                .city(city)
                .postCode(postCode)
                .build();

        return address;
    }
}
