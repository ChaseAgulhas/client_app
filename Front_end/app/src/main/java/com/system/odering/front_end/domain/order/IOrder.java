package com.system.odering.front_end.domain.order;

import com.system.odering.front_end.domain.address.Address;
import com.system.odering.front_end.domain.user.Customer;

/**
 * Created by cfebruary on 2016/11/26.
 */
public interface IOrder {
    Customer getCustomer();
    Address getAddress();
    FoodItem getFoodItem();
}
