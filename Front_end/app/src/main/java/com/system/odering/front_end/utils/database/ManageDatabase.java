package com.system.odering.front_end.utils.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by cfebruary on 2016/12/14.
 */
public class ManageDatabase extends SQLiteOpenHelper {
    private SQLiteDatabase db;

    public ManageDatabase(Context context)
    {
        super(context, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);

    }

    public ManageDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.db = db;
    }

    public ManageDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
        this.db = db;
    }

    public void deleteTable(String tablename){
        db.execSQL("DROP TABLE IF EXISTS "+tablename+";");
    }

    public boolean deleteDatabase(Context context) {
        return context.deleteDatabase(DBConstants.DATABASE_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Need to fix");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
