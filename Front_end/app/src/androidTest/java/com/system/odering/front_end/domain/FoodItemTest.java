package com.system.odering.front_end.domain;

import android.test.AndroidTestCase;

import junit.framework.Assert;

/**
 * Created by cfebruary on 2016/12/11.
 */
public class FoodItemTest extends AndroidTestCase{
    public void testFoodItem()
    {
        IProduct foodItemTest = new FoodItem.Builder()
                .name("Product name")
                .price(100)
                .amountAvailable(10)
                .build();

        Assert.assertNotNull(foodItemTest);
        Assert.assertEquals("Product name", foodItemTest.getName());
        Assert.assertEquals(100, foodItemTest.getPrice());
        Assert.assertEquals(10, foodItemTest.getAmountAvailable());
    }
}
