package com.system.odering.front_end.repositories.order;

import android.content.Context;
import android.test.AndroidTestCase;

import com.system.odering.front_end.domain.order.Impl.Order;
import com.system.odering.front_end.repositories.order.Impl.OrderRepositoryImpl;

import junit.framework.Assert;

import java.util.Set;

/**
 * Created by cfebruary on 2016/12/14.
 */
public class OrderRepository extends AndroidTestCase {
    private Long id;

    public void testCreateReadUpdateDelete() throws Exception {
        Context context = getContext();
        IOrderRepository orderRepository = new OrderRepositoryImpl(context);

        // CREATE
        Order order = new Order.Builder()
                .orderID(Long.valueOf("1456"))
                .build();

        Order insertedEntity = orderRepository.save(order);
        id = insertedEntity.getOrderID();
        Assert.assertNotNull(insertedEntity);

        // READ ALL
        Set<Order> businessSet = orderRepository.findAll();
        Assert.assertTrue(businessSet.size() > 0);


        // READ ENTITY
        Order entity = orderRepository.findById(id);
        Assert.assertNotNull(entity);

        // UPDATE ENTITY
        Order updateEntity = new Order.Builder()
                .copy(entity.getOrderID(), entity.getCustomer(), entity.getAddress(), entity.getFoodItem())
                .orderID(Long.valueOf("9999"))
                .build();
        orderRepository.update(updateEntity);
        Order newEntity = orderRepository.findById(id);
        Assert.assertEquals("9999", newEntity.getOrderID());

        // DELETE ENTITY
        orderRepository.delete(updateEntity);
        Order deletedEntity = orderRepository.findById(id);
        Assert.assertNull(deletedEntity);


        // DELETE ALL
        orderRepository.deleteAll();
        Set<Order> deletedUsers = orderRepository.findAll();
        Assert.assertTrue(deletedUsers.size() == 0);


    }
}