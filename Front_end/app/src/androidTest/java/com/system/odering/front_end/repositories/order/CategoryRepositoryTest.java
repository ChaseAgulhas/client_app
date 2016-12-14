package com.system.odering.front_end.repositories.order;

import android.content.Context;
import android.test.AndroidTestCase;

import com.system.odering.front_end.domain.order.Category;
import com.system.odering.front_end.repositories.order.Impl.CategoryRepositoryImpl;

import junit.framework.Assert;

import java.util.Set;

/**
 * Created by cfebruary on 2016/12/14.
 */
public class CategoryRepositoryTest extends AndroidTestCase {
    private Long id;

    public void testCreateReadUpdateDelete() throws Exception {
        Context context = getContext();
        ICategoryRepository categoryRepository = new CategoryRepositoryImpl(context);

        // CREATE
        Category category = new Category.Builder()
                .categoryID(Long.valueOf("1"))
                .categoryName("Gatsby")
                .build();

        Category insertedEntity = categoryRepository.save(category);
        id = insertedEntity.getCategoryID();
        Assert.assertNotNull(insertedEntity);

        // READ ALL
        Set<Category> businessSet = categoryRepository.findAll();
        Assert.assertTrue(businessSet.size() > 0);


        // READ ENTITY
        Category entity = categoryRepository.findById(id);
        Assert.assertNotNull(entity);

        // UPDATE ENTITY
        Category updateEntity = new Category.Builder()
                .copy(entity)
                .categoryName("Sandwich")
                .build();
        categoryRepository.update(updateEntity);
        Category newEntity = categoryRepository.findById(id);
        Assert.assertEquals("Sandwich", newEntity.getCategoryName());

        // DELETE ENTITY
        categoryRepository.delete(updateEntity);
        Category deletedEntity = categoryRepository.findById(id);
        Assert.assertNull(deletedEntity);


        // DELETE ALL
        categoryRepository.deleteAll();
        Set<Category> deletedUsers = categoryRepository.findAll();
        Assert.assertTrue(deletedUsers.size() == 0);


    }
}