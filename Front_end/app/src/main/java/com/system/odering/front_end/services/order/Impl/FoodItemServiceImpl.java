package com.system.odering.front_end.services.order.Impl;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.system.odering.front_end.domain.order.Impl.FoodItem;
import com.system.odering.front_end.repositories.order.IFoodItemRepository;
import com.system.odering.front_end.repositories.order.Impl.FoodItemRepositoryImpl;
import com.system.odering.front_end.services.order.IFoodItemService;


/**
 * Created by cfebruary on 2016/12/14.
 */
public class FoodItemServiceImpl extends IntentService implements IFoodItemService {
    private static final String ACTION_ADD = "com.system.odering.front_end.services.order.Impl.action.ADD";
    private static final String ACTION_UPDATE = "com.system.odering.front_end.services.order.Impl.action.UPDATE";

    private static final String EXTRA_ADD = "com.system.odering.front_end.services.order.Impl.extra.ADD";
    private static final String EXTRA_UPDATE = "com.system.odering.front_end.services.order.Impl.extra.UPDATE";

    private static FoodItemServiceImpl service = null;

    public static FoodItemServiceImpl getInstance(){
        if(service == null)
        {
            service = new FoodItemServiceImpl();
        }

        return service;
    }

    public FoodItemServiceImpl(){
        super("FoodItemServiceImpl");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_ADD.equals(action)) {
                final FoodItem foodItem = (FoodItem)intent.getSerializableExtra(EXTRA_ADD);
                saveFoodItem(foodItem);
            } else if (ACTION_UPDATE.equals(action)) {
                final FoodItem foodItem = (FoodItem)intent.getSerializableExtra(EXTRA_UPDATE);
                updateFoodItem(foodItem);
            }
        }
    }


    private void saveFoodItem(FoodItem foodItem) {
        IFoodItemRepository foodItemRepository = new FoodItemRepositoryImpl(getBaseContext());
        foodItemRepository.save(foodItem);
    }


    private void updateFoodItem(FoodItem foodItem) {
        IFoodItemRepository foodItemRepository = new FoodItemRepositoryImpl(getBaseContext());
        foodItemRepository.update(foodItem);
    }


    @Override
    public void addFoodItem(Context context, FoodItem foodItem) {
        Intent intent = new Intent(context, FoodItemServiceImpl.class);
        intent.setAction(ACTION_ADD);
        intent.putExtra(EXTRA_ADD, foodItem);
        context.startService(intent);
    }

    @Override
    public void updateFoodItem(Context context, FoodItem foodItem) {
        Intent intent = new Intent(context, FoodItemServiceImpl.class);
        intent.setAction(ACTION_UPDATE);
        intent.putExtra(EXTRA_UPDATE, foodItem);
        context.startService(intent);
    }

    @Override
    public void deleteFoodItem(Context context, FoodItem foodItem) {

    }
}
