package com.system.odering.front_end.services.address.Impl;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.system.odering.front_end.domain.address.Address;
import com.system.odering.front_end.repositories.address.IAddressRepository;
import com.system.odering.front_end.repositories.address.Impl.AddressRepositoryImpl;
import com.system.odering.front_end.services.address.IAddressService;

/**
 * Created by cfebruary on 2016/12/13.
 */
public class AddressServiceImpl extends IntentService implements IAddressService{
    private static final String ACTION_ADD = "com.system.odering.front_end.services.address.Impl.action.ADD";
    private static final String ACTION_UPDATE = "com.system.odering.front_end.services.address.Impl.action.UPDATE";

    private static final String EXTRA_ADD = "com.system.odering.front_end.services.address.Impl.extra.ADD";
    private static final String EXTRA_UPDATE = "com.system.odering.front_end.services.address.Impl.extra.UPDATE";

    private static AddressServiceImpl service = null;

    public static AddressServiceImpl getInstance(){
        if(service == null)
        {
            service = new AddressServiceImpl();
        }

        return service;
    }

    public AddressServiceImpl(){
        super("AddressServiceImpl");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_ADD.equals(action)) {
                final Address Address = (Address)intent.getSerializableExtra(EXTRA_ADD);
                saveAddress(Address);
            } else if (ACTION_UPDATE.equals(action)) {
                final Address Address = (Address)intent.getSerializableExtra(EXTRA_UPDATE);
                updateAddress(Address);
            }
        }
    }


    private void saveAddress(Address address) {
        IAddressRepository addressRepository = new AddressRepositoryImpl(getBaseContext());
        addressRepository.save(address);
    }


    private void updateAddress(Address address) {
        IAddressRepository addressRepository = new AddressRepositoryImpl(getBaseContext());
        addressRepository.update(address);
    }


    @Override
    public void addAddress(Context context, Address address) {
        Intent intent = new Intent(context, AddressServiceImpl.class);
        intent.setAction(ACTION_ADD);
        intent.putExtra(EXTRA_ADD, address);
        context.startService(intent);
    }

    @Override
    public void updateAddress(Context context, Address address) {
        Intent intent = new Intent(context, AddressServiceImpl.class);
        intent.setAction(ACTION_UPDATE);
        intent.putExtra(EXTRA_UPDATE, address);
        context.startService(intent);
    }

    @Override
    public void deleteAddress(Context context, Address address) {

    }
}
