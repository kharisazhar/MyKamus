package com.dicoding.kharisazhar.mykamus.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.dicoding.kharisazhar.mykamus.model.KamusModel;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.dicoding.kharisazhar.mykamus.db.DatabaseContract.DATABASE_TABLE_INDONESIA;
import static com.dicoding.kharisazhar.mykamus.db.DatabaseContract.KamusColumns.TRANSLATE;
import static com.dicoding.kharisazhar.mykamus.db.DatabaseContract.KamusColumns.WORD;

public class KamusIndonesiaEnglishHelper {

    private static String INDONESIA = DATABASE_TABLE_INDONESIA;

    private Context context;
    private DatabaseHelper dataBaseHelper;

    private SQLiteDatabase database;


    public KamusIndonesiaEnglishHelper(Context context) {
        this.context = context;
    }

    public KamusIndonesiaEnglishHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dataBaseHelper.close();
    }

    public ArrayList<KamusModel> getDataByName(String word){
        String result = "";
        Cursor cursor = database.query(DATABASE_TABLE_INDONESIA,null,WORD+" LIKE ?",new String[]{word},null,null,_ID + " ASC",null);
        cursor.moveToFirst();
        ArrayList<KamusModel> arrayList = new ArrayList<>();
        KamusModel kamusModel;
        if (cursor.getCount()>0) {
            do {
                kamusModel = new KamusModel();
                kamusModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                kamusModel.setWord(cursor.getString(cursor.getColumnIndexOrThrow(WORD)));
                kamusModel.setTranslate(cursor.getString(cursor.getColumnIndexOrThrow(TRANSLATE)));

                arrayList.add(kamusModel);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<KamusModel> getAllData(){
        Cursor cursor = database.query(DATABASE_TABLE_INDONESIA,null,null,null,null,null,_ID+ " ASC",null);
        cursor.moveToFirst();
        ArrayList<KamusModel> arrayList = new ArrayList<>();
        KamusModel kamusModel;
        if (cursor.getCount()>0) {
            do {
                kamusModel = new KamusModel();
                kamusModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                kamusModel.setWord(cursor.getString(cursor.getColumnIndexOrThrow(WORD)));
                kamusModel.setTranslate(cursor.getString(cursor.getColumnIndexOrThrow(TRANSLATE)));


                arrayList.add(kamusModel);
                cursor.moveToNext();


            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(KamusModel mahasiswaModel){
        ContentValues initialValues =  new ContentValues();
        initialValues.put(WORD, mahasiswaModel.getWord());
        initialValues.put(TRANSLATE, mahasiswaModel.getTranslate());
        return database.insert(DATABASE_TABLE_INDONESIA, null, initialValues);
    }

    public void beginTransaction(){
        database.beginTransaction();
    }

    public void setTransactionSuccess(){
        database.setTransactionSuccessful();
    }

    public void endTransaction(){
        database.endTransaction();
    }

    public void insertTransaction(KamusModel kamusModel){
        String sql = "INSERT INTO "+DATABASE_TABLE_INDONESIA+" ("+WORD+", "+TRANSLATE
                +") VALUES (?, ?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, kamusModel.getWord());
        stmt.bindString(2, kamusModel.getTranslate());
        stmt.execute();
        stmt.clearBindings();

    }

    public int update(KamusModel kamusModel){
        ContentValues args = new ContentValues();
        args.put(WORD, kamusModel.getWord());
        args.put(TRANSLATE, kamusModel.getWord());
        return database.update(DATABASE_TABLE_INDONESIA, args, _ID + "= '" + kamusModel.getId() + "'", null);
    }


    public int delete(int id){
        return database.delete(DATABASE_TABLE_INDONESIA, _ID + " = '"+id+"'", null);
    }
}
