package com.system.odering.front_end.services.order;

import android.content.Context;

import com.system.odering.front_end.domain.order.Impl.Category;


/**
 * Created by cfebruary on 2016/12/13.
 */
public interface ICategoryService {
    void addCategory(Context context, Category category);
    void updateCategory(Context context,Category category);
    void deleteCategory(Context context, Category category);
}
