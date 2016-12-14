package com.system.odering.front_end.repositories.order;

import android.content.Context;
import android.test.AndroidTestCase;

import com.system.odering.front_end.domain.order.Impl.Beverage;
import com.system.odering.front_end.repositories.order.Impl.BeverageRepositoryImpl;

import junit.framework.Assert;

import java.util.Set;

/**
 * Created by cfebruary on 2016/12/14.
 */
public class BeverageRepositoryTest extends AndroidTestCase {
    private Long id;

    public void testCreateReadUpdateDelete() throws Exception {
        Context context = getContext();
        IBeverageRepository beverageRepository = new BeverageRepositoryImpl(context);

        // CREATE
        Beverage beverage = new Beverage.Builder()
                .id(Long.valueOf("1"))
                .name("Gatsby")
                .build();

        Beverage insertedEntity = beverageRepository.save(beverage);
        id = insertedEntity.getId();
        Assert.assertNotNull(insertedEntity);

        // READ ALL
        Set<Beverage> businessSet = beverageRepository.findAll();
        Assert.assertTrue(businessSet.size() > 0);


        // READ ENTITY
        Beverage entity = beverageRepository.findById(id);
        Assert.assertNotNull(entity);

        // UPDATE ENTITY
        Beverage updateEntity = new Beverage.Builder()
                .copy(entity)
                .name("Sandwich")
                .build();
        beverageRepository.update(updateEntity);
        Beverage newEntity = beverageRepository.findById(id);
        Assert.assertEquals("Sandwich", newEntity.getName());

        // DELETE ENTITY
        beverageRepository.delete(updateEntity);
        Beverage deletedEntity = beverageRepository.findById(id);
        Assert.assertNull(deletedEntity);


        // DELETE ALL
        beverageRepository.deleteAll();
        Set<Beverage> deletedUsers = beverageRepository.findAll();
        Assert.assertTrue(deletedUsers.size() == 0);


    }
}