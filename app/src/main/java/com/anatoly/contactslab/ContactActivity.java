package com.anatoly.contactslab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;
import java.util.UUID;

public class ContactActivity extends AppCompatActivity {
    private static final String EXTRA_CONTACT_ID =
            "com.anatoly.contactslab.contact_id";
    List<Contact> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);

        if(fragment == null) {
            Intent intent = getIntent();
            if (intent.getSerializableExtra(EXTRA_CONTACT_ID) != null) {
                UUID crimeId = (UUID) intent
                        .getSerializableExtra(EXTRA_CONTACT_ID);
                fragmentManager.beginTransaction()
                        .add(R.id.fragment_container, ContactFragment.newInstance(crimeId))
                        .commit();
            } else {
                fragmentManager.beginTransaction()
                        .add(R.id.fragment_container, new ContactFragment())
                        .commit();
            }
        }
    }

    public static Intent newIntent(Context packageContext, UUID contactId) {
        Intent intent = new Intent(packageContext, ContactActivity.class);
        intent.putExtra(EXTRA_CONTACT_ID, contactId);
        return intent;
    }
}
