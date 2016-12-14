package com.system.odering.front_end.domain.order.Impl;

import com.system.odering.front_end.domain.address.Impl.Address;
import com.system.odering.front_end.domain.order.IOrder;
import com.system.odering.front_end.domain.user.Impl.Customer;

import java.io.Serializable;

/**
 * Created by cfebruary on 2016/11/26.
 */
public class Order implements Serializable, IOrder {

    private Long orderID;
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

    public Long getOrderID(){return orderID;}

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
        private Long orderID;
        private Customer customer;
        private Address address;
        private FoodItem foodItem;

        public Builder orderID(Long value)
        {
            this.orderID = value;
            return this;
        }

        public Builder customer(Customer value)
        {
            this.customer = value;
            return this;
        }

        public Builder address(Address value)
        {
            this.address = value;
            return this;
        }

        public Builder foodItem(FoodItem value)
        {
            this.foodItem = value;
            return this;
        }

        public Builder copy(Long orderID, Customer customer, Address address, FoodItem foodItem)
        {
            this.orderID = orderID;
            this.customer = customer;
            this.address = address;
            this.foodItem = foodItem;

            return this;
        }

        public Order build()
        {
            return new Order(this);
        }
    }
}
