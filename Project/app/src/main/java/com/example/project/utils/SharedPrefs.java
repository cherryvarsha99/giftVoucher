package com.example.project.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class SharedPrefs {

    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor prefsEditor;
    public static SharedPrefs sharedPrefs;
    public static final User user = new User();

    private SharedPrefs() {
    }

    public void clearAllPreferences() {
        prefsEditor = sharedPreferences.edit();
        prefsEditor.clear();
        prefsEditor.commit();
    }

    public String getValue(String Tag) {
        return sharedPreferences.getString(Tag, "");
    }

    public static SharedPrefs getInstance(Context ctx) {
        if (sharedPrefs == null) {
            sharedPrefs = new SharedPrefs();
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
            prefsEditor = sharedPreferences.edit();
        }
        return sharedPrefs;
    }

    public void clearPreferences(String key) {
        prefsEditor.remove(key);
        prefsEditor.commit();
    }

    public void setIntValue(String Tag, int value) {
        prefsEditor.putInt(Tag, value);
        prefsEditor.apply();
    }

    public int getIntValue(String Tag) {
        return sharedPreferences.getInt(Tag, 0);
    }

    public void setLongValue(String Tag, long value) {
        prefsEditor.putLong(Tag, value);
        prefsEditor.apply();
    }

    public long getLongValue(String Tag) {
        return sharedPreferences.getLong(Tag, 0);
    }


    public void setValue(String Tag, String token) {
        prefsEditor.putString(Tag, token);
        prefsEditor.commit();
    }



    public boolean getBooleanValue(String Tag) {
        return sharedPreferences.getBoolean(Tag, false);

    }

    public void setBooleanValue(String Tag, boolean token) {
        prefsEditor.putBoolean(Tag, token);
        prefsEditor.commit();
    }

    public void setUser(User user, String tag) {
        Gson gson = new Gson();
        String hashMapString = gson.toJson(user);
        prefsEditor.putString(tag, hashMapString);
        prefsEditor.apply();
    }

    public User getUser(String tag) {
        String obj = sharedPreferences.getString(tag, "default");
        if (obj.equals("default")) {
            return user;
        } else {
            Gson gson = new Gson();
            String storedHashMapString = sharedPreferences.getString(tag, "");
            Type type = new TypeToken<User>() {
            }.getType();
            return gson.fromJson(storedHashMapString, type);
        }
    }
}
