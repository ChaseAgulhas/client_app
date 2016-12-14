package com.system.odering.front_end.services.order;

import android.content.Context;

import com.system.odering.front_end.domain.order.Impl.Beverage;

/**
 * Created by cfebruary on 2016/12/14.
 */
public interface IBeverageService {
    void addBeverage(Context context, Beverage beverage);
    void updateBeverage(Context context,Beverage beverage);
    void deleteBeverage(Context context, Beverage beverage);
}

