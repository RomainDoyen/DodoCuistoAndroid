package com.example.dodocuisto.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = SQLiteDatabaseHelper.class.getSimpleName();

    public SQLiteDatabaseHelper(Context context, String databaseName, int databaseVersion) {
        super(context, databaseName, null, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserDatabase.Config.CREATE_TABLE_STATEMENT);
        db.execSQL(RecetteDatabase.Config.CREATE_TABLE_STATEMENT);
        db.execSQL(DirectionDatabase.Config.CREATE_TABLE_STATEMENT);
        db.execSQL(IngredientDatabase.Config.CREATE_TABLE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion +
                " to " + newVersion + ", which will destroy all old data.");
        db.execSQL("DROP TABLE IF EXISTS " + UserDatabase.Config.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DirectionDatabase.Config.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + IngredientDatabase.Config.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + RecetteDatabase.Config.TABLE_NAME);
        onCreate(db);
    }
}
