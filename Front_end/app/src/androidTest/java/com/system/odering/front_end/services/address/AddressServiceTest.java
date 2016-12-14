package com.system.odering.front_end.services.address;

import android.content.Context;
import android.test.AndroidTestCase;

import com.system.odering.front_end.domain.address.Impl.Address;
import com.system.odering.front_end.repositories.address.IAddressRepository;
import com.system.odering.front_end.repositories.address.Impl.AddressRepositoryImpl;
import com.system.odering.front_end.services.address.Impl.AddressServiceImpl;

import junit.framework.Assert;

import java.util.Set;

/**
 * Created by cfebruary on 2016/12/14.
 */
public class AddressServiceTest extends AndroidTestCase {

    public void testInsert() throws Exception {

        AddressServiceImpl addressService = AddressServiceImpl.getInstance();
        Context context = getContext();
        IAddressRepository addressRepository = new AddressRepositoryImpl(context);

        Address address = new Address.Builder()
                .id(Long.valueOf("1"))
                .streetNumber("1987")
                .streetName("Rpse")
                .suburb("MP")
                .city("Cape")
                .postCode("456")
                .build();

        addressService.addAddress(this.mContext, address);

        Thread.sleep(5000);
        // READ ALL
        Set<Address> businessSet1 = addressRepository.findAll();
        Assert.assertTrue(businessSet1.size() > 0);
    }
}