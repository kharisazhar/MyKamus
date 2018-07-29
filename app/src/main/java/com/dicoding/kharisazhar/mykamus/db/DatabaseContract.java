package com.dicoding.kharisazhar.mykamus.db;

import android.provider.BaseColumns;

public class DatabaseContract {

    public static String DATABASE_TABLE_ENGLISH = "table_english";
    public static String DATABASE_TABLE_INDONESIA = "table_indonesia";

    public static final class KamusColumns implements BaseColumns {

        static String WORD = "word";
        static String TRANSLATE = "translate";
    }

}
