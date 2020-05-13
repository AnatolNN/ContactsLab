package com.anatoly.contactslab.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.anatoly.contactslab.Contact;
import com.anatoly.contactslab.database.DbSchema.ContactsTable;

import java.util.UUID;

public class ContactCursorWrapper extends CursorWrapper {
    public ContactCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Contact getContact() {
        String uuidString = getString(getColumnIndex(ContactsTable.Columns.UUID));
        String firstName = getString(getColumnIndex(ContactsTable.Columns.FIRSTNAME));
        String lastName = getString(getColumnIndex(ContactsTable.Columns.LASTNAME));
        String phone = getString(getColumnIndex(ContactsTable.Columns.PHONE));
        String email = getString(getColumnIndex(ContactsTable.Columns.EMAIL));

        Contact contact = new Contact(UUID.fromString(uuidString));
        contact.setFirstName(firstName);
        contact.setLastName(lastName);
        contact.setPhone(phone);
        contact.setEmail(email);

        return contact;
    }
}
