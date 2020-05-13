package com.anatoly.contactslab;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.anatoly.contactslab.database.ContactCursorWrapper;
import com.anatoly.contactslab.database.DbHelper;
import com.anatoly.contactslab.database.DbSchema.ContactsTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ContactLab {
    private static ContactLab sContactLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static ContactLab get(Context context) {
        if (sContactLab == null) {
            sContactLab = new ContactLab(context);
        }
        return sContactLab;
    }

    private ContactLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new DbHelper(mContext).getWritableDatabase();
    }

    public void addContact(Contact contact) {
        ContentValues values = getContentValues(contact);
        mDatabase.insert(ContactsTable.NAME, null, values);
    }

    public List<Contact> getContacts() {
        List<Contact> contacts = new ArrayList<>();
        try (ContactCursorWrapper cursor = queryContacts(null, null)) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                contacts.add(cursor.getContact());
                cursor.moveToNext();
            }
        }
        return contacts;
    }

    public Contact getContact(UUID id) {
        try (ContactCursorWrapper cursor = queryContacts(
                ContactsTable.Columns.UUID + " = ?",
                new String[]{id.toString()})) {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getContact();
        }
    }

    public void updateContact(Contact contact) {
        String uuidString = contact.getId().toString();
        ContentValues values = getContentValues(contact);
        mDatabase.update(ContactsTable.NAME, values,
                ContactsTable.Columns.UUID + " = ?",
                new String[] { uuidString });
    }

    public void deleteContact(Contact contact) {
        String uuidString = contact.getId().toString();
        mDatabase.delete(ContactsTable.NAME,
                ContactsTable.Columns.UUID + " = ?",
                new String[] { uuidString });
    }

    private ContactCursorWrapper queryContacts(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                ContactsTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new ContactCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Contact contact) {
        ContentValues values = new ContentValues();
        values.put(ContactsTable.Columns.UUID, contact.getId().toString());
        values.put(ContactsTable.Columns.FIRSTNAME, contact.getFirstName());
        values.put(ContactsTable.Columns.LASTNAME, contact.getLastName());
        values.put(ContactsTable.Columns.PHONE, contact.getPhone());
        values.put(ContactsTable.Columns.EMAIL, contact.getEmail());
        return values;
    }

    public void initContacts(Application application) {
        List<Contact> contacts = JsonContactsParser.getContactsFromJsonAsset(application);
        for (Contact contact : contacts) {
            addContact(contact);
        }
    }
}
