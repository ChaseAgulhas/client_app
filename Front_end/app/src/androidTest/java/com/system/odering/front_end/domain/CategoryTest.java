package com.system.odering.front_end.domain;

import android.test.AndroidTestCase;

import com.system.odering.front_end.domain.order.Category;
import com.system.odering.front_end.domain.order.ICategory;

import junit.framework.Assert;

/**
 * Created by cfebruary on 2016/12/11.
 */
public class CategoryTest extends AndroidTestCase{
    public void testCategory()
    {
        ICategory categoryTest = new Category.Builder()
                .categoryID("123")
                .categoryName("Vegetarian")
                .build();

        Assert.assertNotNull(categoryTest);
        Assert.assertEquals("123", categoryTest.getCategoryID());
        Assert.assertEquals("Vegetarian", categoryTest.getCategoryName());
    }
}
