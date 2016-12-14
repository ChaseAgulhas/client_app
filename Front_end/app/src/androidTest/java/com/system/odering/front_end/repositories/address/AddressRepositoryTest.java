package com.system.odering.front_end.repositories.address;

import android.content.Context;
import android.test.AndroidTestCase;

import com.system.odering.front_end.domain.address.Address;
import com.system.odering.front_end.repositories.address.Impl.AddressRepositoryImpl;

import junit.framework.Assert;

import java.util.Set;

/**
 * Created by cfebruary on 2016/12/14.
 */
public class AddressRepositoryTest extends AndroidTestCase {
    private Long id;

    public void testCreateReadUpdateDelete() throws Exception {
        Context context = getContext();
        IAddressRepository addressRepository = new AddressRepositoryImpl(context);

        // CREATE
        Address address = new Address.Builder()
                .id(Long.valueOf("1654"))
                .streetNumber("123")
                .streetName("Street name")
                .suburb("Suburb")
                .city("City")
                .postCode("Post code")
                .build();

        Address insertedEntity = addressRepository.save(address);
        id = insertedEntity.getId();
        Assert.assertNotNull(insertedEntity);

        // READ ALL
        Set<Address> businessSet = addressRepository.findAll();
        Assert.assertTrue(businessSet.size() > 0);


        // READ ENTITY
        Address entity = addressRepository.findById(id);
        Assert.assertNotNull(entity);

        // UPDATE ENTITY
        Address updateEntity = new Address.Builder()
                .copy(address)
                .streetNumber("456")
                .build();
        addressRepository.update(updateEntity);
        Address newEntity = addressRepository.findById(id);
        Assert.assertEquals("456", newEntity.getStreetNumber());

        // DELETE ENTITY
        addressRepository.delete(updateEntity);
        Address deletedEntity = addressRepository.findById(id);
        Assert.assertNull(deletedEntity);


        // DELETE ALL
        addressRepository.deleteAll();
        Set<Address> deletedUsers = addressRepository.findAll();
        Assert.assertTrue(deletedUsers.size() == 0);


    }
}