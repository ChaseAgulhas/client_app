package com.system.odering.front_end.domain;

import java.io.Serializable;

/**
 * Created by cfebruary on 2016/11/26.
 */
public class Order implements Serializable, IOrder{

    private int orderID;
    private Customer customer;
    private Address address;
    private FoodItem foodItem;

    private Order(Builder builder)
    {
        this.orderID = builder.orderID;
        this.customer = builder.customer;
        this.address = builder.address;
        this.foodItem = builder.foodItem;
    }

    public int getOrderID(){return orderID;}

    public Customer getCustomer() {return customer;}

    public Address getAddress() {return address;}

    public FoodItem getFoodItem() {return foodItem;}


    public String toString()
    {
        return "Order ID:" + getOrderID()
                + "\nName: " + customer.getName()
                + "\nAddress: " + address.toString()
                + "\nEmail: " + customer.getEmail()
                + "\nPhone Number: " + customer.getPhoneNumber();
    }

    public static class Builder
    {
        private int orderID;
        private Customer customer;
        private Address address;
        private FoodItem foodItem;

        public Builder setOrderID(int value)
        {
            this.orderID = value;
            return this;
        }

        public Builder setCustomer(Customer value)
        {
            this.customer = value;
            return this;
        }

        public Builder setAddress(Address value)
        {
            this.address = value;
            return this;
        }

        public Builder setFoodItem(FoodItem value)
        {
            this.foodItem = value;
            return this;
        }

        public Builder copy(int orderID, Order value)
        {
            this.orderID = orderID;
            this.customer = value.customer;
            this.address = value.address;
            this.foodItem = value.foodItem;

            return this;
        }

        public Order build()
        {
            return new Order(this);
        }
    }
}
