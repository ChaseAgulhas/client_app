package com.system.odering.front_end.domain.address;

/**
 * Created by cfebruary on 2016/11/26.
 */
public interface IAddress {
    String getStreetNumber();
    String getStreetName();
    String getSuburb();
    String getCity();
    String getPostCode();
    String toString();
}
