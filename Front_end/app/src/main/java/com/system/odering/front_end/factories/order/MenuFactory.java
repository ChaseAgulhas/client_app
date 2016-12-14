package com.system.odering.front_end.factories.order;


import com.system.odering.front_end.domain.order.Impl.Category;
import com.system.odering.front_end.domain.order.Impl.FoodItem;
import com.system.odering.front_end.domain.order.Impl.Menu;

/**
 * Created by cfebruary on 2016/12/10.
 */
public class MenuFactory {
    public static Menu getInstance(Long id, Category category, FoodItem foodItem)
    {
        Menu menu = new Menu.Builder()
                .id(id)
                .category(category)
                .foodItem(foodItem)
                .build();
        return menu;
    }
}
