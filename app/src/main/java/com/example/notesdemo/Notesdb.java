package com.example.notesdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Notesdb extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "notes_db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "notes_tb";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_NOTE = "note";
    public static final String COLUMN_TIME = "time"; // corrected column name
    public static final String COLUMN_DATE = "date"; // corrected column name

    public Notesdb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TITLE + " TEXT," +
                COLUMN_NOTE + " TEXT," +
                COLUMN_DATE + " TEXT," +
                COLUMN_TIME + " TEXT);";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);
        onCreate(db);
    }

    public String insertdata(String title, String descri, String date,String time ) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, title);
        contentValues.put(COLUMN_NOTE, descri);
        contentValues.put(COLUMN_TIME, time);
        contentValues.put(COLUMN_DATE, date);

        long result = database.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return "Failed";
        } else {
            return "Successfully inserted";
        }
    }


//    public String deleteRow(String id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{id});
////        db.close();
//        return "Delete data Entry";
//    }
//
    public int updateData(int id, String title, String desc,String date,String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, title);
        contentValues.put(COLUMN_NOTE, desc);
        contentValues.put(COLUMN_DATE, date);
        contentValues.put(COLUMN_TIME, time);
        int i = db.update(TABLE_NAME, contentValues, COLUMN_ID+" = " + id, null);
        return i;
    }

    public int deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String whereClause = COLUMN_ID + " = ?";
            String[] whereArgs = {String.valueOf(id)};

            int numRowsDeleted = db.delete(TABLE_NAME, whereClause, whereArgs);

            return numRowsDeleted;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
        return -1; // Return -1 in case of an error
    }


    public Cursor getdata() {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        return database.rawQuery(query, null);
    }
}
