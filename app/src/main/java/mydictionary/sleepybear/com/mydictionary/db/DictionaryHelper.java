/*
 * Copyright (c) 2018. Rosdyana Kusuma.
 */

package mydictionary.sleepybear.com.mydictionary.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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
        String DB_NAME = isENtoID ? DbContract.TABLE_EN_ID : DbContract.TABLE_ID_EN;
        Cursor cursor = sqLiteDatabase.query(DB_NAME, null, DbContract.DictionaryColumns.COL_KEYWORD + " LIKE ?", new String[]{keyword}, null, null, DbContract.DictionaryColumns.COL_ID + " ASC", null);
//        if(isENtoID){
//            cursor = sqLiteDatabase.query(DbContract.TABLE_EN_ID,null,DbContract.DictionaryColumns.COL_KEYWORD+" LIKE ?",new String[]{keyword},null,null,DbContract.DictionaryColumns.COL_ID + " ASC",null);
//        }

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
        String DB_NAME = isENtoID ? DbContract.TABLE_EN_ID : DbContract.TABLE_ID_EN;
        Cursor cursor = sqLiteDatabase.query(DB_NAME, null, null, null, null, null, DbContract.DictionaryColumns.COL_ID + " ASC", null);
//        if(isENtoID){
//            cursor = sqLiteDatabase.query(DbContract.TABLE_EN_ID,null,null,null,null,null,DbContract.DictionaryColumns.COL_ID+ " ASC",null);
//        }
        cursor.moveToFirst();
        ArrayList<DictionaryModel> arrayList = new ArrayList<>();
        DictionaryModel dictionaryModel;
        if (cursor.getCount() > 0) {
            do {
                dictionaryModel = new DictionaryModel();
                dictionaryModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DbContract.DictionaryColumns.COL_ID)));
                dictionaryModel.setKeyword(cursor.getString(cursor.getColumnIndexOrThrow(DbContract.DictionaryColumns.COL_KEYWORD)));
                dictionaryModel.setKeyword(cursor.getString(cursor.getColumnIndexOrThrow(DbContract.DictionaryColumns.COL_VALUE)));


                arrayList.add(dictionaryModel);
                cursor.moveToNext();


            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(DictionaryModel dictionaryModel, boolean isENtoID) {
        String DB_NAME = isENtoID ? DbContract.TABLE_EN_ID : DbContract.TABLE_ID_EN;
        ContentValues initialValues = new ContentValues();
        initialValues.put(DbContract.DictionaryColumns.COL_KEYWORD, dictionaryModel.getKeyword());
        initialValues.put(DbContract.DictionaryColumns.COL_VALUE, dictionaryModel.getValue());
//        if(isENtoID){
//            return sqLiteDatabase.insert(DbContract.TABLE_EN_ID, null, initialValues);
//        }
        return sqLiteDatabase.insert(DB_NAME, null, initialValues);
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
        String DB_NAME = isENtoID ? DbContract.TABLE_EN_ID : DbContract.TABLE_ID_EN;
        String sql = "INSERT INTO " + DB_NAME + " (" + DbContract.DictionaryColumns.COL_KEYWORD + ", " + DbContract.DictionaryColumns.COL_VALUE
                + ") VALUES (?, ?)";
//        if(isENtoID){
//            sql = "INSERT INTO "+DbContract.TABLE_EN_ID+" ("+DbContract.DictionaryColumns.COL_KEYWORD+", "+DbContract.DictionaryColumns.COL_VALUE
//                    +") VALUES (?, ?)";
//        }
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
        String DB_NAME = isENtoID ? DbContract.TABLE_EN_ID : DbContract.TABLE_ID_EN;
        ContentValues args = new ContentValues();
        args.put(DbContract.DictionaryColumns.COL_KEYWORD, dictionaryModel.getKeyword());
        args.put(DbContract.DictionaryColumns.COL_VALUE, dictionaryModel.getValue());
//        if (isENtoID) {
//            return sqLiteDatabase.update(DbContract.TABLE_EN_ID, args, DbContract.DictionaryColumns.COL_ID + "= '" + dictionaryModel.getId() + "'", null);
//        }
        return sqLiteDatabase.update(DB_NAME, args, DbContract.DictionaryColumns.COL_ID + "= '" + dictionaryModel.getId() + "'", null);
    }


    public int delete(int id, boolean isENtoID) {
        String DB_NAME = isENtoID ? DbContract.TABLE_EN_ID : DbContract.TABLE_ID_EN;
//        if (isENtoID) {
//            return sqLiteDatabase.delete(DbContract.TABLE_EN_ID, DbContract.DictionaryColumns.COL_ID + " = '" + id + "'", null);
//        }
        return sqLiteDatabase.delete(DB_NAME, DbContract.DictionaryColumns.COL_ID + " = '" + id + "'", null);
    }
}
