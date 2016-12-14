package com.system.odering.front_end.services.user;

import android.content.Context;

import com.system.odering.front_end.domain.user.Impl.Login;

/**
 * Created by cfebruary on 2016/12/14.
 */
public interface ILoginService {
    void addLogin(Context context, Login login);
    void updateLogin(Context context,Login login);
    void deleteLogin(Context context, Login login);
}
