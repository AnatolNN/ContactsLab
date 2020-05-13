package com.anatoly.contactslab;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ContactListFragment extends Fragment {

    private RecyclerView mContactRecyclerView;
    private ContactAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_list, container,
                false);

        mContactRecyclerView = view.findViewById(R.id.content_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mContactRecyclerView.setLayoutManager(layoutManager);
        //set separators between items
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mContactRecyclerView.getContext(),
                layoutManager.getOrientation());
        mContactRecyclerView.addItemDecoration(dividerItemDecoration);

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, @NotNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_contact_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_contact:
                Intent intent = new Intent(getActivity(), ContactActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI() {
        final ContactLab contactLab = ContactLab.get(getActivity());
        final List<Contact> contacts = contactLab.getContacts();
//        contactLab.initContacts(getActivity().getApplication());
        if (mAdapter == null) {
            mAdapter = new ContactAdapter(contacts);
            mContactRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setmContacts(contacts);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class ContactHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Contact mContact;
        private TextView mNameTextView;
        private TextView mPhoneTextView;
        private TextView mEmailTextView;
        private ImageView mCallImageView;

        public ContactHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_contact, parent, false));
            itemView.setOnClickListener(this);

            mNameTextView = itemView.findViewById(R.id.contact_first_last_name);
            mPhoneTextView = itemView.findViewById(R.id.contact_phone);
            mEmailTextView = itemView.findViewById(R.id.contact_email);
            mCallImageView = itemView.findViewById(R.id.imageView_call);
            mCallImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mContact.getPhone()));
                    try {
                        startActivity(intent);
                    } catch (SecurityException e) {
                        Toast.makeText(getActivity(),
                            "You need to enable permission CALL_PHONE for this app!", Toast.LENGTH_SHORT)
                            .show();
                    }
                }
            });
        }

        public void bind(Contact contact) {
            mContact = contact;
            String nameText = mContact.getFirstName() + " " + mContact.getLastName();
            mNameTextView.setText(nameText);
            mPhoneTextView.setText(mContact.getPhone());
            mEmailTextView.setText(mContact.getEmail());
        }

        @Override
        public void onClick(View view) {
            Intent intent = ContactActivity.newIntent(getActivity(), mContact.getId());
            startActivity(intent);
        }
    }

    private class ContactAdapter extends RecyclerView.Adapter<ContactHolder> {
        private List<Contact> mContacts;
        public ContactAdapter(List<Contact> contacts) {
            mContacts = contacts;
        }

        @NonNull
        @Override
        public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ContactHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ContactHolder holder, int position) {
            Contact contact = mContacts.get(position);
            holder.bind(contact);
        }

        @Override
        public int getItemCount() {
            return mContacts.size();
        }

        public void setmContacts(List<Contact> contacts) {
            mContacts = contacts;
        }
    }


}
