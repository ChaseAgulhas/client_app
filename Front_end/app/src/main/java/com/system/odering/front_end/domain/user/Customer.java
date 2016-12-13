package com.system.odering.front_end.domain.user;

import java.io.Serializable;

/**
 * Created by cfebruary on 2016/09/25.
 */
public class Customer implements Serializable, IPerson {
    private Long customerId;
    private String name, surname, email, phoneNumber;

    private Customer(Builder builder)
    {
        this.customerId = builder.customerId;
        this.name = builder.name;
        this.surname = builder.surname;
        this.email = builder.email;
        this.phoneNumber = builder.phoneNumber;
    }

    public Long getCustomerId(){return customerId;}

    public String getName()
    {
        return name;
    }

    public String getSurname()
    {
        return surname;
    }

    public String getEmail(){return email;}

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public String toString()
    {
        return "Name: " + getName()
                + "\nSurname: " + getSurname()
                + "\nEmail: " + getEmail()
                + "\nPhone number: " + getPhoneNumber();
    }

    public static class Builder
    {
        private Long customerId;
        private String name;
        private String surname;
        private String email;
        private String phoneNumber;

        public Builder(){}

        public Builder customerId(Long customerId)
        {
            this.customerId = customerId;
            return this;
        }
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

        public Builder email(String email)
        {
            this.email = email;
            return this;
        }

        public Builder phoneNumber(String phoneNumber)
        {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder copy(Customer customer)
        {
            this.customerId = customer.customerId;
            this.name = customer.name;
            this.surname = customer.surname;
            this.email = customer.email;
            this.phoneNumber = customer.phoneNumber;
            return this;
        }

        public Customer build()
        {
            return new Customer(this);
        }
    }
}
