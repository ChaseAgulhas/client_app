package com.system.odering.front_end.repositories.order.Impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.system.odering.front_end.domain.order.FoodItem;
import com.system.odering.front_end.factories.order.FoodItemFactory;
import com.system.odering.front_end.repositories.order.IFoodItemRepository;
import com.system.odering.front_end.utils.database.DBConstants;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by cfebruary on 2016/12/13.
 */
public class FoodItemRepositoryImpl extends SQLiteOpenHelper implements IFoodItemRepository {
    public static final String TABLE_NAME = "foodItem";
    private SQLiteDatabase db;

    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_PRICE = "PRICE";
    public static final String COLUMN_AMOUNTAVAILABLE = "AMOUNTAVAILABLE";

    //Database table creation
    private static final String DATABASE_CREATE = " CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT NOT NULL,"
            + COLUMN_PRICE + " TEXT NOT NULL,"
            + COLUMN_AMOUNTAVAILABLE + " TEXT NOT NULL);";

    public FoodItemRepositoryImpl(Context context)
    {
        super(context, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
    }

    public void open() throws SQLException {
        db = this.getWritableDatabase();
        onCreate(db);
    }

    @Override
    public FoodItem findById(Long id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_NAME,
                new String[]{
                        COLUMN_ID,
                        COLUMN_NAME,
                        COLUMN_PRICE,
                        COLUMN_AMOUNTAVAILABLE},
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(id)},
                null,
                null,
                null);
        if(cursor.moveToFirst())
        {
            Long foodItemId = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            double price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE));
            int amountAvailable = cursor.getInt(cursor.getColumnIndex(COLUMN_AMOUNTAVAILABLE));

            final FoodItem foodItem = FoodItemFactory.getInstance(foodItemId, name, price, amountAvailable);

            return foodItem;
        }
        else
            return null;
    }

    @Override
    public FoodItem save(FoodItem foodItem) {
        open();
        ContentValues values = new ContentValues();

        values.put(COLUMN_ID, foodItem.getId());
        values.put(COLUMN_NAME, foodItem.getName());
        values.put(COLUMN_PRICE, foodItem.getPrice());
        values.put(COLUMN_AMOUNTAVAILABLE, foodItem.getAmountAvailable());

        Long id = db.insertOrThrow(TABLE_NAME, null,values);

        FoodItem newFoodItem = new FoodItem.Builder()
                .copy(foodItem)
                .id(new Long(id))
                .build();

        return newFoodItem;
    }

    @Override
    public FoodItem update(FoodItem entity) {
        open();

        ContentValues values = new ContentValues();

        values.put(COLUMN_ID, entity.getId());
        values.put(COLUMN_NAME, entity.getName());
        values.put(COLUMN_PRICE, entity.getPrice());
        values.put(COLUMN_AMOUNTAVAILABLE, entity.getAmountAvailable());
        
        db.update(
                TABLE_NAME,
                values,
                COLUMN_ID + " =? ",
                new String[]{String.valueOf((entity.getId()))}
        );
        return entity;
    }

    @Override
    public FoodItem delete(FoodItem entity) {
        open();
        db.delete(
                TABLE_NAME,
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(entity.getId())});
        return entity;
    }

    @Override
    public Set<FoodItem> findAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        Set<FoodItem> foodItemSet = new HashSet<>();
        open();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Long id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                double price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE));
                int amountAvailable = cursor.getInt(cursor.getColumnIndex(COLUMN_AMOUNTAVAILABLE));

                final FoodItem foodItem = FoodItemFactory.getInstance(id, name, price, amountAvailable);

                foodItemSet.add(foodItem);
            }
            while (cursor.moveToNext());
        }
        return foodItemSet;
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
