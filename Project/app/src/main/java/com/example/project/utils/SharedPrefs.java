package com.example.project.utils;

import android.content.SharedPreferences;

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
}
