package com.example.sqlitedatatorecyclerview;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SQLiteDataModel extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "contactDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TBL_NAME = "table_contact";
    private static final String TBL_CONTACT_COL_ID = "id";
    private static final String TBL_CONTACT_COL_NAME = "name";
    private static final String TBL_CONTACT_COL_CONTACT_NO = "contact_no";

    public SQLiteDataModel(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TBL_NAME + " ( " + TBL_CONTACT_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TBL_CONTACT_COL_NAME + " TEXT NOT NULL, " + TBL_CONTACT_COL_CONTACT_NO + " TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addData(String name, String contact_no)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TBL_CONTACT_COL_NAME, name);
        values.put(TBL_CONTACT_COL_CONTACT_NO, contact_no);
        db.insert(TBL_NAME, null, values);
    }

    public ArrayList<ContactModel> fetchContactData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TBL_NAME, null);

        ArrayList<ContactModel> contactModels = new ArrayList<>();

        while(cursor.moveToNext())
        {
            ContactModel contact = new ContactModel();
            contact.id = cursor.getInt(0);
            contact.name = cursor.getString(1);
            contact.contact_no = cursor.getString(2);

            contactModels.add(contact);
        }

        return contactModels;
    }
}
