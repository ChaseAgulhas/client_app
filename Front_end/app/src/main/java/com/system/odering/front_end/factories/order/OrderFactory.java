package com.system.odering.front_end.factories.order;

import com.system.odering.front_end.domain.address.Address;
import com.system.odering.front_end.domain.user.Customer;
import com.system.odering.front_end.domain.order.FoodItem;
import com.system.odering.front_end.domain.order.Order;

/**
 * Created by cfebruary on 2016/12/10.
 */
public class OrderFactory {
    public static Order getInstance(int orderID, Customer customer, Address address, FoodItem foodItem)
    {
        Order order = new Order.Builder()
                .orderID(orderID)
                .customer(customer)
                .address(address)
                .foodItem(foodItem)
                .build();
        return order;
    }
}
