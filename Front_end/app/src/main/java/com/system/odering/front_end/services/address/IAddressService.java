package com.system.odering.front_end.services.address;

import android.content.Context;

import com.system.odering.front_end.domain.address.Impl.Address;

/**
 * Created by cfebruary on 2016/12/13.
 */
public interface IAddressService {
    void addAddress(Context context,Address address);
    void updateAddress(Context context,Address address);
    void deleteAddress(Context context, Address address);
}
