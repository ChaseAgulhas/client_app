package com.system.odering.front_end.services.order.Impl;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.system.odering.front_end.domain.order.Impl.Beverage;
import com.system.odering.front_end.repositories.order.IBeverageRepository;
import com.system.odering.front_end.repositories.order.Impl.BeverageRepositoryImpl;
import com.system.odering.front_end.services.order.IBeverageService;

/**
 * Created by cfebruary on 2016/12/14.
 */
public class BeverageServiceImpl extends IntentService implements IBeverageService {
    private static final String ACTION_ADD = "com.system.odering.front_end.services.order.Impl.action.ADD";
    private static final String ACTION_UPDATE = "com.system.odering.front_end.services.order.Impl.action.UPDATE";

    private static final String EXTRA_ADD = "com.system.odering.front_end.services.order.Impl.extra.ADD";
    private static final String EXTRA_UPDATE = "com.system.odering.front_end.services.order.Impl.extra.UPDATE";

    private static BeverageServiceImpl service = null;

    public static BeverageServiceImpl getInstance(){
        if(service == null)
        {
            service = new BeverageServiceImpl();
        }

        return service;
    }

    public BeverageServiceImpl(){
        super("BeverageServiceImpl");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_ADD.equals(action)) {
                final Beverage beverage = (Beverage)intent.getSerializableExtra(EXTRA_ADD);
                saveBeverage(beverage);
            } else if (ACTION_UPDATE.equals(action)) {
                final Beverage beverage = (Beverage)intent.getSerializableExtra(EXTRA_UPDATE);
                updateBeverage(beverage);
            }
        }
    }


    private void saveBeverage(Beverage beverage) {
        IBeverageRepository beverageRepository = new BeverageRepositoryImpl(getBaseContext());
        beverageRepository.save(beverage);
    }


    private void updateBeverage(Beverage beverage) {
        IBeverageRepository beverageRepository = new BeverageRepositoryImpl(getBaseContext());
        beverageRepository.update(beverage);
    }


    @Override
    public void addBeverage(Context context, Beverage beverage) {
        Intent intent = new Intent(context, BeverageServiceImpl.class);
        intent.setAction(ACTION_ADD);
        intent.putExtra(EXTRA_ADD, beverage);
        context.startService(intent);
    }

    @Override
    public void updateBeverage(Context context, Beverage beverage) {
        Intent intent = new Intent(context, BeverageServiceImpl.class);
        intent.setAction(ACTION_UPDATE);
        intent.putExtra(EXTRA_UPDATE, beverage);
        context.startService(intent);
    }

    @Override
    public void deleteBeverage(Context context, Beverage beverage) {

    }
}
