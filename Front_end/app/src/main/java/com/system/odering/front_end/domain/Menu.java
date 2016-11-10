package com.system.odering.front_end.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cfebruary on 2016/09/25.
 */
public class Menu implements Serializable{
    private String category;

    private Menu(Builder builder)
    {
        this.category = builder.category;
    }

    public String getCategory() {
        return category;
    }

    public String toString()
    {
        return "Category: " + getCategory();
    }

    public static Map<String, Object> ITEM_MAP = new HashMap<String, Object>();

    private static class Builder
    {
        private String category;

        public Builder(){}


    }
}
