package com.anatoly.contactslab.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.anatoly.contactslab.database.DbSchema.ContactsTable;

public class DbHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "contactsBase.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + ContactsTable.NAME + "(" +
                        " _id integer primary key autoincrement, " +
                        ContactsTable.Columns.UUID + ", " +
                        ContactsTable.Columns.FIRSTNAME + ", " +
                        ContactsTable.Columns.LASTNAME + ", " +
                        ContactsTable.Columns.PHONE + ", " +
                        ContactsTable.Columns.EMAIL +
                        ")"
                );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
