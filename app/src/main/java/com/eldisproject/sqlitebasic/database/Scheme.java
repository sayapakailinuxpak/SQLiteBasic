package com.eldisproject.sqlitebasic.database;

import android.provider.BaseColumns;
//Scheme for making database contract
public class Scheme {

    //inner class
    public static class ItemEntry implements BaseColumns{
        public static final String TABLE_NAME = "item_table";
        public static final String COLUMN_ITEM_AMOUNT = "amount";
        public static final String COLUMN_ITEM_NAME = "name";
        public static final String COLUMN_TIMSTAMP = "timestamp";
    }
}
