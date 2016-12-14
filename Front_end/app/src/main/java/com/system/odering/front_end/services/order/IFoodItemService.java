package com.system.odering.front_end.services.order;

import android.content.Context;

import com.system.odering.front_end.domain.order.FoodItem;

/**
 * Created by cfebruary on 2016/12/14.
 */
public interface IFoodItemService {
    void addFoodItem(Context context, FoodItem foodItem);
    void updateFoodItem(Context context,FoodItem foodItem);
    void deleteFoodItem(Context context, FoodItem foodItem);
}
