package com.system.odering.front_end.factories.order;

import com.system.odering.front_end.domain.order.Category;

/**
 * Created by cfebruary on 2016/12/10.
 */
public class CategoryFactory {
    public static Category getInstance(Long categoryID, String categoryName)
    {
        Category category = new Category.Builder()
                .categoryID(categoryID)
                .categoryName(categoryName)
                .build();

        return category;
    }
}
