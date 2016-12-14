package com.system.odering.front_end.factories.order;

import com.system.odering.front_end.domain.order.Impl.Beverage;

/**
 * Created by cfebruary on 2016/12/14.
 */
public class BeverageFactory {
    public static Beverage getInstance(Long id, String name, double price, int amountAvailable)
    {
        Beverage beverage = new Beverage.Builder()
                .id(id)
                .name(name)
                .price(price)
                .amountAvailable(amountAvailable)
                .build();
        return beverage;
    }
}
