package com.system.odering.front_end.repositories.order.Impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.system.odering.front_end.domain.order.Category;
import com.system.odering.front_end.domain.order.FoodItem;
import com.system.odering.front_end.domain.order.Menu;
import com.system.odering.front_end.factories.order.MenuFactory;
import com.system.odering.front_end.repositories.order.IMenuRepository;
import com.system.odering.front_end.utils.database.DBConstants;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by cfebruary on 2016/12/13.
 */
public class MenuRepositoryImpl extends SQLiteOpenHelper implements IMenuRepository {
    public static final String TABLE_NAME = "menu";
    private SQLiteDatabase db;

    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_CATEGORYNAME = "CATEGORYNAME";
    public static final String COLUMN_FOODNAME = "FOODNAME";

    //Database table creation
    private static final String DATABASE_CREATE = " CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_CATEGORYNAME + " TEXT NOT NULL,"
            + COLUMN_FOODNAME + " TEXT NOT NULL);";

    public MenuRepositoryImpl(Context context)
    {
        super(context, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
    }

    public void open() throws SQLException {
        db = this.getWritableDatabase();
        onCreate(db);
    }

    @Override
    public Menu findById(Long id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_NAME,
                new String[]{
                        COLUMN_ID,
                        COLUMN_CATEGORYNAME,
                        COLUMN_FOODNAME},
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(id)},
                null,
                null,
                null);
        if(cursor.moveToFirst())
        {
            Long menuId = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
            String categoryName = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORYNAME));
            String foodName = cursor.getString(cursor.getColumnIndex(COLUMN_FOODNAME));

            Category category = new Category.Builder()
                    .categoryName(categoryName)
                    .build();
            FoodItem foodItem = new FoodItem.Builder()
                    .name(foodName)
                    .build();

            final Menu menu = MenuFactory.getInstance(menuId, category, foodItem);

            return menu;
        }
        else
            return null;
    }

    @Override
    public Menu save(Menu menu) {
        open();
        ContentValues values = new ContentValues();

        values.put(COLUMN_ID, menu.getId());
        values.put(COLUMN_CATEGORYNAME, menu.getCategory().getCategoryName());
        values.put(COLUMN_FOODNAME, menu.getFoodItem().getName());

        Long id = db.insertOrThrow(TABLE_NAME, null,values);

        Menu newMenu = new Menu.Builder()
                .copy(menu.getId(), menu.getCategory(), menu.getFoodItem())
                .id(new Long(id))
                .build();

        return newMenu;
    }

    @Override
    public Menu update(Menu entity) {
        open();

        ContentValues values = new ContentValues();

        values.put(COLUMN_ID, entity.getId());
        values.put(COLUMN_CATEGORYNAME, entity.getCategory().getCategoryName());
        values.put(COLUMN_FOODNAME, entity.getFoodItem().getName());

        db.update(
                TABLE_NAME,
                values,
                COLUMN_ID + " =? ",
                new String[]{String.valueOf((entity.getId()))}
        );
        return entity;
    }

    @Override
    public Menu delete(Menu entity) {
        open();
        db.delete(
                TABLE_NAME,
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(entity.getId())});
        return entity;
    }

    @Override
    public Set<Menu> findAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        Set<Menu> menuSet = new HashSet<>();
        open();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Long columnId = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
                String categoryName = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORYNAME));
                String foodName = cursor.getString(cursor.getColumnIndex(COLUMN_FOODNAME));

                Category category = new Category.Builder()
                        .categoryName(categoryName)
                        .build();
                FoodItem foodItem = new FoodItem.Builder()
                        .name(foodName)
                        .build();
                final Menu menu = MenuFactory.getInstance(columnId, category, foodItem);

                menuSet.add(menu);
            }
            while (cursor.moveToNext());
        }
        return menuSet;
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
        db.execSQL(DATABASE_CREATE);
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
