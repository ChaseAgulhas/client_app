package com.system.odering.front_end.activities;

import java.util.ArrayList;

/**
 * Created by Chase on 2016-12-09.
 */

public interface Communicator {
    public ArrayList<UserFoodItem> getCartList();
    public void addToCartList(FoodItem foodItem, String loggedInUser);
    public String getLoggedInUser();
}
