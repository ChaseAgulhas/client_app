package com.system.odering.front_end.domain.order;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cfebruary on 2016/09/25.
 */
public class Menu implements Serializable, IMenu{
    private Long id;
    private Category category;
    private FoodItem foodItem;

    private Menu(Builder builder)
    {
        this.id = builder.id;
        this.category = builder.category;
        this.foodItem = builder.foodItem;
    }

    public Long getId(){return id;}

    public Category getCategory() {
        return category;
    }

    public FoodItem getFoodItem(){return foodItem;}

    public String toString()
    {
        return "Category: " + getCategory() + "\nFood item: " + getFoodItem();
    }

    public static Map<String, Object> ITEM_MAP = new HashMap<String, Object>();

    public static class Builder
    {
        private Long id;
        private Category category;
        private FoodItem foodItem;

        public Builder(){}

        public Builder id(Long id){
            this.id = id;
            return this;
        }

        public Builder category(Category category){
            this.category = category;
            return this;
        }

        public Builder foodItem(FoodItem foodItem){
            this.foodItem = foodItem;
            return this;
        }

        public Builder copy(Long id, Category category, FoodItem foodItem){
            this.id = id;
            this.category = category;
            this.foodItem = foodItem;

            return this;
        }

        public Menu build(){return new Menu(this);}
    }
}
