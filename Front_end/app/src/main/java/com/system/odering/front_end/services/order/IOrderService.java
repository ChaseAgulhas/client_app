package com.system.odering.front_end.services.order;

import android.content.Context;

import com.system.odering.front_end.domain.order.Impl.Order;


/**
 * Created by cfebruary on 2016/12/14.
 */
public interface IOrderService {
    void addOrder(Context context, Order order);
    void updateOrder(Context context,Order order);
    void deleteOrder(Context context, Order order);
}
