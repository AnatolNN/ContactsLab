package com.anatoly.contactslab;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Objects;
import java.util.UUID;

public class ContactFragment extends Fragment {
    private static final String ARG_CONTACT_ID = "contact_id";
    private boolean isOldContact;


    private Contact mContact;
    private EditText mEditTextFirstName;
    private EditText mEditTextLastName;
    private EditText mEditTextPhone;
    private EditText mEditTextEmail;

    public static ContactFragment newInstance(UUID contactId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CONTACT_ID, contactId);
        ContactFragment fragment = new ContactFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        Bundle args = getArguments();
        if (args != null) {
            if (args.getSerializable(ARG_CONTACT_ID) != null) {
                final UUID crimeId = (UUID) args.getSerializable(ARG_CONTACT_ID);
                mContact = ContactLab.get(getActivity()).getContact(crimeId);
                isOldContact = true;
            }
        } else {
            mContact = new Contact();
            isOldContact = false;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contact, container, false);

        mEditTextFirstName = v.findViewById(R.id.editText_firstName);
        mEditTextLastName = v.findViewById(R.id.editText_lastName);
        mEditTextPhone = v.findViewById(R.id.editText_phone);
        mEditTextEmail = v.findViewById(R.id.editText_email);

        if(isOldContact) {
            mEditTextFirstName.setText(mContact.getFirstName());
            mEditTextLastName.setText(mContact.getLastName());
            mEditTextPhone.setText(mContact.getPhone());
            mEditTextEmail.setText(mContact.getEmail());
            String contactName = mContact.getFirstName() + " " + mContact.getLastName();
            Objects.requireNonNull(getActivity()).setTitle(contactName);
        } else  {
            Objects.requireNonNull(getActivity()).setTitle("New Contact");
        }

        return v;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_contact, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_contact:
                if(isOldContact) {
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            ContactLab.get(getActivity()).deleteContact(mContact);
                        }
                    });
                    Intent intent = new Intent(getContext(), ContactListActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    Objects.requireNonNull(getActivity()).finish();
                    Toast.makeText(getActivity(),
                            "Contact deleted!", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Toast.makeText(getActivity(),
                            "Contact does not exist!", Toast.LENGTH_SHORT)
                            .show();
                }
                return true;
            case R.id.save_changes:
                String stringFirstName = mEditTextFirstName.getText().toString();
                String stringLastName = mEditTextLastName.getText().toString();
                String stringPhone = mEditTextPhone.getText().toString();
                String stringEmail = mEditTextEmail.getText().toString();

                if (stringFirstName.equals("") && stringLastName.equals("")) {
                    Toast.makeText(getActivity(),
                            "You need to set FirstName or LastName!", Toast.LENGTH_SHORT)
                            .show();
                    return false;
                } else {
                    mContact.setFirstName(stringFirstName);
                    mContact.setLastName(stringLastName);
                }

                if (stringPhone.equals("")) {
                    Toast.makeText(getActivity(),
                            "You need to set Phone number!", Toast.LENGTH_SHORT)
                            .show();
                    return false;
                } else {
                    mContact.setPhone(stringPhone);
                }
                mContact.setEmail(stringEmail);

                if (!isOldContact) {
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            ContactLab.get(getActivity()).addContact(mContact);
                        }
                    });
                    isOldContact = true;
                } else {
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            ContactLab.get(getActivity()).updateContact(mContact);
                        }
                    });
                }

                Toast.makeText(getActivity(),
                        "Contact saved!", Toast.LENGTH_SHORT)
                        .show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
