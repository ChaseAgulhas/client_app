package com.system.odering.front_end.services.order.Impl;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.system.odering.front_end.domain.order.Menu;
import com.system.odering.front_end.repositories.order.IMenuRepository;
import com.system.odering.front_end.repositories.order.Impl.MenuRepositoryImpl;
import com.system.odering.front_end.services.order.IMenuService;

/**
 * Created by cfebruary on 2016/12/14.
 */
public class MenuServiceImpl extends IntentService implements IMenuService {
    private static final String ACTION_ADD = "com.system.odering.front_end.services.order.Impl.action.ADD";
    private static final String ACTION_UPDATE = "com.system.odering.front_end.services.order.Impl.action.UPDATE";

    private static final String EXTRA_ADD = "com.system.odering.front_end.services.order.Impl.extra.ADD";
    private static final String EXTRA_UPDATE = "com.system.odering.front_end.services.order.Impl.extra.UPDATE";

    private static MenuServiceImpl service = null;

    public static MenuServiceImpl getInstance(){
        if(service == null)
        {
            service = new MenuServiceImpl();
        }

        return service;
    }

    public MenuServiceImpl(){
        super("MenuServiceImpl");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_ADD.equals(action)) {
                final Menu menu = (Menu)intent.getSerializableExtra(EXTRA_ADD);
                saveMenu(menu);
            } else if (ACTION_UPDATE.equals(action)) {
                final Menu menu = (Menu)intent.getSerializableExtra(EXTRA_UPDATE);
                updateMenu(menu);
            }
        }
    }


    private void saveMenu(Menu menu) {
        IMenuRepository menuRepository = new MenuRepositoryImpl(getBaseContext());
        menuRepository.save(menu);
    }


    private void updateMenu(Menu menu) {
        IMenuRepository menuRepository = new MenuRepositoryImpl(getBaseContext());
        menuRepository.update(menu);
    }


    @Override
    public void addMenu(Context context, Menu menu) {
        Intent intent = new Intent(context, MenuServiceImpl.class);
        intent.setAction(ACTION_ADD);
        intent.putExtra(EXTRA_ADD, menu);
        context.startService(intent);
    }

    @Override
    public void updateMenu(Context context, Menu menu) {
        Intent intent = new Intent(context, MenuServiceImpl.class);
        intent.setAction(ACTION_UPDATE);
        intent.putExtra(EXTRA_UPDATE, menu);
        context.startService(intent);
    }

    @Override
    public void deleteMenu(Context context, Menu menu) {

    }
}
