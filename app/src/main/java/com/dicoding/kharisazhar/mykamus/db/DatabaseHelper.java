package com.dicoding.kharisazhar.mykamus.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.dicoding.kharisazhar.mykamus.db.DatabaseContract.DATABASE_TABLE_ENGLISH;
import static com.dicoding.kharisazhar.mykamus.db.DatabaseContract.DATABASE_TABLE_INDONESIA;
import static com.dicoding.kharisazhar.mykamus.db.DatabaseContract.KamusColumns.TRANSLATE;
import static com.dicoding.kharisazhar.mykamus.db.DatabaseContract.KamusColumns.WORD;

public class DatabaseHelper extends SQLiteOpenHelper{

    private static String DATABASE_NAME = "kamus_db";

    private static int DATABASE_VERSION = 5;

    public static String CREATE_TABLE_ENGLISH = "create table "+DATABASE_TABLE_ENGLISH+
            " ("+_ID+" integer primary key autoincrement, " +
                WORD+" text not null, " +
                TRANSLATE+" text not null);";

    public static String CREATE_TABLE_INDONESIA= "create table "+DATABASE_TABLE_INDONESIA+
            " ("+_ID+" integer primary key autoincrement, " +
            WORD+" text not null, " +
            TRANSLATE+" text not null);";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ENGLISH);
        db.execSQL(CREATE_TABLE_INDONESIA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE_ENGLISH);
        db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE_INDONESIA);
        onCreate(db);
    }
}
