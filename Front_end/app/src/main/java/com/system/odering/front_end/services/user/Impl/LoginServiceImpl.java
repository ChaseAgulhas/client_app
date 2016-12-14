package com.system.odering.front_end.services.user.Impl;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.system.odering.front_end.domain.user.Login;
import com.system.odering.front_end.repositories.user.ILoginRepository;
import com.system.odering.front_end.repositories.user.Impl.LoginRepositoryImpl;
import com.system.odering.front_end.services.user.ILoginService;

/**
 * Created by cfebruary on 2016/12/14.
 */
public class LoginServiceImpl extends IntentService implements ILoginService {
    private static final String ACTION_ADD = "com.system.odering.front_end.services.user.Impl.action.ADD";
    private static final String ACTION_UPDATE = "com.system.odering.front_end.services.user.Impl.action.UPDATE";

    private static final String EXTRA_ADD = "com.system.odering.front_end.services.user.Impl.extra.ADD";
    private static final String EXTRA_UPDATE = "com.system.odering.front_end.services.user.Impl.extra.UPDATE";

    private static LoginServiceImpl service = null;

    public static LoginServiceImpl getInstance(){
        if(service == null)
        {
            service = new LoginServiceImpl();
        }

        return service;
    }

    public LoginServiceImpl(){
        super("LoginServiceImpl");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_ADD.equals(action)) {
                final Login login = (Login)intent.getSerializableExtra(EXTRA_ADD);
                saveLogin(login);
            } else if (ACTION_UPDATE.equals(action)) {
                final Login login = (Login)intent.getSerializableExtra(EXTRA_UPDATE);
                updateLogin(login);
            }
        }
    }


    private void saveLogin(Login login) {
        ILoginRepository loginRepository = new LoginRepositoryImpl(getBaseContext());
        loginRepository.save(login);
    }


    private void updateLogin(Login login) {
        ILoginRepository loginRepository = new LoginRepositoryImpl(getBaseContext());
        loginRepository.update(login);
    }


    @Override
    public void addLogin(Context context, Login login) {
        Intent intent = new Intent(context, LoginServiceImpl.class);
        intent.setAction(ACTION_ADD);
        intent.putExtra(EXTRA_ADD, login);
        context.startService(intent);
    }

    @Override
    public void updateLogin(Context context, Login login) {
        Intent intent = new Intent(context, LoginServiceImpl.class);
        intent.setAction(ACTION_UPDATE);
        intent.putExtra(EXTRA_UPDATE, login);
        context.startService(intent);
    }

    @Override
    public void deleteLogin(Context context, Login login) {

    }
}
