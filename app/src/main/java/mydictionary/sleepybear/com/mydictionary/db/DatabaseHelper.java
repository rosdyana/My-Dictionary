/*
 * Copyright (c) 2018. Rosdyana Kusuma.
 */

package mydictionary.sleepybear.com.mydictionary.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "dictionary.db";

    private static final int DB_VERSION = 1;

    public static String CREATE_TABLE_EN_ID = "create table " + DbContract.TABLE_EN_ID + " (" + DbContract.DictionaryColumns.COL_ID + " integer primary key autoincrement, " +
            DbContract.DictionaryColumns.COL_KEYWORD + " text not null, " +
            DbContract.DictionaryColumns.COL_VALUE + " text not null);";

    public static String CREATE_TABLE_ID_EN = "create table " + DbContract.TABLE_ID_EN + " (" + DbContract.DictionaryColumns.COL_ID + " integer primary key autoincrement, " +
            DbContract.DictionaryColumns.COL_KEYWORD + " text not null, " +
            DbContract.DictionaryColumns.COL_VALUE + " text not null);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_EN_ID);
        sqLiteDatabase.execSQL(CREATE_TABLE_ID_EN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DbContract.TABLE_EN_ID);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DbContract.TABLE_ID_EN);
        onCreate(sqLiteDatabase);
    }
}
