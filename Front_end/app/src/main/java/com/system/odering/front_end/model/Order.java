package com.system.odering.front_end.model;

import com.system.odering.front_end.activities.UserFoodItem;

import java.util.List;

/**
 * Created by JasonMckay on 15-Dec-16.
 */

public class Order {
    private String userId;
    private String location;
    private String time;
    private Long id;
    private List<UserFoodItem> items;

    public Order(String userId, String location, String time, List<UserFoodItem> items) {
        this.userId = userId;
        this.location = location;
        this.time = time;
        this.items = items;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<UserFoodItem> getItems() {
        return items;
    }

    public void setItems(List<UserFoodItem> items) {
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
