package com.example.no0ne.barcodescannernew.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by no0ne on 4/19/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database reference
    private SQLiteDatabase mDatabase;

    // Database name & version
    private static String DATABASE_NAME = "innovizzDB";
    private static final int DATABASE_VERSION = 1;

    // Table ScannerCodes
    private static final String TABLE_CODES = "Codes";

    // Column names
    private static final String COLUMN_ID = "Id";
    private static final String COLUMN_TITLE = "Title";
    private static final String COLUMN_CODE = "Code";

    // Query for creating table
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_CODES + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TITLE + " TEXT NOT NULL, "
            + COLUMN_CODE + " TEXT NOT NULL" + ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '" + TABLE_CODES + "'");
    }

    public void insertCode(String title, String code) {
        mDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_CODE, code);

        mDatabase.insert(TABLE_CODES, null, values);
        mDatabase.close();
    }

    public ArrayList<String> getTitle() {
        mDatabase = this.getReadableDatabase();

        ArrayList<String> titleList = new ArrayList<>();
        String[] projection = {COLUMN_TITLE};

        Cursor cursor = mDatabase.query(TABLE_CODES, projection, null, null, null, null, null);

        if (cursor.moveToNext()) {
            do {
                titleList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        return titleList;
    }

    public ArrayList<String> getCodeList() {
        mDatabase = this.getReadableDatabase();

        ArrayList<String> codeList = new ArrayList<>();
        String[] projection = {COLUMN_CODE};

        Cursor cursor = mDatabase.query(TABLE_CODES, projection, null, null, null, null, null);

        if (cursor.moveToNext()) {
            do {
                codeList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        return codeList;
    }
}
