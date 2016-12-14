package com.system.odering.front_end.services.user;

import android.content.Context;
import android.test.AndroidTestCase;

import com.system.odering.front_end.domain.user.Impl.Customer;
import com.system.odering.front_end.repositories.user.ICustomerRepository;
import com.system.odering.front_end.repositories.user.Impl.CustomerRepositoryImpl;
import com.system.odering.front_end.services.user.Impl.CustomerServiceImpl;

import junit.framework.Assert;

import java.util.Set;

/**
 * Created by cfebruary on 2016/12/14.
 */
public class CustomerServiceTest extends AndroidTestCase {

    public void testInsert() throws Exception {

        CustomerServiceImpl customerService = CustomerServiceImpl.getInstance();
        Context context = getContext();
        ICustomerRepository customerRepository = new CustomerRepositoryImpl(context);

        Customer customer = new Customer.Builder()
                .customerId(Long.valueOf("1"))
                .name("Name")
                .surname("Surname")
                .email("Email")
                .phoneNumber("Phone number")
                .build();

        customerService.addCustomer(this.mContext, customer);

        Thread.sleep(5000);
        // READ ALL
        Set<Customer> customerSet = customerRepository.findAll();
        Assert.assertTrue(customerSet.size() > 0);
    }
}