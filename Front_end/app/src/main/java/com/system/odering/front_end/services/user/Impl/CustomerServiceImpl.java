package com.system.odering.front_end.services.user.Impl;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.system.odering.front_end.domain.user.Impl.Customer;
import com.system.odering.front_end.repositories.user.ICustomerRepository;
import com.system.odering.front_end.repositories.user.Impl.CustomerRepositoryImpl;
import com.system.odering.front_end.services.user.ICustomerService;


/**
 * Created by cfebruary on 2016/12/14.
 */
public class CustomerServiceImpl extends IntentService implements ICustomerService {
    private static final String ACTION_ADD = "com.system.odering.front_end.services.user.Impl.action.ADD";
    private static final String ACTION_UPDATE = "com.system.odering.front_end.services.user.Impl.action.UPDATE";

    private static final String EXTRA_ADD = "com.system.odering.front_end.services.user.Impl.extra.ADD";
    private static final String EXTRA_UPDATE = "com.system.odering.front_end.services.user.Impl.extra.UPDATE";

    private static CustomerServiceImpl service = null;

    public static CustomerServiceImpl getInstance(){
        if(service == null)
        {
            service = new CustomerServiceImpl();
        }

        return service;
    }

    public CustomerServiceImpl(){
        super("CustomerServiceImpl");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_ADD.equals(action)) {
                final Customer customer = (Customer)intent.getSerializableExtra(EXTRA_ADD);
                saveCustomer(customer);
            } else if (ACTION_UPDATE.equals(action)) {
                final Customer customer = (Customer)intent.getSerializableExtra(EXTRA_UPDATE);
                updateCustomer(customer);
            }
        }
    }


    private void saveCustomer(Customer customer) {
        ICustomerRepository customerRepository = new CustomerRepositoryImpl(getBaseContext());
        customerRepository.save(customer);
    }


    private void updateCustomer(Customer customer) {
        ICustomerRepository customerRepository = new CustomerRepositoryImpl(getBaseContext());
        customerRepository.update(customer);
    }


    @Override
    public void addCustomer(Context context, Customer customer) {
        Intent intent = new Intent(context, CustomerServiceImpl.class);
        intent.setAction(ACTION_ADD);
        intent.putExtra(EXTRA_ADD, customer);
        context.startService(intent);
    }

    @Override
    public void updateCustomer(Context context, Customer customer) {
        Intent intent = new Intent(context, CustomerServiceImpl.class);
        intent.setAction(ACTION_UPDATE);
        intent.putExtra(EXTRA_UPDATE, customer);
        context.startService(intent);
    }

    @Override
    public void deleteCustomer(Context context, Customer customer) {

    }
}
