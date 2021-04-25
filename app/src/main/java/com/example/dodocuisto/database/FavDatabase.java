package com.example.dodocuisto.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.dodocuisto.controller.DatabaseController;
import com.example.dodocuisto.controller.*;

public class FavDatabase extends SQLiteDatabaseHelper {
    private static final String DATABASE_NAME = "recipesDatabase";
    private static final int DATABASE_VERSION = 1;
    private static String FAVORITE_STATUS = "fStatus";
    private DatabaseController databaseAdapter;

    public FavDatabase(Context context) {
        super(context, DATABASE_NAME, DATABASE_VERSION);
    }
    //private static String DATABASE_NAME = "recipes"
    //private static String

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //sqLiteDatabase.execSQL(Config.);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
    }

    public void insertEmpty() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        for (int x = 1; x < 11; x++) {
            cv.put(RecetteDatabase.Config.KEY_ID, x);
            cv.put(FAVORITE_STATUS, "0");
            db.insert(RecetteDatabase.Config.TABLE_NAME, null, cv);
        }
    }

    public void insertIntoDatabaase(String item_title, int item_image, String id, String fav_status) {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(RecetteDatabase.Config.KEY_ID, id);
        cv.put(FAVORITE_STATUS, fav_status);
        db.insert(RecetteDatabase.Config.TABLE_NAME, null, cv);
        Log.d("FavDatabase", fav_status + cv);
    }

    public Cursor read_all_data(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + RecetteDatabase.Config.TABLE_NAME + " WHERE " + RecetteDatabase.Config.KEY_ID + "=" + id + "";
        return db.rawQuery(sql, null, null);
    }

    public void remove_fav(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE " + RecetteDatabase.Config.TABLE_NAME + " SET  " + FAVORITE_STATUS + " ='0' WHERE " + RecetteDatabase.Config.KEY_ID + "=" + id + "";
        db.execSQL(sql);
        Log.d("remove", id.toString());

    }

    public Cursor select_all_favorite_list() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + RecetteDatabase.Config.TABLE_NAME + " WHERE " + FAVORITE_STATUS + " ='1'";
        return db.rawQuery(sql,null,null);
    }
}
