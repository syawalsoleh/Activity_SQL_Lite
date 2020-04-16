package com.example.activity_sql_lite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String MHS_TABLE_NAME = "mahasiswa";
    public static final String MHS_COLUMN_ID = "id";
    public static final String MHS_COLUMN_NIM = "nim";
    public static final String MHS_COLUMN_NAMA = "nama";
    public static final String MHS_COLUMN_PHONE = "phone";
    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(
                "create table mahasiswa " +
                        "(id integer primary key, nim text, nama text, phone text)"
        );
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS mahasiswa");
        onCreate(db);
    }

    public boolean insertContact (String nim, String nama, String phone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nim",nim);
        contentValues.put("nama",nama);
        contentValues.put("phone",phone);

        db.insert("mahasiswa",null,contentValues);
        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from mahasiswa where id="+id+"",null);
        return res;
    }

    public int numberOfRow(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db,MHS_TABLE_NAME);
        return numRows;
    }

    public ArrayList<String> getAllCotacts(){
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from mahasiswa",null);
        res.moveToFirst();

        while (res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(MHS_COLUMN_NAMA)));
            res.moveToNext();
        }
        return array_list;
    }

}
