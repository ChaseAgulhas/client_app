package com.system.odering.front_end.services.order;

import android.content.Context;
import android.test.AndroidTestCase;

import com.system.odering.front_end.domain.order.Category;
import com.system.odering.front_end.repositories.order.ICategoryRepository;
import com.system.odering.front_end.repositories.order.Impl.CategoryRepositoryImpl;
import com.system.odering.front_end.services.order.Impl.CategoryServiceImpl;

import junit.framework.Assert;

import java.util.Set;

/**
 * Created by cfebruary on 2016/12/14.
 */
public class CategoryServiceTest extends AndroidTestCase {

    public void testInsert() throws Exception {

        CategoryServiceImpl categoryService = CategoryServiceImpl.getInstance();
        Context context = getContext();
        ICategoryRepository categoryRepository = new CategoryRepositoryImpl(context);

        Category category = new Category.Builder()
                .categoryID(Long.valueOf("1"))
                .categoryName("Breakfast")
                .build();

        categoryService.addCategory(this.mContext, category);

        Thread.sleep(5000);
        // READ ALL
        Set<Category> categorySet = categoryRepository.findAll();
        Assert.assertTrue(categorySet.size() > 0);
    }
}