package com.system.odering.front_end.domain.order;

/**
 * Created by cfebruary on 2016/10/10.
 */
public class Category implements ICategory{

    private String categoryID;
    private String categoryName;

    public Category(Builder builder)
    {
        this.categoryID = builder.categoryID;
        this.categoryName = builder.categoryName;
    }

    public String getCategoryID()
    {
        return categoryID;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    public String toString()
    {
        return "Category name: " + getCategoryName();
    }

    public static class Builder
    {
        private String categoryID;
        private String categoryName;

        public Builder(){}

        public Builder categoryID(String categoryID)
        {
            this.categoryID = categoryID;
            return this;
        }

        public Builder categoryName(String categoryName)
        {
            this.categoryName = categoryName;
            return this;
        }

        public Builder copy(Category category)
        {
            this.categoryID = category.categoryID;
            this.categoryName = category.categoryName;
            return this;
        }

        public Category build()
        {
            return new Category(this);
        }
    }
}
