package com.system.odering.front_end.services.order;

import android.content.Context;
import android.test.AndroidTestCase;

import com.system.odering.front_end.domain.address.Impl.Address;
import com.system.odering.front_end.domain.order.Impl.FoodItem;
import com.system.odering.front_end.domain.order.Impl.Order;
import com.system.odering.front_end.domain.user.Impl.Customer;
import com.system.odering.front_end.factories.address.AddressFactory;
import com.system.odering.front_end.factories.order.FoodItemFactory;
import com.system.odering.front_end.factories.user.CustomerFactory;
import com.system.odering.front_end.repositories.order.IOrderRepository;
import com.system.odering.front_end.repositories.order.Impl.OrderRepositoryImpl;
import com.system.odering.front_end.services.order.Impl.OrderServiceImpl;

import junit.framework.Assert;

import java.util.Set;

/**
 * Created by cfebruary on 2016/12/14.
 */
public class OrderServiceTest extends AndroidTestCase {

    public void testInsert() throws Exception {

        OrderServiceImpl orderService = OrderServiceImpl.getInstance();
        Context context = getContext();
        IOrderRepository orderRepository = new OrderRepositoryImpl(context);

        Customer customer = CustomerFactory.getInstance(Long.valueOf("234"), "Name", "Surname", "Email", "Phone");
        Address address = AddressFactory.getInstance(Long.valueOf("123"), "543", "Street", "Suburb", "City", "Post code");
        FoodItem foodItem = FoodItemFactory.getInstance(Long.valueOf("345345"), "Chips", 465, 12);

        Order order = new Order.Builder()
                .orderID(Long.valueOf("1"))
                .customer(customer)
                .address(address)
                .foodItem(foodItem)
                .build();

        orderService.addOrder(this.mContext, order);

        Thread.sleep(5000);
        // READ ALL
        Set<Order> orderSet = orderRepository.findAll();
        Assert.assertTrue(orderSet.size() > 0);
    }
}