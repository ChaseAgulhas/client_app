package com.system.odering.front_end.factories.user;

import com.system.odering.front_end.domain.user.Login;

/**
 * Created by cfebruary on 2016/12/10.
 */
public class LoginFactory {
    public static Login getInstance(Long loginId, String username, String password)
    {
        Login login = new Login.Builder()
                .id(loginId)
                .username(username)
                .password(password)
                .build();
        return login;
    }

}
