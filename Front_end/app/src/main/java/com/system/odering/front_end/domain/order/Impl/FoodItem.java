package com.system.odering.front_end.domain.order.Impl;

import com.system.odering.front_end.domain.order.IProduct;

import java.io.Serializable;

/**
 * Created by cfebruary on 2016/09/25.
 */
public class FoodItem implements Serializable, IProduct {
    private Long id;
    private String name;
    private double price;
    private int amountAvailable;

    private FoodItem(Builder builder)
    {
        this.id = builder.id;
        this.name = builder.name;
        this.price = builder.price;
        this.amountAvailable = builder.amountAvailable;
    }

    public Long getId(){return id;}

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getAmountAvailable() {
        return amountAvailable;
    }

    public String toString()
    {
        return "Item name: " + getName()
                +"Price: " + getPrice()
                +"Available: " + getAmountAvailable();
    }

    public static class Builder
    {
        private Long id;
        private String name;
        private double price;
        private int amountAvailable;

        public Builder(){}

        public Builder id(Long id){
            this.id = id;
            return this;
        }

        public Builder name(String name)
        {
            this.name = name;
            return this;
        }

        public Builder price(double price)
        {
            this.price = price;
            return this;
        }

        public Builder amountAvailable(int amountAvailable)
        {
            this.amountAvailable = amountAvailable;
            return this;
        }

        public Builder copy(FoodItem foodItem)
        {
            this.id = foodItem.id;
            this.name = foodItem.name;
            this.price = foodItem.price;
            this.amountAvailable = foodItem.amountAvailable;

            return this;
        }

        public FoodItem build(){return new FoodItem(this);}
    }
}
