package com.system.odering.front_end.repositories.address.Impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.system.odering.front_end.domain.address.Address;
import com.system.odering.front_end.factories.address.AddressFactory;
import com.system.odering.front_end.repositories.address.IAddressRepository;
import com.system.odering.front_end.utils.database.DBConstants;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by cfebruary on 2016/12/13.
 */
public class AddressRepositoryImpl extends SQLiteOpenHelper implements IAddressRepository{
    public static final String TABLE_ADDRESS = "address";
    private SQLiteDatabase db;

    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_STREETNUMBER = "STREETNUMBER";
    public static final String COLUMN_STREETNAME = "STREETNAME";
    public static final String COLUMN_SUBURB = "SUBURB";
    public static final String COLUMN_CITY = "CITY";
    public static final String COLUMN_POSTCODE = "POSTCODE";

    //Table creation
    private static final String TABLE_CREATE = " CREATE TABLE IF NOT EXISTS "
            + TABLE_ADDRESS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_STREETNUMBER + " TEXT NOT NULL,"
            + COLUMN_STREETNAME + " TEXT NOT NULL,"
            + COLUMN_SUBURB + " TEXT NOT NULL,"
            + COLUMN_CITY + " TEXT NOT NULL,"
            + COLUMN_POSTCODE + " TEXT NOT NULL);";

    public AddressRepositoryImpl(Context context)
    {
        super(context, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
    }

    public void open() throws SQLException{
        db = this.getWritableDatabase();
    }

    @Override
    public Address findById(Long id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_ADDRESS,
                new String[]{
                        COLUMN_ID,
                        COLUMN_STREETNUMBER,
                        COLUMN_STREETNAME,
                        COLUMN_SUBURB,
                        COLUMN_CITY,
                        COLUMN_POSTCODE},
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(id)},
                null,
                null,
                null);
        if(cursor.moveToFirst())
        {
            String streeNumber = cursor.getString(cursor.getColumnIndex(COLUMN_STREETNUMBER));
            String streeName = cursor.getString(cursor.getColumnIndex(COLUMN_STREETNAME));
            String suburb = cursor.getString(cursor.getColumnIndex(COLUMN_SUBURB));
            String city = cursor.getString(cursor.getColumnIndex(COLUMN_CITY));
            String postCode = cursor.getString(cursor.getColumnIndex(COLUMN_POSTCODE));

            final Address address = AddressFactory.getInstance(streeNumber, streeName, suburb, city, postCode);

            return address;
        }
        else
            return null;
    }

    @Override
    public Address save(Address address) {
        open();
        ContentValues values = new ContentValues();

        values.put(COLUMN_STREETNUMBER, address.getStreetNumber());
        values.put(COLUMN_STREETNAME, address.getStreetName());
        values.put(COLUMN_SUBURB, address.getSuburb());
        values.put(COLUMN_CITY, address.getCity());
        values.put(COLUMN_POSTCODE, address.getPostCode());

        Long id = db.insertOrThrow(TABLE_ADDRESS, null,values);

        Address newAddress = new Address.Builder()
                .copy(address)
                .id(new Long(id))
                .build();

        return newAddress;
    }

    @Override
    public Address update(Address entity) {
        open();

        ContentValues values = new ContentValues();

        values.put(COLUMN_ID, entity.getId());
        values.put(COLUMN_STREETNUMBER, entity.getStreetNumber());
        values.put(COLUMN_STREETNAME, entity.getStreetName());
        values.put(COLUMN_SUBURB, entity.getSuburb());
        values.put(COLUMN_CITY, entity.getCity());
        values.put(COLUMN_POSTCODE, entity.getPostCode());

        db.update(
                TABLE_ADDRESS,
                values,
                COLUMN_ID + " =? ",
                new String[]{String.valueOf((entity.getId()))}
        );
        return entity;
    }

    @Override
    public Address delete(Address entity) {
        open();
        db.delete(
                TABLE_ADDRESS,
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(entity.getId())});
        return entity;
    }

    @Override
    public Set<Address> findAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        Set<Address> addressSet = new HashSet<>();
        open();
        Cursor cursor = db.query(TABLE_ADDRESS, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Long columnId = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
                String streetNumber = cursor.getString(cursor.getColumnIndex(COLUMN_STREETNUMBER));
                String streetName = cursor.getString(cursor.getColumnIndex(COLUMN_STREETNAME));
                String suburb = cursor.getString(cursor.getColumnIndex(COLUMN_SUBURB));
                String city = cursor.getString(cursor.getColumnIndex(COLUMN_CITY));
                String postCode = cursor.getString(cursor.getColumnIndex(COLUMN_POSTCODE));

                final Address address = AddressFactory.getInstance(streetNumber, streetName, suburb, city, postCode);

                addressSet.add(address);
            }
            while (cursor.moveToNext());
        }
        return addressSet;
    }

    @Override
    public int deleteAll() {
        open();
        int rowsDeleted = db.delete(TABLE_ADDRESS,null,null);
        //close();
        return rowsDeleted;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(this.getClass().getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADDRESS);
        onCreate(db);
    }
}
