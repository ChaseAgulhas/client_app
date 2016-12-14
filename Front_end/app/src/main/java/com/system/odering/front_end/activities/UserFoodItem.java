package com.system.odering.front_end.activities;

import java.io.Serializable;

/**
 * Created by Chase Agulhas on 2016/12/09.
 */

public class UserFoodItem implements Serializable {

    private FoodItem product;
    private String price;
    private int quantity;

    public UserFoodItem(FoodItem product, String price, int quantity)
    {
        this.product = product;
        this.price = price;
        this.quantity = quantity;
    }

    public FoodItem getProduct() {
        return product;
    }

    public String getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String toString()
    {
        return "Item name: " + product.getName()
                +"Price: " + getQuantity() * (Double.parseDouble(product.getPrice()))
                +"Quantity: " + getQuantity();
    }

    public void setProduct(FoodItem product)
    {
        this.product = product;
    }

    public void setPrice()
    {
        Double price1 = getQuantity() * (Double.parseDouble(product.getPrice()));
        this.price = price1.toString();
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
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
