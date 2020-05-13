package com.anatoly.contactslab;

import java.util.UUID;

public class Contact {
    private UUID mId;
    private String mFirstName;
    private String mLastName;
    private String mPhone;
    private String mEmail;

    public Contact() {
        this(UUID.randomUUID());
    }

    public Contact(UUID id) {
        mId = id;
    }

    public UUID getId() {
        return mId;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "mFirstName='" + mFirstName + '\'' +
                ", mLastName='" + mLastName + '\'' +
                ", mPhone='" + mPhone + '\'' +
                ", mEmail='" + mEmail + '\'' +
                '}';
    }
}
