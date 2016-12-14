package com.system.odering.front_end.domain.order;

import com.system.odering.front_end.domain.address.Impl.Address;
import com.system.odering.front_end.domain.order.Impl.FoodItem;
import com.system.odering.front_end.domain.user.Impl.Customer;

/**
 * Created by cfebruary on 2016/11/26.
 */
public interface IOrder {
    Customer getCustomer();
    Address getAddress();
    FoodItem getFoodItem();
}
