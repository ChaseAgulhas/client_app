package com.system.odering.front_end.domain;

import java.io.Serializable;

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

    private static class Builder
    {
        private String category;

        public Builder(){}


    }
}
