package com.system.odering.front_end.activities;

import java.io.Serializable;

/**
 * Created by Chase on 2016-11-30.
 */
public class FoodItem implements Serializable {

    private String name;
    private String price;
    private int amountAvailable;

    public FoodItem(String name, String price, int amountAvailable)
    {
        this.name = name;
        this.price = price;
        this.amountAvailable = amountAvailable;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
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

    public void name(String name)
    {
        this.name = name;
    }

    public void price(String price)
    {
        this.price = price;
    }

    public void amountAvailable(int amountAvailable)
    {
        this.amountAvailable = amountAvailable;
    }

    /*public static class Builder
    {
        private String name;
        private String price;
        private int amountAvailable;

        public Builder(){}

        public Builder name(String name)
        {
            this.name = name;
            return this;
        }

        public Builder price(String price)
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
    }*/
}