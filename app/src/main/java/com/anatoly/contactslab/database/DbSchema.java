package com.anatoly.contactslab.database;

public class DbSchema {
    public static final class ContactsTable {
        public static final String NAME = "contacts";

        public static final class Columns {
            public static final String UUID = "uuid";
            public static final String FIRSTNAME = "firstname";
            public static final String LASTNAME = "lastname";
            public static final String PHONE = "phone";
            public static final String EMAIL = "email";
        }
    }
}
