package com.system.odering.front_end.repositories.order.Impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.system.odering.front_end.domain.address.Address;
import com.system.odering.front_end.domain.order.FoodItem;
import com.system.odering.front_end.domain.order.Order;
import com.system.odering.front_end.domain.user.Customer;
import com.system.odering.front_end.factories.order.OrderFactory;
import com.system.odering.front_end.repositories.order.IOrderRepository;
import com.system.odering.front_end.utils.database.DBConstants;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by cfebruary on 2016/12/14.
 */
public class OrderRepositoryImpl extends SQLiteOpenHelper implements IOrderRepository {
    public static final String TABLE_NAME = "order";
    private SQLiteDatabase db;

    public static final String COLUMN_ID = "ORDERID";
    public static final String COLUMN_NAME = "CUSTOMER";
    public static final String COLUMN_ADDRESS = "ADDRESS";
    public static final String COLUMN_FOODITEM = "FOODITEM";

    //Database table creation
    private static final String DATABASE_CREATE = " CREATE TABLE IF NOT EXISTS "
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT NOT NULL,"
            + COLUMN_ADDRESS + " TEXT NOT NULL,"
            + COLUMN_FOODITEM + " TEXT NOT NULL);";

    public OrderRepositoryImpl(Context context)
    {
        super(context, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
    }

    public void open() throws SQLException {
        db = this.getWritableDatabase();
    }

    @Override
    public Order findById(Long id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_NAME,
                new String[]{
                        COLUMN_ID,
                        COLUMN_NAME,
                        COLUMN_ADDRESS,
                        COLUMN_FOODITEM},
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(id)},
                null,
                null,
                null);
        if(cursor.moveToFirst())
        {
            Long orderId = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            String addressName = cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS));
            String foodItemName = cursor.getString(cursor.getColumnIndex(COLUMN_FOODITEM));

            Customer customer = new Customer.Builder()
                    .name(name)
                    .build();
            Address address = new Address.Builder()
                    .streetName(addressName)
                    .build();
            FoodItem foodItem = new FoodItem.Builder()
                    .name(foodItemName)
                    .build();

            final Order order = OrderFactory.getInstance(orderId, customer, address, foodItem);

            return order;
        }
        else
            return null;
    }

    @Override
    public Order save(Order order) {
        open();
        ContentValues values = new ContentValues();

        values.put(COLUMN_ID, order.getOrderID());
        values.put(COLUMN_NAME, order.getCustomer().getName());
        values.put(COLUMN_ADDRESS, order.getAddress().getStreetName());
        values.put(COLUMN_FOODITEM, order.getFoodItem().getName());

        Long id = db.insertOrThrow(TABLE_NAME, null,values);

        Order newOrder = new Order.Builder()
                .copy(order.getOrderID(), order.getCustomer(), order.getAddress(), order.getFoodItem())
                .orderID(new Long(id))
                .build();

        return newOrder;
    }

    @Override
    public Order update(Order entity) {
        open();

        ContentValues values = new ContentValues();

        values.put(COLUMN_ID, entity.getOrderID());
        values.put(COLUMN_NAME, entity.getCustomer().getName());
        values.put(COLUMN_ADDRESS, entity.getAddress().getStreetName());
        values.put(COLUMN_FOODITEM, entity.getFoodItem().getName());

        db.update(
                TABLE_NAME,
                values,
                COLUMN_ID + " =? ",
                new String[]{String.valueOf((entity.getOrderID()))}
        );
        return entity;
    }

    @Override
    public Order delete(Order entity) {
        open();
        db.delete(
                TABLE_NAME,
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(entity.getOrderID())});
        return entity;
    }

    @Override
    public Set<Order> findAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        Set<Order> orderSet = new HashSet<>();
        open();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Long orderId = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String addressName = cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS));
                String foodItemName = cursor.getString(cursor.getColumnIndex(COLUMN_FOODITEM));

                Customer customer = new Customer.Builder()
                        .name(name)
                        .build();
                Address address = new Address.Builder()
                        .streetName(addressName)
                        .build();
                FoodItem foodItem = new FoodItem.Builder()
                        .name(foodItemName)
                        .build();

                final Order order = OrderFactory.getInstance(orderId, customer, address, foodItem);
                orderSet.add(order);
            }
            while (cursor.moveToNext());
        }
        return orderSet;
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
