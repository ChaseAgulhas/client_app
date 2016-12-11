package com.system.odering.front_end.domain;

import android.test.AndroidTestCase;

import junit.framework.Assert;

/**
 * Created by cfebruary on 2016/12/11.
 */
public class AddressTest extends AndroidTestCase{
    public void testAddress()
    {
        IAddress addressTest = new Address.Builder()
                .streetNumber("123")
                .streetName("Street name")
                .suburb("Suburb")
                .city("City")
                .postCode("Post code")
                .build();

        Assert.assertNotNull(addressTest);
        Assert.assertEquals("123", addressTest.getStreetNumber());
        Assert.assertEquals("Street name", addressTest.getStreetName());
        Assert.assertEquals("Suburb", addressTest.getSuburb());
        Assert.assertEquals("City", addressTest.getCity());
        Assert.assertEquals("Post code", addressTest.getPostCode());
    }
}
