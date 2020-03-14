package com.eldisproject.sqlitebasic.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//DB Helper is for creating database
public class ItemDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "DB_Item.db";
    public static final int DATABASE_VERSION = 1;

    public ItemDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ITEM_TABLE = "CREATE TABLE " +
                Scheme.ItemEntry.TABLE_NAME + "(" +
                Scheme.ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Scheme.ItemEntry.COLUMN_ITEM_AMOUNT + " INTEGER NOT NULL, " +
                Scheme.ItemEntry.COLUMN_ITEM_NAME + " TEXT NOT NULL, " +
                Scheme.ItemEntry.COLUMN_TIMSTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";

        db.execSQL(SQL_CREATE_ITEM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + Scheme.ItemEntry.TABLE_NAME;
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }
}
