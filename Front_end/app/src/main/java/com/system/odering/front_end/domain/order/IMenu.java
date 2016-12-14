package com.system.odering.front_end.domain.order;

import com.system.odering.front_end.domain.order.Impl.Category;
import com.system.odering.front_end.domain.order.Impl.FoodItem;

/**
 * Created by cfebruary on 2016/12/13.
 */
public interface IMenu {
    Long getId();
    Category getCategory();
    FoodItem getFoodItem();
}
