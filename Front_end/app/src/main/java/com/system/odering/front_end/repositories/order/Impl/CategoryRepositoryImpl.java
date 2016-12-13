package com.system.odering.front_end.repositories.order.Impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.system.odering.front_end.domain.order.Category;
import com.system.odering.front_end.factories.order.CategoryFactory;
import com.system.odering.front_end.repositories.order.ICategoryRepository;
import com.system.odering.front_end.utils.DBConstants;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by cfebruary on 2016/12/13.
 */
public class CategoryRepositoryImpl extends SQLiteOpenHelper implements ICategoryRepository {
    public static final String TABLE_NAME = "category";
    private SQLiteDatabase db;

    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_CATEGORYNAME = "CATEGORYNAME";

    public CategoryRepositoryImpl(Context context)
    {
        super(context, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
    }

    public void open() throws SQLException {
        db = this.getWritableDatabase();
    }

    @Override
    public Category findById(Long id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_NAME,
                new String[]{
                        COLUMN_ID,
                        COLUMN_CATEGORYNAME},
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(id)},
                null,
                null,
                null);
        if(cursor.moveToFirst())
        {
            Long categoryID = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
            String categoryName = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORYNAME));

            final Category category = CategoryFactory.getInstance(categoryID, categoryName);

            return category;
        }
        else
            return null;
    }

    @Override
    public Category save(Category category) {
        open();
        ContentValues values = new ContentValues();

        values.put(COLUMN_ID, category.getCategoryID());
        values.put(COLUMN_CATEGORYNAME, category.getCategoryName());

        Long id = db.insertOrThrow(TABLE_NAME, null,values);

        Category newCategory = new Category.Builder()
                .copy(category)
                .categoryID(new Long(id))
                .build();

        return newCategory;
    }

    @Override
    public Category update(Category entity) {
        open();

        ContentValues values = new ContentValues();

        values.put(COLUMN_ID, entity.getCategoryID());
        values.put(COLUMN_CATEGORYNAME, entity.getCategoryName());

        db.update(
                TABLE_NAME,
                values,
                COLUMN_ID + " =? ",
                new String[]{String.valueOf((entity.getCategoryID()))}
        );
        return entity;
    }

    @Override
    public Category delete(Category entity) {
        open();
        db.delete(
                TABLE_NAME,
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(entity.getCategoryID())});
        return entity;
    }

    @Override
    public Set<Category> findAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        Set<Category> categorySet = new HashSet<>();
        open();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Long columnId = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
                String categoryName = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORYNAME));

                final Category category = CategoryFactory.getInstance(columnId, categoryName);

                categorySet.add(category);
            }
            while (cursor.moveToNext());
        }
        return categorySet;
    }

    @Override
    public int deleteAll() {
        open();
        int rowsDeleted = db.delete(TABLE_NAME,null,null);
        //close();
        return rowsDeleted;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Need to finish");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(this.getClass().getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
