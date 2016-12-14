package com.system.odering.front_end.repositories.user;

import android.content.Context;
import android.test.AndroidTestCase;

import com.system.odering.front_end.domain.user.Impl.Login;
import com.system.odering.front_end.repositories.user.Impl.LoginRepositoryImpl;

import junit.framework.Assert;

import java.util.Set;

/**
 * Created by cfebruary on 2016/12/14.
 */
public class LoginRepositoryTest extends AndroidTestCase {
    private Long id;

    public void testCreateReadUpdateDelete() throws Exception {
        Context context = getContext();
        ILoginRepository loginRepository = new LoginRepositoryImpl(context);

        // CREATE
        Login login = new Login.Builder()
                .id(Long.valueOf("1"))
                .username("Test")
                .build();

        Login insertedEntity = loginRepository.save(login);
        id = insertedEntity.getId();
        Assert.assertNotNull(insertedEntity);

        // READ ALL
        Set<Login> businessSet = loginRepository.findAll();
        Assert.assertTrue(businessSet.size() > 0);


        // READ ENTITY
        Login entity = loginRepository.findById(id);
        Assert.assertNotNull(entity);

        // UPDATE ENTITY
        Login updateEntity = new Login.Builder()
                .copy(entity)
                .username("Username")
                .build();
        loginRepository.update(updateEntity);
        Login newEntity = loginRepository.findById(id);
        Assert.assertEquals("Username", newEntity.getUsername());

        // DELETE ENTITY
        loginRepository.delete(updateEntity);
        Login deletedEntity = loginRepository.findById(id);
        Assert.assertNull(deletedEntity);


        // DELETE ALL
        loginRepository.deleteAll();
        Set<Login> deletedUsers = loginRepository.findAll();
        Assert.assertTrue(deletedUsers.size() == 0);


    }
}