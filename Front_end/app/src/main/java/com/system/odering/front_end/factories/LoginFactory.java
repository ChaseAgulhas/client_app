package com.system.odering.front_end.factories;

import com.system.odering.front_end.domain.Login;

/**
 * Created by cfebruary on 2016/12/10.
 */
public class LoginFactory {
    public static Login getInstance(String username, String password)
    {
        Login login = new Login.Builder()
                .username(username)
                .password(password)
                .build();
        return login;
    }

}
