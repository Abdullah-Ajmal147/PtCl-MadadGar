package com.stepup.madadgaar.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Updated by Farhan on 20/10/2019.
 * Preferences only related to User
 */

public class UserPreferences {


    public static final String PREF_BACK_PRESSED = "pre_back_press";
    public static final String PREF_IS_CARD_BLOCKED = "pre_is_card_blocked";
    public static final String PREF_BLOCK_TIME="pre_block_time";
    public static final String PREF_IS_SPLASH = "pre_is_splash";
    public static final String PREF_USER_IS_LOGIN = "pre_user_is_login";
    public static final String PREF_IS_CONNECTED = "pre_is_connected";
    public static final String PREF_RECONNECT = "pre_reconnect";
    public static final String PREF_EXPIRY_DAYS = "pre_expiry_days";
    public static final String PREF_EXPIRY_DATE = "pre_expiry_date";
    public static final String PREF_SERVER_NAME = "pre_server_name";
    public static final String PREF_SERVER_ICON = "pre_server_icon";
    public static final String PREF_SERVER_IP = "pre_server_ip";
    public static final String PREF_USER_NAME = "pre_user_name";
    public static final String PREF_USER_PASSWORD = "pre_password";
    public static final String PREF_SERVER_POSITION = "pre_server_position";
    public static final String PREF_UUID = "pre_uuid";
    public static final String PREF_DEVICE_ID = "pre_device_id";
    public static final String PREF_SHOW_WHATSAPP="pref_allow_connect";
    public static final String SERVER_ID="server_id";
    public static final String PREF_SUPPORT_NUMBER="pref_support_number";

    public static final String PREF_IS_AUTO_CONNECT="pref_is_auto_connect";
    private static final String TAG = UserPreferences.class.getSimpleName();
    private final String PrefName = "com.DigitalDevelopers.OnionVPN.preference.UserPreferences";

    private static UserPreferences ourInstance = null;
    public static final String IS_CONNECTED_BY_USER="pre_is_connected_by_user";
    private static Context mContext = null;
    private SharedPreferences pref = null;

    public static UserPreferences getInstance(Context pContext) {
        try {
            mContext = pContext.getApplicationContext();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (ourInstance == null) {
            ourInstance = new UserPreferences();
        }

        return ourInstance;
    }

    private UserPreferences() {
        pref = mContext.getSharedPreferences(PrefName, Context.MODE_PRIVATE);
    }

    public void saveInteger(final String pRef, final int value) {

        try {
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt(pRef, value);
            editor.apply();
        } catch (Exception e) {
            Log.e(TAG, "" + e.getMessage());
        }
    }

    public int getInteger(final String pRef, int defaultValue) {
        return pref.getInt(pRef, defaultValue);
    }

    public void saveString(final String pRef, final String value) {

        try {
            SharedPreferences.Editor editor = pref.edit();
            editor.putString(pRef, value);
            editor.apply();
        } catch (Exception e) {
            Log.e(TAG, "" + e.getMessage());
        }
    }
    public void saveLong(final String pRef, final long value) {

        try {
            SharedPreferences.Editor editor = pref.edit();
            editor.putLong(pRef, value);
            editor.apply();
        } catch (Exception e) {
            Log.e(TAG, "" + e.getMessage());
        }
    }
    public long getlong(final String pRef, final long defaultValue) {
        return pref.getLong(pRef, defaultValue);
    }
    public String getString(final String pRef, final String defaultValue) {
        return pref.getString(pRef, defaultValue);
    }

    public void saveBoolean(final String pRef, final boolean value) {

        try {
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean(pRef, value);
            editor.apply();
        } catch (Exception e) {
            Log.e(TAG, "" + e.getMessage());
        }
    }

    public boolean getBoolean(final String pRef, final boolean defaultValue) {
        return pref.getBoolean(pRef, defaultValue);
    }

    public  void removeServer()
    {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(SERVER_ID,null);
        editor.putString(PREF_SERVER_IP,null);
        editor.putString(PREF_SERVER_NAME,null);
        editor.putString(PREF_USER_PASSWORD,null);
        editor.putString(PREF_USER_NAME,null);
        editor.putInt(UserPreferences.PREF_SERVER_ICON, 0);
        editor.putInt(UserPreferences.PREF_SERVER_POSITION, 0);
        editor.apply();
    }



}

