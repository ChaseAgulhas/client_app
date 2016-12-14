package com.system.odering.front_end.services.order;

import android.content.Context;
import android.test.AndroidTestCase;

import com.system.odering.front_end.domain.order.Impl.FoodItem;
import com.system.odering.front_end.repositories.order.IFoodItemRepository;
import com.system.odering.front_end.repositories.order.Impl.FoodItemRepositoryImpl;
import com.system.odering.front_end.services.order.Impl.FoodItemServiceImpl;

import junit.framework.Assert;

import java.util.Set;

/**
 * Created by cfebruary on 2016/12/14.
 */
public class FoodItemServiceTest extends AndroidTestCase {

    public void testInsert() throws Exception {

        FoodItemServiceImpl foodItemService = FoodItemServiceImpl.getInstance();
        Context context = getContext();
        IFoodItemRepository foodItemRepository = new FoodItemRepositoryImpl(context);

        FoodItem foodItem = new FoodItem.Builder()
                .id(Long.valueOf("1"))
                .name("Eggs and Bacon")
                .build();

        foodItemService.addFoodItem(this.mContext, foodItem);

        Thread.sleep(5000);
        // READ ALL
        Set<FoodItem> foodItemSet = foodItemRepository.findAll();
        Assert.assertTrue(foodItemSet.size() > 0);
    }
}