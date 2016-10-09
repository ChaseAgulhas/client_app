package com.system.odering.front_end.domain;

import java.io.Serializable;

/**
 * Created by cfebruary on 2016/09/25.
 */
public class Customer implements Serializable, Person{
    private String name, surname, phoneNumber;

    private Customer(Builder builder)
    {
        this.name = builder.name;
        this.surname = builder.surname;
        this.phoneNumber = builder.phoneNumber;
    }

    public String getName()
    {
        return name;
    }

    public String getSurname()
    {
        return surname;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public String toString()
    {
        return "Name: " + getName()
                + "\nSurname: " + getSurname()
                + "\nPhone number: " + getPhoneNumber();
    }

    public static class Builder
    {
        private String name;
        private String surname;
        private String phoneNumber;

        public Builder(){}

        public Builder name(String name)
        {
            this.name = name;
            return this;
        }

        public Builder surname(String surname)
        {
            this.surname = surname;
            return this;
        }

        public Builder phoneNumber(String phoneNumber)
        {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder copy(Customer customer)
        {
            this.name = customer.name;
            this.surname = customer.surname;
            this.phoneNumber = customer.phoneNumber;
            return this;
        }

        public Customer build()
        {
            return new Customer(this);
        }
    }
}
