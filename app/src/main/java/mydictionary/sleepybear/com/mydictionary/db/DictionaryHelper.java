/*
 * Copyright (c) 2018. Rosdyana Kusuma.
 */

package mydictionary.sleepybear.com.mydictionary.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

import mydictionary.sleepybear.com.mydictionary.model.DictionaryModel;

public class DictionaryHelper {
    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    public DictionaryHelper(Context context) {
        this.context = context;
    }

    public DictionaryHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    public ArrayList<DictionaryModel> getValueByKeyword(String keyword, boolean isENtoID) {
        ArrayList<DictionaryModel> arrayList = new ArrayList<>();
        DictionaryModel dictionaryModel;
        String TABLE_NAME = isENtoID ? DbContract.TABLE_EN_ID : DbContract.TABLE_ID_EN;
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, null, DbContract.DictionaryColumns.COL_KEYWORD + " LIKE ?", new String[]{keyword}, null, null, DbContract.DictionaryColumns.COL_ID + " ASC", null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                dictionaryModel = new DictionaryModel();
                dictionaryModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DbContract.DictionaryColumns.COL_ID)));
                dictionaryModel.setKeyword(cursor.getString(cursor.getColumnIndexOrThrow(DbContract.DictionaryColumns.COL_KEYWORD)));
                dictionaryModel.setValue(cursor.getString(cursor.getColumnIndexOrThrow(DbContract.DictionaryColumns.COL_VALUE)));

                arrayList.add(dictionaryModel);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<DictionaryModel> getAllData(boolean isENtoID) {
        ArrayList<DictionaryModel> arrayList = new ArrayList<>();
        DictionaryModel dictionaryModel;

        Cursor cursor = queryAllData(isENtoID);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                dictionaryModel = new DictionaryModel();
                dictionaryModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DbContract.DictionaryColumns.COL_ID)));
                dictionaryModel.setKeyword(cursor.getString(cursor.getColumnIndexOrThrow(DbContract.DictionaryColumns.COL_KEYWORD)));
                dictionaryModel.setValue(cursor.getString(cursor.getColumnIndexOrThrow(DbContract.DictionaryColumns.COL_VALUE)));

                arrayList.add(dictionaryModel);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public Cursor queryAllData(boolean isENtoID) {
        String TABLE_NAME = isENtoID ? DbContract.TABLE_EN_ID : DbContract.TABLE_ID_EN;
        return sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + DbContract.DictionaryColumns.COL_ID + " ASC", null);
    }

    public long insert(DictionaryModel dictionaryModel, boolean isENtoID) {
        String TABLE_NAME = isENtoID ? DbContract.TABLE_EN_ID : DbContract.TABLE_ID_EN;
        ContentValues initialValues = new ContentValues();
        initialValues.put(DbContract.DictionaryColumns.COL_KEYWORD, dictionaryModel.getKeyword());
        initialValues.put(DbContract.DictionaryColumns.COL_VALUE, dictionaryModel.getValue());
        return sqLiteDatabase.insert(TABLE_NAME, null, initialValues);
    }

    public void beginTransaction() {
        sqLiteDatabase.beginTransaction();
    }

    public void setTransactionSuccess() {
        sqLiteDatabase.setTransactionSuccessful();
    }

    public void endTransaction() {
        sqLiteDatabase.endTransaction();
    }

    public void insertTransaction(ArrayList<DictionaryModel> dictionaryModels, boolean isENtoID) {
        String TABLE_NAME = isENtoID ? DbContract.TABLE_EN_ID : DbContract.TABLE_ID_EN;
        String sql = "INSERT INTO " + TABLE_NAME + " (" + DbContract.DictionaryColumns.COL_KEYWORD + ", "
                + DbContract.DictionaryColumns.COL_VALUE
                + ") VALUES (?, ?)";
        beginTransaction();

        SQLiteStatement stmt = sqLiteDatabase.compileStatement(sql);
        for (int i = 0; i < dictionaryModels.size(); i++) {
            stmt.bindString(1, dictionaryModels.get(i).getKeyword());
            stmt.bindString(2, dictionaryModels.get(i).getValue());
            stmt.execute();
            stmt.clearBindings();
        }

        setTransactionSuccess();
        endTransaction();
    }

    public int update(DictionaryModel dictionaryModel, boolean isENtoID) {
        String TABLE_NAME = isENtoID ? DbContract.TABLE_EN_ID : DbContract.TABLE_ID_EN;
        ContentValues args = new ContentValues();
        args.put(DbContract.DictionaryColumns.COL_KEYWORD, dictionaryModel.getKeyword());
        args.put(DbContract.DictionaryColumns.COL_VALUE, dictionaryModel.getValue());
        return sqLiteDatabase.update(TABLE_NAME, args, DbContract.DictionaryColumns.COL_ID + "= '" + dictionaryModel.getId() + "'", null);
    }


    public int delete(int id, boolean isENtoID) {
        String TABLE_NAME = isENtoID ? DbContract.TABLE_EN_ID : DbContract.TABLE_ID_EN;
        return sqLiteDatabase.delete(TABLE_NAME, DbContract.DictionaryColumns.COL_ID + " = '" + id + "'", null);
    }
}
