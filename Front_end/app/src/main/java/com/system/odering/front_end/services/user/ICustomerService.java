package com.system.odering.front_end.services.user;

import android.content.Context;

import com.system.odering.front_end.domain.user.Impl.Customer;

/**
 * Created by cfebruary on 2016/12/14.
 */
public interface ICustomerService {
    void addCustomer(Context context, Customer customer);
    void updateCustomer(Context context,Customer customer);
    void deleteCustomer(Context context, Customer customer);
}
