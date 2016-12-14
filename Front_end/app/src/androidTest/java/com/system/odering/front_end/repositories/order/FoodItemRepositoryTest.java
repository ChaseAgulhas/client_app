package com.system.odering.front_end.repositories.order;

import android.content.Context;
import android.test.AndroidTestCase;

import com.system.odering.front_end.domain.order.Impl.FoodItem;
import com.system.odering.front_end.repositories.order.Impl.FoodItemRepositoryImpl;

import junit.framework.Assert;

import java.util.Set;

/**
 * Created by cfebruary on 2016/12/14.
 */
public class FoodItemRepositoryTest extends AndroidTestCase {
    private Long id;

    public void testCreateReadUpdateDelete() throws Exception {
        Context context = getContext();
        IFoodItemRepository foodItemRepository = new FoodItemRepositoryImpl(context);

        // CREATE
        FoodItem foodItem = new FoodItem.Builder()
                .id(Long.valueOf("1"))
                .name("Chicken roll")
                .build();

        FoodItem insertedEntity = foodItemRepository.save(foodItem);
        id = insertedEntity.getId();
        Assert.assertNotNull(insertedEntity);

        // READ ALL
        Set<FoodItem> businessSet = foodItemRepository.findAll();
        Assert.assertTrue(businessSet.size() > 0);


        // READ ENTITY
        FoodItem entity = foodItemRepository.findById(id);
        Assert.assertNotNull(entity);

        // UPDATE ENTITY
        FoodItem updateEntity = new FoodItem.Builder()
                .copy(entity)
                .name("Boerewors roll")
                .build();
        foodItemRepository.update(updateEntity);
        FoodItem newEntity = foodItemRepository.findById(id);
        Assert.assertEquals("Boerewors roll", newEntity.getName());

        // DELETE ENTITY
        foodItemRepository.delete(updateEntity);
        FoodItem deletedEntity = foodItemRepository.findById(id);
        Assert.assertNull(deletedEntity);


        // DELETE ALL
        foodItemRepository.deleteAll();
        Set<FoodItem> deletedUsers = foodItemRepository.findAll();
        Assert.assertTrue(deletedUsers.size() == 0);


    }
}