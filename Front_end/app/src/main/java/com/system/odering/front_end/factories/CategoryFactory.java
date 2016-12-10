package com.system.odering.front_end.factories;

import com.system.odering.front_end.domain.Category;

/**
 * Created by cfebruary on 2016/12/10.
 */
public class CategoryFactory {
    public static Category getInstance(String categoryID, String categoryName)
    {
        Category category = new Category.Builder()
                .categoryID(categoryID)
                .categoryName(categoryName)
                .build();

        return category;
    }
}
