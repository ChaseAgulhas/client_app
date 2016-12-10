package com.system.odering.front_end.factories;

import com.system.odering.front_end.domain.Customer;

/**
 * Created by cfebruary on 2016/12/10.
 */
public class CustomerFactory {
    public static Customer getInstance(String name, String surname, String email, String phoneNumber)
    {
        Customer customer = new Customer.Builder()
                .name(name)
                .surname(surname)
                .email(email)
                .phoneNumber(phoneNumber)
                .build();
        return customer;
    }
}
