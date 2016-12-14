package com.system.odering.front_end.services.order.Impl;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.system.odering.front_end.domain.order.Impl.Order;
import com.system.odering.front_end.repositories.order.IOrderRepository;
import com.system.odering.front_end.repositories.order.Impl.OrderRepositoryImpl;
import com.system.odering.front_end.services.order.IOrderService;

/**
 * Created by cfebruary on 2016/12/14.
 */
public class OrderServiceImpl extends IntentService implements IOrderService {
    private static final String ACTION_ADD = "com.system.odering.front_end.services.order.Impl.action.ADD";
    private static final String ACTION_UPDATE = "com.system.odering.front_end.services.order.Impl.action.UPDATE";

    private static final String EXTRA_ADD = "com.system.odering.front_end.services.order.Impl.extra.ADD";
    private static final String EXTRA_UPDATE = "com.system.odering.front_end.services.order.Impl.extra.UPDATE";

    private static OrderServiceImpl service = null;

    public static OrderServiceImpl getInstance(){
        if(service == null)
        {
            service = new OrderServiceImpl();
        }

        return service;
    }

    public OrderServiceImpl(){
        super("OrderServiceImpl");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_ADD.equals(action)) {
                final Order order = (Order)intent.getSerializableExtra(EXTRA_ADD);
                saveOrder(order);
            } else if (ACTION_UPDATE.equals(action)) {
                final Order order = (Order)intent.getSerializableExtra(EXTRA_UPDATE);
                updateOrder(order);
            }
        }
    }


    private void saveOrder(Order order) {
        IOrderRepository orderRepository = new OrderRepositoryImpl(getBaseContext());
        orderRepository.save(order);
    }


    private void updateOrder(Order order) {
        IOrderRepository orderRepository = new OrderRepositoryImpl(getBaseContext());
        orderRepository.update(order);
    }


    @Override
    public void addOrder(Context context, Order order) {
        Intent intent = new Intent(context, OrderServiceImpl.class);
        intent.setAction(ACTION_ADD);
        intent.putExtra(EXTRA_ADD, order);
        context.startService(intent);
    }

    @Override
    public void updateOrder(Context context, Order order) {
        Intent intent = new Intent(context, OrderServiceImpl.class);
        intent.setAction(ACTION_UPDATE);
        intent.putExtra(EXTRA_UPDATE, order);
        context.startService(intent);
    }

    @Override
    public void deleteOrder(Context context, Order order) {

    }
}
