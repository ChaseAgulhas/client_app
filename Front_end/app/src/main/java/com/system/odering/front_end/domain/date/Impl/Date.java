package com.system.odering.front_end.domain.date.Impl;

import com.system.odering.front_end.domain.date.IDate;

import java.io.Serializable;

/**
 * Created by cfebruary on 2016/12/14.
 */
public class Date implements Serializable, IDate {
    private String day, month, year;

    private Date(Builder builder)
    {
        this.day = builder.day;
        this.month = builder.month;
        this.year = builder.year;
    }

    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    private static class Builder
    {
        private String day, month, year;

        public Builder(){}

        public Builder day(String day)
        {
            this.day = day;
            return this;
        }

        public Builder month(String month)
        {
            this.month = month;
            return this;
        }

        public Builder year(String year)
        {
            this.year = year;
            return this;
        }

        public Builder copy(Date date)
        {
            this.day = date.day;
            this.month = date.month;
            this.year = date.year;

            return this;
        }

        public Date build() {return new Date(this);}
    }
}
