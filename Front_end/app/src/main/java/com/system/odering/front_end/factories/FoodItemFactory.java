package com.system.odering.front_end.factories;

import com.system.odering.front_end.domain.FoodItem;

/**
 * Created by cfebruary on 2016/09/25.
 */
public class FoodItemFactory {

    public static FoodItem getInstance(String name, double price, int amountAvailable)
    {
        FoodItem foodItem = new FoodItem.Builder()
                .name(name)
                .price(price)
                .amountAvailable(amountAvailable)
                .build();

        return foodItem;
    }
}
