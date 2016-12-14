package com.system.odering.front_end.services.order;

import android.content.Context;

import com.system.odering.front_end.domain.order.Impl.Menu;


/**
 * Created by cfebruary on 2016/12/14.
 */
public interface IMenuService {
    void addMenu(Context context, Menu menu);
    void updateMenu(Context context,Menu menu);
    void deleteMenu(Context context, Menu menu);
}
