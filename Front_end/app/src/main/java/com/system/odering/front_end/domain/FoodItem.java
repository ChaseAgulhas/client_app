package com.system.odering.front_end.domain;

import java.io.Serializable;

/**
 * Created by cfebruary on 2016/09/25.
 */
public class FoodItem implements Serializable, IProduct{
    private String name;
    private double price;
    private int amountAvailable;

    private FoodItem(Builder builder)
    {
        this.name = builder.name;
        this.price = builder.price;
        this.amountAvailable = builder.amountAvailable;
    }

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
        private String name;
        private double price;
        private int amountAvailable;

        public Builder(){}

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
            this.name = foodItem.name;
            this.price = foodItem.price;
            this.amountAvailable = foodItem.amountAvailable;

            return this;
        }

        public FoodItem build(){return new FoodItem(this);}
    }
}
