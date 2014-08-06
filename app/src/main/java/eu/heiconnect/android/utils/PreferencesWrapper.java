package eu.heiconnect.android.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesWrapper {

    // ----------------------------------
    // CONSTANTS
    // ----------------------------------
    private static final String PREFERENCES_FILE_NAME = "heiconnect";
    private final SharedPreferences sharedPreferences;

    // ----------------------------------
    // CONSTRUCTORS
    // ----------------------------------
    public PreferencesWrapper(Context context) {
        sharedPreferences = context.getApplicationContext().getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
    }

    // ----------------------------------
    // PUBLIC METHODS
    // ----------------------------------

    public void putUserToken(String token) {
        putString(Key.USER_TOKEN, token);
    }

    public String getUserToken() {
        return getString(Key.USER_TOKEN);
    }


    public void putUserName(String name) {
        putString(Key.USER_NAME, name);
    }

    public String getUserName() {
        return getString(Key.USER_NAME);
    }

    // ----------------------------------
    // PRIVATE METHODS
    // ----------------------------------
    private void putString(Key key, String value) {
        sharedPreferences.edit().putString(key.toString(), value).apply();
    }

    private String getString(Key key) {
        return sharedPreferences.getString(key.toString(), null);
    }

    // ----------------------------------
    // INNER CLASSES
    // ----------------------------------
    public static enum Key {
        USER_TOKEN,
        USER_NAME
    }
}
