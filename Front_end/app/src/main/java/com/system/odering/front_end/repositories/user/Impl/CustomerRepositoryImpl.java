package com.system.odering.front_end.repositories.user.Impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.system.odering.front_end.domain.user.Impl.Customer;
import com.system.odering.front_end.factories.user.CustomerFactory;
import com.system.odering.front_end.repositories.user.ICustomerRepository;
import com.system.odering.front_end.utils.database.DBConstants;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by cfebruary on 2016/12/14.
 */
public class CustomerRepositoryImpl extends SQLiteOpenHelper implements ICustomerRepository {
    public static final String TABLE_NAME = "customer";
    private SQLiteDatabase db;

    public static final String COLUMN_ID = "CUSTOMERID";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_SURNAME = "SURNAME";
    public static final String COLUMN_EMAIL = "EMAIL";
    public static final String COLUMN_PHONENUMBER = "PHONENUMBER";

    //Database table creation
    private static final String DATABASE_CREATE = " CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT NOT NULL,"
            + COLUMN_SURNAME + " TEXT NOT NULL,"
            + COLUMN_EMAIL + " TEXT NOT NULL,"
            + COLUMN_PHONENUMBER + " TEXT NOT NULL);";

    public CustomerRepositoryImpl(Context context)
    {
        super(context, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
    }

    public void open() throws SQLException {
        db = this.getWritableDatabase();
    }

    @Override
    public Customer findById(Long id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_NAME,
                new String[]{
                        COLUMN_ID,
                        COLUMN_NAME,
                        COLUMN_SURNAME,
                        COLUMN_EMAIL,
                        COLUMN_PHONENUMBER},
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(id)},
                null,
                null,
                null);
        if(cursor.moveToFirst())
        {
            Long customerId = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            String surname = cursor.getString(cursor.getColumnIndex(COLUMN_SURNAME));
            String email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
            String phoneNumber = cursor.getString(cursor.getColumnIndex(COLUMN_PHONENUMBER));

            final Customer customer = CustomerFactory.getInstance(customerId, name, surname, email, phoneNumber);

            return customer;
        }
        else
            return null;
    }

    @Override
    public Customer save(Customer customer) {
        open();
        ContentValues values = new ContentValues();

        values.put(COLUMN_ID, customer.getCustomerId());
        values.put(COLUMN_NAME, customer.getName());
        values.put(COLUMN_SURNAME, customer.getSurname());
        values.put(COLUMN_EMAIL, customer.getEmail());
        values.put(COLUMN_PHONENUMBER, customer.getPhoneNumber());

        Long id = db.insertOrThrow(TABLE_NAME, null,values);

        Customer newCustomer = new Customer.Builder()
                .copy(customer)
                .customerId(new Long(id))
                .build();

        return newCustomer;
    }

    @Override
    public Customer update(Customer entity) {
        open();

        ContentValues values = new ContentValues();

        values.put(COLUMN_ID, entity.getCustomerId());
        values.put(COLUMN_NAME, entity.getName());
        values.put(COLUMN_SURNAME, entity.getSurname());
        values.put(COLUMN_EMAIL, entity.getEmail());
        values.put(COLUMN_PHONENUMBER, entity.getPhoneNumber());

        db.update(
                TABLE_NAME,
                values,
                COLUMN_ID + " =? ",
                new String[]{String.valueOf((entity.getCustomerId()))}
        );
        return entity;
    }

    @Override
    public Customer delete(Customer entity) {
        open();
        db.delete(
                TABLE_NAME,
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(entity.getCustomerId())});
        return entity;
    }

    @Override
    public Set<Customer> findAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        Set<Customer> customerSet = new HashSet<>();
        open();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Long customerId = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String surname = cursor.getString(cursor.getColumnIndex(COLUMN_SURNAME));
                String email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
                String phoneNumber = cursor.getString(cursor.getColumnIndex(COLUMN_PHONENUMBER));

                final Customer customer = CustomerFactory.getInstance(customerId, name, surname, email, phoneNumber);

                customerSet.add(customer);
            }
            while (cursor.moveToNext());
        }
        return customerSet;
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
