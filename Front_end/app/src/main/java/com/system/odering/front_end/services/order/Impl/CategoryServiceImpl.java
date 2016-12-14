package com.system.odering.front_end.services.order.Impl;


import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.system.odering.front_end.domain.order.Category;
import com.system.odering.front_end.repositories.order.ICategoryRepository;
import com.system.odering.front_end.repositories.order.Impl.CategoryRepositoryImpl;
import com.system.odering.front_end.services.order.ICategoryService;

/**
 * Created by cfebruary on 2016/12/13.
 */
public class CategoryServiceImpl extends IntentService implements ICategoryService {
    private static final String ACTION_ADD = "com.system.odering.front_end.services.order.Impl.action.ADD";
    private static final String ACTION_UPDATE = "com.system.odering.front_end.services.order.Impl.action.UPDATE";

    private static final String EXTRA_ADD = "com.system.odering.front_end.services.order.Impl.extra.ADD";
    private static final String EXTRA_UPDATE = "com.system.odering.front_end.services.order.Impl.extra.UPDATE";

    private static CategoryServiceImpl service = null;

    public static CategoryServiceImpl getInstance(){
        if(service == null)
        {
            service = new CategoryServiceImpl();
        }

        return service;
    }

    public CategoryServiceImpl(){
        super("CategoryServiceImpl");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_ADD.equals(action)) {
                final Category category = (Category)intent.getSerializableExtra(EXTRA_ADD);
                saveCategory(category);
            } else if (ACTION_UPDATE.equals(action)) {
                final Category category = (Category)intent.getSerializableExtra(EXTRA_UPDATE);
                updateCategory(category);
            }
        }
    }


    private void saveCategory(Category category) {
        ICategoryRepository categoryRepository = new CategoryRepositoryImpl(getBaseContext());
        categoryRepository.save(category);
    }


    private void updateCategory(Category category) {
        ICategoryRepository categoryRepository = new CategoryRepositoryImpl(getBaseContext());
        categoryRepository.update(category);
    }


    @Override
    public void addCategory(Context context, Category category) {
        Intent intent = new Intent(context, CategoryServiceImpl.class);
        intent.setAction(ACTION_ADD);
        intent.putExtra(EXTRA_ADD, category);
        context.startService(intent);
    }

    @Override
    public void updateCategory(Context context, Category category) {
        Intent intent = new Intent(context, CategoryServiceImpl.class);
        intent.setAction(ACTION_UPDATE);
        intent.putExtra(EXTRA_UPDATE, category);
        context.startService(intent);
    }

    @Override
    public void deleteCategory(Context context, Category category) {

    }
}
