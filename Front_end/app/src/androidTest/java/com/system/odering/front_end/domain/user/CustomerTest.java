package com.system.odering.front_end.domain.user;

import android.test.AndroidTestCase;

import com.system.odering.front_end.domain.user.Impl.Customer;

import junit.framework.Assert;

/**
 * Created by cfebruary on 2016/12/11.
 */
public class CustomerTest extends AndroidTestCase{
    public void testCustomer()
    {
        IPerson customerTest = new Customer.Builder()
                .name("Name")
                .surname("Surname")
                .email("Email")
                .phoneNumber("Phone number")
                .build();

        Assert.assertNotNull(customerTest);
        Assert.assertEquals("Name", customerTest.getName());
        Assert.assertEquals("Surname", customerTest.getSurname());
        Assert.assertEquals("Email", customerTest.getEmail());
        Assert.assertEquals("Phone Number", customerTest.getPhoneNumber());
    }
}
