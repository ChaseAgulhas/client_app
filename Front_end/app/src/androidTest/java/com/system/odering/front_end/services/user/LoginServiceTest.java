package com.system.odering.front_end.services.user;

import android.content.Context;
import android.test.AndroidTestCase;

import com.system.odering.front_end.domain.user.Impl.Login;
import com.system.odering.front_end.repositories.user.ILoginRepository;
import com.system.odering.front_end.repositories.user.Impl.LoginRepositoryImpl;
import com.system.odering.front_end.services.user.Impl.LoginServiceImpl;

import junit.framework.Assert;

import java.util.Set;

/**
 * Created by cfebruary on 2016/12/14.
 */
public class LoginServiceTest extends AndroidTestCase {

    public void testInsert() throws Exception {

        LoginServiceImpl loginService = LoginServiceImpl.getInstance();
        Context context = getContext();
        ILoginRepository loginRepository = new LoginRepositoryImpl(context);

        Login login = new Login.Builder()
                .id(Long.valueOf("1"))
                .username("Username")
                .password("Password")
                .build();

        loginService.addLogin(this.mContext, login);

        Thread.sleep(5000);
        // READ ALL
        Set<Login> loginSet = loginRepository.findAll();
        Assert.assertTrue(loginSet.size() > 0);
    }
}