package com.system.odering.front_end.services.order;

import android.content.Context;
import android.test.AndroidTestCase;

import com.system.odering.front_end.domain.order.Category;
import com.system.odering.front_end.domain.order.FoodItem;
import com.system.odering.front_end.domain.order.Menu;
import com.system.odering.front_end.factories.order.CategoryFactory;
import com.system.odering.front_end.factories.order.FoodItemFactory;
import com.system.odering.front_end.repositories.order.IMenuRepository;
import com.system.odering.front_end.repositories.order.Impl.MenuRepositoryImpl;
import com.system.odering.front_end.services.order.Impl.MenuServiceImpl;

import junit.framework.Assert;

import java.util.Set;

/**
 * Created by cfebruary on 2016/12/14.
 */
public class MenuServiceTest extends AndroidTestCase {

    public void testInsert() throws Exception {

        MenuServiceImpl menuService = MenuServiceImpl.getInstance();
        Context context = getContext();
        IMenuRepository menuRepository = new MenuRepositoryImpl(context);

        Category category = CategoryFactory.getInstance(Long.valueOf("109"), "Breakfast");
        FoodItem foodItem = FoodItemFactory.getInstance(Long.valueOf("345345"), "Chips", 465, 12);

        Menu menu = new Menu.Builder()
                .id(Long.valueOf("1"))
                .category(category)
                .foodItem(foodItem)
                .build();

        menuService.addMenu(this.mContext, menu);

        Thread.sleep(5000);
        // READ ALL
        Set<Menu> menuSet = menuRepository.findAll();
        Assert.assertTrue(menuSet.size() > 0);
    }
}