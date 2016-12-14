package com.system.odering.front_end.repositories.user.Impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.system.odering.front_end.domain.user.Impl.Login;
import com.system.odering.front_end.factories.user.LoginFactory;
import com.system.odering.front_end.repositories.user.ILoginRepository;
import com.system.odering.front_end.utils.database.DBConstants;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by cfebruary on 2016/12/14.
 */
public class LoginRepositoryImpl extends SQLiteOpenHelper implements ILoginRepository {
    public static final String TABLE_NAME = "login";
    private SQLiteDatabase db;

    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_USERNAME = "USERNAME";
    public static final String COLUMN_PASSWORD = "PASSWORD";

    //Database table creation
    private static final String DATABASE_CREATE = " CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USERNAME + " TEXT NOT NULL,"
            + COLUMN_PASSWORD + " TEXT NOT NULL);";

    public LoginRepositoryImpl(Context context)
    {
        super(context, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
    }

    public void open() throws SQLException {
        db = this.getWritableDatabase();
    }

    @Override
    public Login findById(Long id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_NAME,
                new String[]{
                        COLUMN_ID,
                        COLUMN_USERNAME,
                        COLUMN_PASSWORD},
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(id)},
                null,
                null,
                null);
        if(cursor.moveToFirst())
        {
            Long loginId = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
            String username = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));
            String password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));

            final Login login = LoginFactory.getInstance(loginId, username, password);

            return login;
        }
        else
            return null;
    }

    @Override
    public Login save(Login login) {
        open();
        ContentValues values = new ContentValues();

        values.put(COLUMN_ID, login.getId());
        values.put(COLUMN_USERNAME, login.getUsername());
        values.put(COLUMN_PASSWORD, login.getPassword());

        Long id = db.insertOrThrow(TABLE_NAME, null,values);

        Login newLogin = new Login.Builder()
                .copy(login)
                .id(new Long(id))
                .build();

        return newLogin;
    }

    @Override
    public Login update(Login entity) {
        open();

        ContentValues values = new ContentValues();

        values.put(COLUMN_ID, entity.getId());
        values.put(COLUMN_USERNAME, entity.getUsername());
        values.put(COLUMN_PASSWORD, entity.getPassword());

        db.update(
                TABLE_NAME,
                values,
                COLUMN_ID + " =? ",
                new String[]{String.valueOf((entity.getId()))}
        );
        return entity;
    }

    @Override
    public Login delete(Login entity) {
        open();
        db.delete(
                TABLE_NAME,
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(entity.getId())});
        return entity;
    }

    @Override
    public Set<Login> findAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        Set<Login> loginSet = new HashSet<>();
        open();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Long id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
                String username = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));
                String password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));

                final Login login = LoginFactory.getInstance(id, username, password);

                loginSet.add(login);
            }
            while (cursor.moveToNext());
        }
        return loginSet;
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
