package com.system.odering.front_end.repositories.user;

import android.content.Context;
import android.test.AndroidTestCase;

import com.system.odering.front_end.domain.user.Customer;
import com.system.odering.front_end.repositories.user.Impl.CustomerRepositoryImpl;

import junit.framework.Assert;

import java.util.Set;

/**
 * Created by cfebruary on 2016/12/14.
 */
public class CustomerRepositoryTest extends AndroidTestCase {
    private Long id;

    public void testCreateReadUpdateDelete() throws Exception {
        Context context = getContext();
        ICustomerRepository customerRepository = new CustomerRepositoryImpl(context);

        // CREATE
        Customer customer = new Customer.Builder()
                .customerId(Long.valueOf("1"))
                .name("Love")
                .build();

        Customer insertedEntity = customerRepository.save(customer);
        id = insertedEntity.getCustomerId();
        Assert.assertNotNull(insertedEntity);

        // READ ALL
        Set<Customer> businessSet = customerRepository.findAll();
        Assert.assertTrue(businessSet.size() > 0);


        // READ ENTITY
        Customer entity = customerRepository.findById(id);
        Assert.assertNotNull(entity);

        // UPDATE ENTITY
        Customer updateEntity = new Customer.Builder()
                .copy(entity)
                .name("Me")
                .build();
        customerRepository.update(updateEntity);
        Customer newEntity = customerRepository.findById(id);
        Assert.assertEquals("Me", newEntity.getName());

        // DELETE ENTITY
        customerRepository.delete(updateEntity);
        Customer deletedEntity = customerRepository.findById(id);
        Assert.assertNull(deletedEntity);


        // DELETE ALL
        customerRepository.deleteAll();
        Set<Customer> deletedUsers = customerRepository.findAll();
        Assert.assertTrue(deletedUsers.size() == 0);


    }
}