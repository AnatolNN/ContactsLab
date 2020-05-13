package com.anatoly.contactslab;

import android.app.Application;

import android.content.res.AssetManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class JsonContactsParser {

    public static List<Contact> getContactsFromJsonAsset(Application application) {
        String jsonText = getJsonString(application.getAssets());
        List<Contact> contacts = new ArrayList<>();
        try {
            JSONArray arr = new JSONArray(jsonText);
            for (int i = 0; i < arr.length(); i++) {
                Contact c = new Contact();
                JSONObject jo = arr.getJSONObject(i);
                c.setFirstName(jo.getString("firstname"));
                c.setLastName(jo.getString("lastname"));
                c.setPhone(jo.getString("phone"));
                c.setEmail(jo.getString("email"));
                contacts.add(c);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return contacts;
    }

    private static String getJsonString(AssetManager assetManager) {
        String json;
        try (InputStream inputStream = assetManager.open("contacts_data.json")) {
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return json;
    }
}
