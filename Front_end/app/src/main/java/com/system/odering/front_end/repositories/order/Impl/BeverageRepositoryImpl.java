package com.system.odering.front_end.repositories.order.Impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.system.odering.front_end.domain.order.Impl.Beverage;
import com.system.odering.front_end.factories.order.BeverageFactory;
import com.system.odering.front_end.repositories.order.IBeverageRepository;
import com.system.odering.front_end.utils.database.DBConstants;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by cfebruary on 2016/12/14.
 */
public class BeverageRepositoryImpl extends SQLiteOpenHelper implements IBeverageRepository {
    public static final String TABLE_NAME = "beverage";
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

    public BeverageRepositoryImpl(Context context)
    {
        super(context, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
    }

    public void open() throws SQLException {
        db = this.getWritableDatabase();
        onCreate(db);
    }

    @Override
    public Beverage findById(Long id){
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
            Long beverageId = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            double price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE));
            int amountAvailable = cursor.getInt(cursor.getColumnIndex(COLUMN_AMOUNTAVAILABLE));

            final Beverage beverage = BeverageFactory.getInstance(beverageId, name, price, amountAvailable);

            return beverage;
        }
        else
            return null;
    }

    @Override
    public Beverage save(Beverage beverage) {
        open();
        ContentValues values = new ContentValues();

        values.put(COLUMN_ID, beverage.getId());
        values.put(COLUMN_NAME, beverage.getName());
        values.put(COLUMN_PRICE, beverage.getPrice());
        values.put(COLUMN_AMOUNTAVAILABLE, beverage.getAmountAvailable());

        Long id = db.insertOrThrow(TABLE_NAME, null,values);

        Beverage newBeverage = new Beverage.Builder()
                .copy(beverage)
                .id(new Long(id))
                .build();

        return newBeverage;
    }

    @Override
    public Beverage update(Beverage entity) {
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
    public Beverage delete(Beverage entity) {
        open();
        db.delete(
                TABLE_NAME,
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(entity.getId())});
        return entity;
    }

    @Override
    public Set<Beverage> findAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        Set<Beverage> beverageSet = new HashSet<>();
        open();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Long id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                double price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE));
                int amountAvailable = cursor.getInt(cursor.getColumnIndex(COLUMN_AMOUNTAVAILABLE));

                final Beverage beverage = BeverageFactory.getInstance(id, name, price, amountAvailable);

                beverageSet.add(beverage);
            }
            while (cursor.moveToNext());
        }
        return beverageSet;
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
