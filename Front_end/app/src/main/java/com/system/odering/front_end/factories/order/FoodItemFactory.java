package com.system.odering.front_end.factories.order;

import com.system.odering.front_end.domain.order.FoodItem;

/**
 * Created by cfebruary on 2016/09/25.
 */
public class FoodItemFactory {

    public static FoodItem getInstance(Long id, String name, double price, int amountAvailable)
    {
        FoodItem foodItem = new FoodItem.Builder()
                .id(id)
                .name(name)
                .price(price)
                .amountAvailable(amountAvailable)
                .build();

        return foodItem;
    }
}
